# yuv_convert
是一个基于libyuv的图像色彩空间转换框架.支持NV12,NV21,YUV420,RGBA四种格式的相互转化


armeabi-v7a,arm64-v8a,x86,x86-64

# 集成:
implementation 'io.github.IAmCodingCoding:yuv_convert:$last_version'
具体版本请查看 [release](https://github.com/IAmCodingCoding/yuv_convert/releases)

# 支持不同形式的输入输出:
1. 输入:
- ByteBufferInput: 以DirectByteBuffer作为数据载体.所有不同的色彩通道放到不同的ByteBuffer中.比如: RGBA格式需要输入:\[bufferR,bufferG,bufferB,bufferA\]
- PackedByteBufferInput: 以DirectByteBuffer作为数据载体.所有色彩通道放到同一个ByteBuffer中
- ByteArrayInput: 以ByteArray作为数据载体.所有不同的色彩通道放到不同的ByteArray中.比如:RGBA格式需要输入\[arrayR,arrayG,arrayB,arrayA\]
- PackedByteArrayInput:以ByteArray作为数据载体.所有色彩通道放到同一个ByteArray中
3. 输出:
- ByteBufferOutput: 以DirectByteBuffer作为数据载体.所有不同的色彩通道放到不同的ByteBuffer中.比如: RGBA格式输出为:\[bufferR,bufferG,bufferB,bufferA\]
- PackedByteBufferOutput:以DirectByteBuffer作为数据载体.所有色彩通道放到同一个ByteBuffer中
- ByteArrayOutput: 以ByteArray作为数据载体.所有不同的色彩通道放到不同的ByteArray中.比如:RGBA格式输出{arrayR,arrayG,arrayB,arrayA}
- PackedByteArrayOutput:以ByteArray作为数据载体.所有不同的色彩通道放到同一个ByteArray中

# Sample:
1. nv12数据转换成rgba数据.并装载到bitmap中:
```kotlin
//创建一个输入端口.输入数据为打包的bytebuffer（即所有色彩通道都在一个DirectByteBuffer中）
val input = PackedByteBufferInput()
//创建一个输出端口.输出数据为打包的bytebuffer（即所有色彩通道都在一个DirectByteBuffer中）
val output = PackedByteBufferOutput(Format.RGBA)
input.provide(nv12Src, Format.NV12, width, height, intArrayOf(width, width))
Converter(input).convert(output)

val result:ByteBuffer=output.getOutput()
val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
bitmap.copyPixelsFromBuffer(result)
showBitmap(bitmap)//显示bitmap
```

2. 使用ImageReader读取到RGAB数据后需要转换为NV12:
```kotlin
val imageReader = ImageReader.newInstance(videoWidth, videoHeight, android.graphics.PixelFormat.RGBA_8888, 3)
private val input by lazy { PackedByteBufferInput() }
private val converter by lazy { Converter(input) }
private val nv12Output by lazy { ByteBufferOutput(Format.NV12) }//输出格式为Array<ByteBuffer>
imageReader.setOnImageAvailableListener(object :ImageReader.OnImageAvailableListener{
  override fun onImageAvailable(reader: ImageReader?) {
      if (reader == null) return
      val image = reader.acquireNextImage()
            input.provide(
                image.planes[0].buffer, //ImageReader读取rgba数据时.所有的通道都放在planes[0]中.所以这里选择了PackedByteBufferInput
                Format.RGBA,//指定输入数据的格式.此处为RGBA
                image.width,//输入图像的宽
                image.height,//输入图像的高
                intArrayOf(image.planes[0].rowStride)//输入图像每个通道每行的步长
            )
      converter.convert(nv12Output)//转换为nv12格式.转换后的数据存放在nv12Output中.同一个converter可以对接多个不同的Output
      image.close()//归还image
      val output:Array<ByteBuffer> = output.getOutput()//从Output中获取转换后的数据,可以重复调用
      val componentY=output[0] //存放y通道的ByteBuffer
      val componentUV=output[1] //存放uv通道的ByteBuffer
      val componentSize:Int=output.format.getComponentCount()//NV12和NV21格式固定返回2.YUV420返回3.RGBA返回4
      val strides=output.format.getStandardStride(image.width,image.height)//获取特定格式每行的步长
      val yCapacity=output.format.getComponentCapacity(0,image.width,image.height)//y通道的size
      val uvCapacity=output.format.getComponentCapacity(1,image.width,image.height)//uv通道的size
      val minSize=output.format.getMinFrameSize(image.width,image.height)//获取存储该格式一帧画面最小所需要的空间.nv12、nv21、yu420所需空间为width*height*1.5  rgba所需空间为width*height*4
      //todo  use the nv12 data ...
  }
},workHandler)
```
3. 并发场景:
- 使用synchronized
```kotlin
    private val input by lazy { PackedByteBufferInput() }
    private val output by lazy { ByteBufferOutput(Format.YUV420) }
    private val converter by lazy { Converter(input) }

    imageProducer.parallelProduce{rgbaImage->//伪代码，此处的ImageProducer会并发的生产rgba格式的数据
        synchronized(imageProducer){
            input.provide(
                image.planes[0].buffer, //ImageReader读取rgba数据时.所有的通道都放在planes[0]中.所以这里选择了PackedByteBufferInput
                Format.RGBA,//指定输入数据的格式.此处为RGBA
                image.width,//输入图像的宽
                image.height,//输入图像的高
                intArrayOf(image.planes[0].rowStride)//输入图像每个通道每行的步长
            )
            converter.convert(output)
            val result=output.getOutput()
        } 
    }
```
- 使用ThreadLocal
```kotlin
    val input = object : ThreadLocal<PackedByteBufferInput>() {
        override fun initialValue(): PackedByteBufferInput {
            return PackedByteBufferInput()
        }
    }
    val output = object : ThreadLocal<PackedByteBufferOutput>() {
        override fun initialValue(): PackedByteBufferOutput {
            return PackedByteBufferOutput(Format.YUV420)
        }
    }
    val converter = object : ThreadLocal<Converter<ByteBuffer>>() {
        override fun initialValue(): Converter<ByteBuffer> {
            return Converter(input.get())
        }
    }
    imageProducer.parallelProduce{rgbaImage->//伪代码，此处的ImageProducer会并发的生产rgba格式的数据
        input.get()?.provide(
            image.planes[0].buffer, //ImageReader读取rgba数据时.所有的通道都放在planes[0]中.所以这里选择了PackedByteBufferInput
            Format.RGBA,//指定输入数据的格式.此处为RGBA
            image.width,//输入图像的宽
            image.height,//输入图像的高
            intArrayOf(image.planes[0].rowStride)//输入图像每个通道每行的步长
            )
        converter.get()?.convert(output.get()!!)
        val result=output.get()?.getOutput()    
    }
```


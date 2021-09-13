package com.zmy.yuv_convert.example

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.zmy.yuv_convert.Converter
import com.zmy.yuv_convert.Format
import com.zmy.yuv_convert.input.PackedByteBufferInput
import com.zmy.yuv_convert.output.PackedByteArrayOutput
import com.zmy.yuv_convert.output.PackedByteBufferOutput
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {
    private val image1 by lazy { findViewById<ImageView>(R.id.image1) }
    private val image2 by lazy { findViewById<ImageView>(R.id.image2) }
    private val width = 720
    private val height = 1280
    private val yuv420Src: ByteBuffer by lazy {
        val buffer = ByteBuffer.allocateDirect(width * height * 3 / 2)
        assets.open("720_1280.yuv420").use {
            val buf = ByteArray(1204)
            while (true) {
                val len = it.read(buf, 0, buf.size)
                if (len < 0) {
                    break
                }
                buffer.put(buf, 0, len)
            }
        }
        buffer
    }
    private val nv12Src: ByteBuffer by lazy {
        val input = PackedByteBufferInput()
        val output = PackedByteBufferOutput(Format.NV12)
        input.provide(yuv420Src, Format.YUV420, width, height, intArrayOf(width, width / 2, width / 2))
        Converter(input).convert(output)
        output.output
    }
    private val nv21Src: ByteBuffer by lazy {
        val input = PackedByteBufferInput()
        val output = PackedByteBufferOutput(Format.NV21)
        input.provide(yuv420Src, Format.YUV420, width, height, intArrayOf(width, width / 2, width / 2))
        Converter(input).convert(output)
        output.output
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = PackedByteBufferInput()
        val output = PackedByteArrayOutput(Format.RGBA)
        input.provide(nv12Src, Format.NV12, width, height, intArrayOf(width, width))
        Converter(input).convert(output)
        val bmNV12 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bmNV12.copyPixelsFromBuffer(ByteBuffer.wrap(output.output))
        image1.setImageBitmap(bmNV12)


        val bmNV21 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        input.provide(nv21Src, Format.NV21, width, height, intArrayOf(width, width))
        Converter(input).convert(output)
        bmNV21.copyPixelsFromBuffer(ByteBuffer.wrap(output.output))
        image2.setImageBitmap(bmNV21)
    }
}
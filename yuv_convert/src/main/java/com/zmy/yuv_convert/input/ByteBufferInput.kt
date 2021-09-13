package com.zmy.yuv_convert.input

import com.zmy.yuv_convert.Format
import java.nio.ByteBuffer
import kotlin.math.max

class ByteBufferInput : Input<Array<ByteBuffer>>() {
    private var buffer: ByteBuffer? = null
    override fun provide(data: Array<ByteBuffer>, format: Format, width: Int, height: Int, stride: IntArray) {
        val totalCapacity = max(data.map { it.capacity() }.reduce { s, t -> s + t }, format.getMinFrameSize(width, height))
        if ((buffer?.capacity() ?: -1) < totalCapacity) {
            buffer = ByteBuffer.allocateDirect(totalCapacity)
        }
        var start = 0
        data.forEachIndexed { component, it ->
            it.position(0)
            it.limit(it.capacity())
            buffer?.position(start)
            buffer?.limit(start + it.capacity())
            buffer?.put(it)
            buffer?.position(0)
            start +=  max(it.capacity(), format.getComponentCapacity(component, width, height))
        }
        inputData = InputData(buffer!!, format, width, height, stride)
    }
}
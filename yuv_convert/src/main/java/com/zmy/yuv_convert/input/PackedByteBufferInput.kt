package com.zmy.yuv_convert.input

import com.zmy.yuv_convert.Format
import java.nio.ByteBuffer
import kotlin.math.max

class PackedByteBufferInput : Input<ByteBuffer>() {
    private var buffer: ByteBuffer? = null
    override fun provide(data: ByteBuffer, format: Format, width: Int, height: Int, stride: IntArray) {
        val targetCapacity = max(format.getMinFrameSize(width, height), data.capacity())
        if ((buffer?.capacity() ?: -1) < targetCapacity) {
            buffer = ByteBuffer.allocateDirect(max(format.getMinFrameSize(width, height), data.capacity()))
        }
        buffer?.position(0)
        data.position(0)
        buffer?.put(data)
        buffer?.position(0)
        inputData = InputData(buffer!!, format, width, height, stride)
    }
}
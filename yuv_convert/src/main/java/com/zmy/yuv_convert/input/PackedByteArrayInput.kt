package com.zmy.yuv_convert.input

import com.zmy.yuv_convert.Format
import java.nio.ByteBuffer
import kotlin.math.max

class PackedByteArrayInput : Input<ByteArray>() {
    private var buffer: ByteBuffer? = null
    override fun provide(data: ByteArray, format: Format, width: Int, height: Int, stride: IntArray) {
        val targetCapacity = max(format.getMinFrameSize(width, height), data.size)
        if ((buffer?.capacity() ?: -1) < targetCapacity) {
            buffer = ByteBuffer.allocateDirect(targetCapacity)
        }
        buffer?.position(0)
        buffer?.put(data, 0, data.size)
        buffer?.position(0)
        inputData = InputData(buffer!!, format, width, height, stride)
    }
}
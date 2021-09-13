package com.zmy.yuv_convert.input

import com.zmy.yuv_convert.Format
import java.nio.ByteBuffer
import kotlin.math.max

class ByteArrayInput : Input<Array<ByteArray>>() {

    private var buffer: ByteBuffer? = null

    override fun provide(data: Array<ByteArray>, format: Format, width: Int, height: Int, stride: IntArray) {
        val totalCapacity = max(data.map { it.size }.reduce { s, t -> s + t }, format.getMinFrameSize(width, height))
        if (buffer?.capacity() ?: -1 < totalCapacity) {
            buffer = ByteBuffer.allocateDirect(totalCapacity)
        }
        var start = 0
        data.forEachIndexed { component, inputData ->
            buffer?.position(start)
            buffer?.limit(start + inputData.size)
            buffer?.put(inputData)
            buffer?.position(0)
            start += max(inputData.size, format.getComponentCapacity(component, width, height))
        }
        inputData = InputData(buffer!!, format, width, height, stride)
    }
}
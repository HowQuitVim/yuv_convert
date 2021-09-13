package com.zmy.yuv_convert.output

import com.zmy.yuv_convert.Format
import java.nio.ByteBuffer

abstract class Output<T>(val format: Format) {
    protected var container: ByteBuffer? = null
    protected var width = 0
    protected var height = 0
    internal fun getDataContainer(): ByteBuffer {
        val size = format.getMinFrameSize(width,height)
        if ((container?.capacity() ?: -1) < size) {
            container = ByteBuffer.allocateDirect(size)
        }
        return container!!
    }

    abstract fun getOutput(): T
    fun update(width: Int, height: Int) {
        this.width = width
        this.height = height
    }
}
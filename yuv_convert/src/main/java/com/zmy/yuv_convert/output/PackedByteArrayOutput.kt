package com.zmy.yuv_convert.output

import com.zmy.yuv_convert.Format

class PackedByteArrayOutput(format: Format) : Output<ByteArray>(format) {
    var array: ByteArray? = null
    override fun getOutput(): ByteArray {
        if ((array?.size ?: -1) < format.getMinFrameSize(width, height)) {
            array = ByteArray(format.getMinFrameSize(width, height))
        }
        container?.position(0)
        container?.let { it.limit(it.capacity()) }
        container?.get(array!!)
        return array!!
    }
}
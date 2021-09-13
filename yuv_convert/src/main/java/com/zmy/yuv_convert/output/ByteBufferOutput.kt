package com.zmy.yuv_convert.output

import com.zmy.yuv_convert.Format
import java.nio.ByteBuffer

class ByteBufferOutput(format: Format) : Output<Array<ByteBuffer>>(format) {
    private var ret: Array<ByteBuffer>? = null
    override fun getOutput(): Array<ByteBuffer> {
        var start = 0
        ret = Array(format.getComponentCount()) {
            container?.position(start)
            container?.limit(start + format.getComponentCapacity(it, width, height))
            start += format.getComponentCapacity(it, width, height)
            container!!.slice()
        }
        return ret!!
    }
}
package com.zmy.yuv_convert.output

import com.zmy.yuv_convert.Format
import java.nio.ByteBuffer

class PackedByteBufferOutput(format: Format) : Output<ByteBuffer>(format) {
    override fun getOutput(): ByteBuffer {
        container!!.position(0)
        return container!!
    }
}
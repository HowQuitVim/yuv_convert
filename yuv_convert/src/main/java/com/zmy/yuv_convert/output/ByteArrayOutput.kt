package com.zmy.yuv_convert.output

import com.zmy.yuv_convert.Format

class ByteArrayOutput(format: Format) : Output<Array<ByteArray>>(format) {
    private var ret = mutableListOf<ByteArray>()
    override fun getOutput(): Array<ByteArray> {
        for (i in 0 until format.getComponentCount()) {
            if (i >= ret.size) {
                ret.add(ByteArray(format.getComponentCapacity(i, width, height)))
            } else {
                if (ret[i].size < format.getComponentCapacity(i, width, height)) {
                    ret[i] = ByteArray(format.getComponentCapacity(i, width, height))
                }
            }
        }
        var start = 0
        ret.forEachIndexed { component, buffer ->
            container?.position(start)
            val size = format.getComponentCapacity(component, width, height)
            container?.limit(start + size)
            start += format.getComponentCapacity(component, width, height)
            container?.get(buffer)
        }
        return ret.toTypedArray()
    }
}
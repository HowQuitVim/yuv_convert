package com.zmy.yuv_convert.input

import com.zmy.yuv_convert.Format
import java.nio.ByteBuffer

data class InputData(val data: ByteBuffer, val format: Format, val width: Int, val height: Int, val strides: IntArray) {
    init {
        data.position(0)
    }
}
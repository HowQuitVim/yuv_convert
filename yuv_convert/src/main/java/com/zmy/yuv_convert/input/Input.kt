package com.zmy.yuv_convert.input

import com.zmy.yuv_convert.Format

abstract class Input<T> {
    protected var inputData: InputData? = null
        @Synchronized set
        @Synchronized get

    abstract fun provide(data: T, format: Format, width: Int, height: Int, stride: IntArray)
    internal fun getInputData() = inputData
}
package com.zmy.yuv_convert

import com.zmy.yuv_convert.Format.*
import com.zmy.yuv_convert.input.Input
import com.zmy.yuv_convert.output.Output
import com.zmy.yuv_convert.output.PackedByteBufferOutput

class Converter<T>(private val input: Input<T>) {
    private val tempOutput by lazy { PackedByteBufferOutput(Format.YUV420) }
    fun <O> convert(output: Output<O>) {
        input.getInputData()?.let { inputData ->
            output.update(inputData.width, inputData.height)
            val container = output.getDataContainer()
            when (inputData.format) {
                YUV420 -> {
                    when (output.format) {
                        YUV420 -> YUVConvert.I420Format(inputData.width, inputData.height, inputData.data, inputData.strides[0], inputData.strides[1], inputData.strides[2], container)
                        RGBA -> YUVConvert.I420ToRGBA(inputData.width, inputData.height, inputData.data, inputData.strides[0], inputData.strides[1], inputData.strides[2], container)
                        NV12 -> YUVConvert.I420ToNV12(inputData.width, inputData.height, inputData.data, inputData.strides[0], inputData.strides[1], inputData.strides[2], container)
                        NV21 -> YUVConvert.I420ToNV21(inputData.width, inputData.height, inputData.data, inputData.strides[0], inputData.strides[1], inputData.strides[2], container)
                    }
                }
                NV12 -> {
                    when (output.format) {
                        NV12 -> YUVConvert.NV12Format(inputData.width, inputData.height, inputData.data, inputData.strides[0], inputData.strides[1], container)
                        YUV420 -> YUVConvert.NV12ToI420(inputData.width, inputData.height, inputData.data, inputData.strides[0], inputData.strides[1], container)
                        RGBA -> YUVConvert.NV12ToRGBA(inputData.width, inputData.height, input.getInputData()!!.data, inputData.strides[0], inputData.strides[1], container)
                        NV21 -> {
                            tempOutput.update(inputData.width, inputData.height)
                            YUVConvert.NV12ToI420(inputData.width, inputData.height, inputData.data, inputData.strides[0], inputData.strides[1], tempOutput.getDataContainer())
                            YUVConvert.I420ToNV21(inputData.width, inputData.height, tempOutput.getOutput(), inputData.width, inputData.width / 2, inputData.width / 2, container)
                        }
                    }
                }
                NV21 -> {
                    when (output.format) {
                        NV21 -> YUVConvert.NV21Format(inputData.width, inputData.height, input.getInputData()!!.data, inputData.strides[0], inputData.strides[1], container)
                        YUV420 -> YUVConvert.NV21ToI420(inputData.width, inputData.height, inputData.data, inputData.strides[0], inputData.strides[1], container)
                        RGBA -> YUVConvert.NV21ToRGBA(inputData.width, inputData.height, input.getInputData()!!.data, inputData.strides[0], inputData.strides[1], container)
                        NV12 -> {
                            tempOutput.update(inputData.width, inputData.height)
                            YUVConvert.NV21ToI420(inputData.width, inputData.height, inputData.data, inputData.strides[0], inputData.strides[1], tempOutput.getDataContainer())
                            YUVConvert.I420ToNV12(inputData.width, inputData.height, tempOutput.getOutput(), inputData.width, inputData.width / 2, inputData.width / 2, container)
                        }
                    }
                }
                RGBA -> {
                    when (output.format) {
                        RGBA -> YUVConvert.RGBAFormat(inputData.width, inputData.height, inputData.data, inputData.strides[0], container)
                        NV21 -> YUVConvert.RGBAToNV21(inputData.width, inputData.height, inputData.data, inputData.strides[0], container)
                        NV12 -> YUVConvert.RGBAToNV12(inputData.width, inputData.height, inputData.data, inputData.strides[0], container)
                        YUV420 -> YUVConvert.RGBAToI420(inputData.width, inputData.height, inputData.data, inputData.strides[0], container)
                    }
                }
            }
        }
    }
}
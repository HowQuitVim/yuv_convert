package com.zmy.yuv_convert;

import static com.zmy.yuv_convert.Format.YUV420;

import com.zmy.yuv_convert.input.InputData;
import com.zmy.yuv_convert.input.Input;
import com.zmy.yuv_convert.output.Output;
import com.zmy.yuv_convert.output.PackedByteBufferOutput;

import java.nio.ByteBuffer;

public class Converter<T> {
    private Input<T> input;
    private PackedByteBufferOutput tempOutput;


    public Converter(Input<T> input) {
        this.input = input;
    }

    private PackedByteBufferOutput getTempOutput() {
        if (tempOutput == null) {
            tempOutput = new PackedByteBufferOutput(YUV420);
        }
        return tempOutput;
    }

    public <O> void convert(Output<O> output) {
        InputData inputData = input.getInputData();
        if (inputData == null) {
            return;
        }
        output.update(inputData.getWidth(), inputData.getHeight());
        ByteBuffer container = output.getDataContainer();
        switch (inputData.getFormat()) {
            case YUV420:
                switch (output.getFormat()) {
                    case YUV420:
                        YUVConvert.I420Format(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], inputData.getStrides()[1], inputData.getStrides()[2], container);
                        break;
                    case RGBA:
                        YUVConvert.I420ToRGBA(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], inputData.getStrides()[1], inputData.getStrides()[2], container);
                        break;
                    case NV12:
                        YUVConvert.I420ToNV12(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], inputData.getStrides()[1], inputData.getStrides()[2], container);
                        break;
                    case NV21:
                        YUVConvert.I420ToNV21(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], inputData.getStrides()[1], inputData.getStrides()[2], container);
                        break;
                }

                break;
            case NV12:
                switch (output.getFormat()) {
                    case NV12:
                        YUVConvert.NV12Format(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], inputData.getStrides()[1], container);
                        break;
                    case YUV420:
                        YUVConvert.NV12ToI420(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], inputData.getStrides()[1], container);
                        break;
                    case RGBA:
                        YUVConvert.NV12ToRGBA(inputData.getWidth(), inputData.getHeight(), input.getInputData().getData(), inputData.getStrides()[0], inputData.getStrides()[1], container);
                        break;
                    case NV21:
                        getTempOutput().update(inputData.getWidth(), inputData.getHeight());
                        YUVConvert.NV12ToI420(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], inputData.getStrides()[1], getTempOutput().getDataContainer());
                        YUVConvert.I420ToNV21(inputData.getWidth(), inputData.getHeight(), getTempOutput().getOutput(), inputData.getWidth(), inputData.getWidth() / 2, inputData.getWidth() / 2, container);
                        break;
                }
                break;
            case NV21:
                switch (output.getFormat()) {
                    case NV21:
                        YUVConvert.NV21Format(inputData.getWidth(), inputData.getHeight(), input.getInputData().getData(), inputData.getStrides()[0], inputData.getStrides()[1], container);
                        break;
                    case YUV420:
                        YUVConvert.NV21ToI420(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], inputData.getStrides()[1], container);
                        break;
                    case RGBA:
                        YUVConvert.NV21ToRGBA(inputData.getWidth(), inputData.getHeight(), input.getInputData().getData(), inputData.getStrides()[0], inputData.getStrides()[1], container);
                        break;
                    case NV12:
                        getTempOutput().update(inputData.getWidth(), inputData.getHeight());
                        YUVConvert.NV21ToI420(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], inputData.getStrides()[1], getTempOutput().getDataContainer());
                        YUVConvert.I420ToNV12(inputData.getWidth(), inputData.getHeight(), getTempOutput().getOutput(), inputData.getWidth(), inputData.getWidth() / 2, inputData.getWidth() / 2, container);
                        break;

                }
                break;
            case RGBA:
                switch (output.getFormat()) {
                    case RGBA:
                        YUVConvert.RGBAFormat(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], container);
                        break;
                    case NV21:
                        YUVConvert.RGBAToNV21(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], container);
                        break;
                    case NV12:
                        YUVConvert.RGBAToNV12(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], container);
                        break;
                    case YUV420:
                        YUVConvert.RGBAToI420(inputData.getWidth(), inputData.getHeight(), inputData.getData(), inputData.getStrides()[0], container);
                        break;
                }
                break;
        }
    }

}

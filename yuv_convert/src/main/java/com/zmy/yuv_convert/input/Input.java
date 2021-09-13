package com.zmy.yuv_convert.input;

import com.zmy.yuv_convert.Format;

public abstract class Input<T> {
    private InputData inputData;

    public InputData getInputData() {
        if (inputData == null)
            throw new IllegalStateException("please call provide function first");
        return inputData;
    }

    void setInputData(InputData inputData) {
        this.inputData = inputData;
    }

    public abstract void provide(T data, Format format, int width, int height, int[] stride);

}

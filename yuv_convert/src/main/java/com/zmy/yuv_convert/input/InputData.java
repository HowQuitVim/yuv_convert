package com.zmy.yuv_convert.input;

import com.zmy.yuv_convert.Format;

import java.nio.ByteBuffer;

public class InputData {
    private ByteBuffer data;
    private Format format;
    private int width;
    private int height;
    private int[] strides;

    public InputData(ByteBuffer data, Format format, int width, int height, int[] strides) {
        if (data == null) {
            throw new IllegalArgumentException("data must be non null");
        }
        if (data.capacity() < format.getMinFrameSize(width, height)) {
            throw new IllegalArgumentException("data s invalid");
        }
        if (!format.checkStride(strides, width, height)) {
            throw new IllegalArgumentException("strides is invalid");
        }
        this.data = data;
        this.data.position(0);
        this.format = format;
        this.width = width;
        this.height = height;
        this.strides = strides;
    }

    public ByteBuffer getData() {
        return data;
    }

    public Format getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getStrides() {
        return strides;
    }
}

package com.zmy.yuv_convert.output;

import com.zmy.yuv_convert.Format;

import java.nio.ByteBuffer;

public abstract class Output<T> {
    protected Format format;
    protected ByteBuffer container;
    protected int width = 0;
    protected int height = 0;

    public Output(Format format) {
        this.format = format;
    }

    public Format getFormat() {
        return format;
    }

    public ByteBuffer getDataContainer() {
        int size = format.getMinFrameSize(width, height);
        if (container == null || container.capacity() < size) {
            container = ByteBuffer.allocateDirect(size);
        }
        return container;
    }

   public abstract T getOutput();

    public void update(int width, int height) {
        this.width = width;
        this.height = height;
    }
}

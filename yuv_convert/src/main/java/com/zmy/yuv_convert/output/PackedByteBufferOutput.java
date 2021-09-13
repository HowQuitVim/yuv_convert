package com.zmy.yuv_convert.output;

import com.zmy.yuv_convert.Format;

import java.nio.ByteBuffer;

public class PackedByteBufferOutput extends Output<ByteBuffer> {
    public PackedByteBufferOutput(Format format) {
        super(format);
    }

    @Override
    public ByteBuffer getOutput() {
        container.position(0);
        return container;
    }
}

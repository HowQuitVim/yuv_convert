package com.zmy.yuv_convert.input;

import com.zmy.yuv_convert.Format;

import java.nio.ByteBuffer;

public class PackedByteBufferInput extends Input<ByteBuffer> {
    private ByteBuffer buffer;

    @Override
    public void provide(ByteBuffer data, Format format, int width, int height, int[] stride) {
        int targetCapacity = Math.max(format.getMinFrameSize(width, height), data.capacity());
        if (buffer == null || buffer.capacity() < targetCapacity) {
            buffer = ByteBuffer.allocateDirect(Math.max(format.getMinFrameSize(width, height), data.capacity()));
        }
        buffer.position(0);
        data.position(0);
        buffer.put(data);
        setInputData(new InputData(buffer, format, width, height, stride));
    }
}

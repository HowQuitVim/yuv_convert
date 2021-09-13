package com.zmy.yuv_convert.input;

import com.zmy.yuv_convert.Format;

import java.nio.ByteBuffer;

public class PackedByteArrayInput extends Input<byte[]> {
    private ByteBuffer buffer;

    @Override
    public void provide(byte[] data, Format format, int width, int height, int[] stride) {
        int targetCapacity = Math.max(format.getMinFrameSize(width, height), data.length);
        if (buffer == null || buffer.capacity() < targetCapacity) {
            buffer = ByteBuffer.allocateDirect(targetCapacity);
        }
        buffer.position(0);
        buffer.put(data, 0, data.length);
        setInputData(new InputData(buffer, format, width, height, stride));
    }
}

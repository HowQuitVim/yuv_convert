package com.zmy.yuv_convert.input;

import com.zmy.yuv_convert.Format;

import java.nio.ByteBuffer;

public class ByteBufferInput extends Input<ByteBuffer[]> {
    private ByteBuffer buffer;

    @Override
    public void provide(ByteBuffer[] data, Format format, int width, int height, int[] stride) {
        int totalLength = 0;
        for (ByteBuffer datum : data) {
            totalLength += datum.capacity();

        }
        int totalCapacity = Math.max(totalLength, format.getMinFrameSize(width, height));
        if (buffer == null || buffer.capacity() < totalCapacity) {
            buffer = ByteBuffer.allocateDirect(totalCapacity);
        }
        int start = 0;
        for (int component = 0; component < data.length; component++) {
            ByteBuffer it = data[component];
            it.position(0);
            it.limit(it.capacity());
            buffer.position(start);
            buffer.limit(start + it.capacity());
            buffer.put(it);
            start += Math.max(it.capacity(), format.getComponentCapacity(component, width, height));
        }
        setInputData(new InputData(buffer, format, width, height, stride));
    }
}

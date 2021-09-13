package com.zmy.yuv_convert.input;

import com.zmy.yuv_convert.Format;

import java.nio.ByteBuffer;

public class ByteArrayInput extends Input<byte[][]> {
    private ByteBuffer buffer;

    @Override
    public void provide(byte[][] data, Format format, int width, int height, int[] stride) {
        int totalLength = 0;
        for (byte[] datum : data) {
            totalLength += datum.length;
        }
        int totalCapacity = Math.max(totalLength, format.getMinFrameSize(width, height));
        if (buffer==null||buffer.capacity() < totalCapacity){
            buffer = ByteBuffer.allocateDirect(totalCapacity);
        }
        int start = 0;
        for (int component = 0; component < data.length; component++) {
            byte[] inputData = data[component];
            buffer .position(start);
            buffer .limit(start + inputData.length);
            buffer .put(inputData);
            start += Math.max(inputData.length, format.getComponentCapacity(component, width, height));
        }
        setInputData(new InputData(buffer, format, width, height, stride));
    }
}

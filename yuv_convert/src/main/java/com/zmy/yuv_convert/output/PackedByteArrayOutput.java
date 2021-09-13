package com.zmy.yuv_convert.output;

import com.zmy.yuv_convert.Format;

public class PackedByteArrayOutput extends Output<byte[]> {
    byte array[];

    public PackedByteArrayOutput(Format format) {
        super(format);
    }

    @Override
    public byte[] getOutput() {
        if (array == null || array.length < format.getMinFrameSize(width, height)) {
            array = new byte[format.getMinFrameSize(width, height)];
        }
        container.position(0);
        container.limit(container.capacity());
        container.get(array);
        return array;
    }
}

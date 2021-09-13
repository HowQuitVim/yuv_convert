package com.zmy.yuv_convert.output;

import com.zmy.yuv_convert.Format;

import java.nio.ByteBuffer;

public class ByteBufferOutput extends Output<ByteBuffer[]> {
    private ByteBuffer ret[];

    public ByteBufferOutput(Format format) {
        super(format);
    }

    @Override
    public ByteBuffer[] getOutput() {
        int start = 0;
        ret = new ByteBuffer[format.getComponentCount()];
        for (int component = 0; component < ret.length; component++) {
            container.position(start);
            container.limit(start + format.getComponentCapacity(component, width, height));
            start += format.getComponentCapacity(component, width, height);
            ret[component] = container.slice();
        }
        return ret;
    }
}

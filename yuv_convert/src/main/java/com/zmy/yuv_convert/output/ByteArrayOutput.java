package com.zmy.yuv_convert.output;

import com.zmy.yuv_convert.Format;

import java.util.ArrayList;
import java.util.List;

public class ByteArrayOutput extends Output<byte[][]> {
    private final List<byte[]> ret = new ArrayList<>();

    public ByteArrayOutput(Format format) {
        super(format);
    }


    @Override
   public byte[][] getOutput() {
        for (int i = 0; i < format.getComponentCount(); i++) {
            if (i >= ret.size()) {
                ret.add(new byte[format.getComponentCapacity(i, width, height)]);
            } else {
                if (ret.get(i).length < format.getComponentCapacity(i, width, height)) {
                    ret.set(i, new byte[format.getComponentCapacity(i, width, height)]);
                }
            }
        }
        int start = 0;
        for (int component = 0; component < format.getComponentCount(); component++) {
            byte[] buffer = ret.get(component);
            container.position(start);
            int size = format.getComponentCapacity(component, width, height);
            container.limit(start + size);
            start += format.getComponentCapacity(component, width, height);
            container.get(buffer);
        }

        return (byte[][]) ret.toArray();
    }
}

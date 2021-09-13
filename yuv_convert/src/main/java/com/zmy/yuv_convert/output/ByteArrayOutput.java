package com.zmy.yuv_convert.output;

import com.zmy.yuv_convert.Format;

public class ByteArrayOutput extends Output<byte[][]> {
    private byte[][] data;

    public ByteArrayOutput(Format format) {
        super(format);
    }


    @Override
    public byte[][] getOutput() {
        ensureDataCapacity();
        int start = 0;
        for (int component = 0; component < format.getComponentCount(); component++) {
            container.position(start);
            int size = format.getComponentCapacity(component, width, height);
            container.limit(start + size);
            start += format.getComponentCapacity(component, width, height);
            container.get(data[component]);
        }
        return data;
    }

    //确保data有足够的容量
    private void ensureDataCapacity() {
        if (data == null || data.length != format.getComponentCount()) {
            data = new byte[format.getComponentCount()][];
        }
        for (int component = 0; component < format.getComponentCount(); component++) {
            if (data[component] == null || data[component].length < format.getComponentCapacity(component, width, height)) {
                data[component] = new byte[format.getComponentCapacity(component, width, height)];
            }
        }
    }
}

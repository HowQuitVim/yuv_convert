package com.zmy.yuv_convert;

public enum Format {
    RGBA {
        @Override
        public int getComponentCapacity(int component, int width, int height) {
            return width * height * 4;
        }

        @Override
        public int getComponentCount() {
            return 1;
        }

        @Override
        public int[] getStandardStride(int width, int height) {
            return new int[]{width * 4};
        }
    },
    YUV420 {
        @Override
        public int getComponentCapacity(int component, int width, int height) {
            int capacity = width * height / 4;
            if (component == 0) {
                capacity = width * height;
            }
            return capacity;
        }

        @Override
        public int getComponentCount() {
            return 3;
        }

        @Override
        public int[] getStandardStride(int width, int height) {
            return new int[]{width, width / 2, width / 2};
        }
    },
    NV12 {
        @Override
        public int getComponentCapacity(int component, int width, int height) {
            int capacity = width * height / 2;
            if (component == 0) {
                capacity = width * height;
            }
            return capacity;
        }

        @Override
        public int getComponentCount() {
            return 2;
        }

        @Override
        public int[] getStandardStride(int width, int height) {
            return new int[]{width, width};
        }
    },
    NV21 {
        @Override
        public int getComponentCapacity(int component, int width, int height) {
            int capacity = width * height / 2;
            if (component == 0) {
                capacity = width * height;
            }
            return capacity;
        }

        @Override
        public int getComponentCount() {
            return 2;
        }

        @Override
        public int[] getStandardStride(int width, int height) {
            return new int[]{width, width};
        }
    };

    public abstract int getComponentCapacity(int component, int width, int height);

    public abstract int getComponentCount();

    public abstract int[] getStandardStride(int width, int height);

    public int getMinFrameSize(int width, int height) {
        int size = 0;
        for (int i = 0; i < getComponentCount(); i++) {
            size += getComponentCapacity(i, width, height);

        }
        return size;
    }

    public boolean checkStride(int[] strides, int width, int height) {
        if (strides.length < getComponentCount()) {
            return false;
        }

        for (int component = 0; component < getComponentCount(); component++) {
            if (strides[component] < getStandardStride(width,height)[component]) {
                return false;
            }
        }
        return true;
    }

}

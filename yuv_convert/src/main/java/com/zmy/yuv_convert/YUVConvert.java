package com.zmy.yuv_convert;

import java.nio.ByteBuffer;

public class YUVConvert {
    static {
        System.loadLibrary("yuv_converter");
    }

    //yuv420 to other format
    static native int I420Format(int width, int height, ByteBuffer yuv, int strideY, int strideU, int strideV, ByteBuffer out);

    static native int I420ToNV21(int width, int height, ByteBuffer yuv, int strideY, int strideU, int strideV, ByteBuffer out);

    static native int I420ToNV12(int width, int height, ByteBuffer yuv, int strideY, int strideU, int strideV, ByteBuffer out);

    static native int I420ToRGBA(int width, int height, ByteBuffer yuv, int strideY, int strideU, int strideV, ByteBuffer out);

    //nv12 to other format
    static native int NV12Format(int width, int height, ByteBuffer yuv, int strideY, int strideUV, ByteBuffer out);

    static native int NV12ToI420(int width, int height, ByteBuffer yuv, int strideY, int strideUV, ByteBuffer out);

    static native int NV12ToRGBA(int width, int height, ByteBuffer yuv, int strideY, int strideUV, ByteBuffer out);

    //nv21 to other format
    static native int NV21Format(int width, int height, ByteBuffer yuv, int strideY, int strideUV, ByteBuffer out);

    static native int NV21ToI420(int width, int height, ByteBuffer yuv, int strideY, int strideUV, ByteBuffer out);


    static native int NV21ToRGBA(int width, int height, ByteBuffer yuv, int strideY, int strideUV, ByteBuffer out);


    //rgba to other format
    static native int RGBAFormat(int width, int height, ByteBuffer rgba, int stride, ByteBuffer out);

    static native int RGBAToI420(int width, int height, ByteBuffer rgba, int stride, ByteBuffer out);

    static native int RGBAToNV12(int width, int height, ByteBuffer rgba, int stride, ByteBuffer out);

    static native int RGBAToNV21(int width, int height, ByteBuffer rgba, int stride, ByteBuffer out);


}

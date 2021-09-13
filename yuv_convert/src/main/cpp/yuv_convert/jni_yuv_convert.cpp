//
// Created by zmy on 21-1-11.
//

#include "jni.h"
#include "android/log.h"
#include "libyuv.h"

extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_I420Format(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject yuv, jint stride_y, jint stride_u,
                                                 jint stride_v, jobject out) {
    auto src_y = (uint8_t *) env->GetDirectBufferAddress(yuv);
    auto src_u = src_y + height * stride_y;
    auto src_v = src_u + height / 2 * stride_u;

    auto dst_y = (uint8_t *) env->GetDirectBufferAddress(out);
    auto dst_u = dst_y + height * width;
    auto dst_v = dst_u + height / 2 * width / 2;
    return libyuv::I420Copy(src_y, stride_y, src_u, stride_u, src_v, stride_v, dst_y, width, dst_u,
                            width / 2, dst_v, width / 2, width, height);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_I420ToNV21(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject yuv, jint stride_y, jint stride_u,
                                                 jint stride_v, jobject out) {
    auto src_y = (uint8_t *) env->GetDirectBufferAddress(yuv);
    auto src_u = src_y + height * stride_y;
    auto src_v = src_u + height / 2 * stride_u;

    auto dst_y = (uint8_t *) env->GetDirectBufferAddress(out);
    auto dst_uv = dst_y + height * width;
    return libyuv::I420ToNV21(src_y, stride_y, src_u, stride_u, src_v, stride_v, dst_y, width,
                              dst_uv, width, width, height);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_I420ToNV12(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject yuv, jint stride_y, jint stride_u,
                                                 jint stride_v, jobject out) {
    auto src_y = (uint8_t *) env->GetDirectBufferAddress(yuv);
    auto src_u = src_y + height * stride_y;
    auto src_v = src_u + height / 2 * stride_u;

    auto dst_y = (uint8_t *) env->GetDirectBufferAddress(out);
    auto dst_uv = dst_y + height * width;
    return libyuv::I420ToNV12(src_y, stride_y, src_u, stride_u, src_v, stride_v, dst_y, width,
                              dst_uv, width, width, height);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_I420ToRGBA(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject yuv, jint stride_y, jint stride_u,
                                                 jint stride_v, jobject out) {
    auto src_y = (uint8_t *) env->GetDirectBufferAddress(yuv);
    auto src_u = src_y + height * stride_y;
    auto src_v = src_u + height / 2 * stride_u;

    auto dst_rgba = (uint8_t *) env->GetDirectBufferAddress(out);
    return libyuv::I420ToABGR(src_y, stride_y, src_u, stride_u, src_v, stride_v, dst_rgba,
                              width * 4, width, height);
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_NV12Format(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject yuv, jint stride_y, jint stride_uv,
                                                 jobject out) {
    auto src_y = (uint8_t *) env->GetDirectBufferAddress(yuv);
    auto src_uv = src_y + height * stride_y;

    auto dst_y = (uint8_t *) env->GetDirectBufferAddress(out);
    auto dst_uv = dst_y + height * width;
    return libyuv::NV12Copy(src_y, stride_y, src_uv, stride_uv, dst_y, width, dst_uv, width, width,
                            height);
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_NV12ToI420(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject yuv, jint stride_y, jint stride_uv,
                                                 jobject out) {
    auto src_y = (uint8_t *) env->GetDirectBufferAddress(yuv);
    auto src_uv = src_y + height * stride_y;


    auto dst_y = (uint8_t *) env->GetDirectBufferAddress(out);
    auto dst_u = dst_y + height * width;
    auto dst_v = dst_u + height * width / 4;

    return libyuv::NV12ToI420(src_y, stride_y, src_uv, stride_uv, dst_y, width, dst_u, width / 2,
                              dst_v, width / 2, width, height);
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_NV12ToRGBA(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject yuv, jint stride_y, jint stride_uv,
                                                 jobject out) {
    auto src_y = (uint8_t *) env->GetDirectBufferAddress(yuv);
    auto src_uv = src_y + height * stride_y;

    auto dst_rgba = (uint8_t *) env->GetDirectBufferAddress(out);

    return libyuv::NV12ToABGR(src_y, stride_y, src_uv, stride_uv, dst_rgba, width * 4, width,
                              height);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_NV21Format(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject yuv, jint stride_y, jint stride_uv,
                                                 jobject out) {
    auto src_y = (uint8_t *) env->GetDirectBufferAddress(yuv);
    auto src_uv = src_y + height * stride_y;

    auto dst_y = (uint8_t *) env->GetDirectBufferAddress(out);
    auto dst_uv = dst_y + height * width;
    return libyuv::NV21Copy(src_y, stride_y, src_uv, stride_uv, dst_y, width, dst_uv, width, width,
                            height);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_NV21ToI420(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject yuv, jint stride_y, jint stride_uv,
                                                 jobject out) {
    auto src_y = (uint8_t *) env->GetDirectBufferAddress(yuv);
    auto src_uv = src_y + height * stride_y;


    auto dst_y = (uint8_t *) env->GetDirectBufferAddress(out);
    auto dst_u = dst_y + height * width;
    auto dst_v = dst_u + height / 2 * width / 2;

    return libyuv::NV21ToI420(src_y, stride_y, src_uv, stride_uv, dst_y, width, dst_u, width / 2,
                              dst_v, width / 2, width, height);

}

extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_NV21ToRGBA(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject yuv, jint stride_y, jint stride_uv,
                                                 jobject out) {
    auto src_y = (uint8_t *) env->GetDirectBufferAddress(yuv);
    auto src_uv = src_y + height * stride_y;

    auto dst_rgba = (uint8_t *) env->GetDirectBufferAddress(out);
    return libyuv::NV21ToABGR(src_y, stride_y, src_uv, stride_uv, dst_rgba, width * 4, width,
                              height);
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_RGBAFormat(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject rgba, jint stride, jobject out) {
    auto src_rgba = (uint8_t *) env->GetDirectBufferAddress(rgba);
    auto dst_rgba = (uint8_t *) env->GetDirectBufferAddress(out);
    return libyuv::ARGBCopy(src_rgba, stride, dst_rgba, width * 4, width, height);
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_RGBAToI420(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject rgba, jint stride, jobject out) {
    auto src_rgba = (uint8_t *) env->GetDirectBufferAddress(rgba);

    auto dst_y = (uint8_t *) env->GetDirectBufferAddress(out);
    auto dst_u = dst_y + height * width;
    auto dst_v = dst_u + height / 2 * width / 2;
    return libyuv::ABGRToI420(src_rgba, stride, dst_y, width, dst_u, width / 2, dst_v, width / 2,
                              width, height);
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_RGBAToNV12(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject rgba, jint stride, jobject out) {
    auto src_rgba = (uint8_t *) env->GetDirectBufferAddress(rgba);

    auto dst_y = (uint8_t *) env->GetDirectBufferAddress(out);
    auto dst_uv = dst_y + height * width;
    return libyuv::ABGRToNV12(src_rgba, stride, dst_y, width, dst_uv, width, width, height);
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_zmy_yuv_1convert_YUVConvert_RGBAToNV21(JNIEnv *env, jclass clazz, jint width, jint height,
                                                 jobject rgba, jint stride, jobject out) {
    auto src_rgba = (uint8_t *) env->GetDirectBufferAddress(rgba);

    auto dst_y = (uint8_t *) env->GetDirectBufferAddress(out);
    auto dst_uv = dst_y + height * width;
    return libyuv::ABGRToNV21(src_rgba, stride, dst_y, width, dst_uv, width, width, height);
}
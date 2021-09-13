cmake_minimum_required(VERSION 3.4.1)
set(CMAKE_BUILD_TYPE Release)

include_directories(yuv_convert/include)
link_directories(${CMAKE_SOURCE_DIR}/../jniLibs/${CMAKE_ANDROID_ARCH_ABI}/)
add_library(
        yuv_converter
        SHARED
        yuv_convert/jni_yuv_convert.cpp
)

target_link_libraries(yuv_converter
        log
        yuv
        )

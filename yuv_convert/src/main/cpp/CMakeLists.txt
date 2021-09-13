cmake_minimum_required(VERSION 3.4.1)
set(CMAKE_BUILD_TYPE Release)

add_subdirectory(libyuv)


add_library(
        yuv_converter
        SHARED
        yuv_convert/jni_yuv_convert.cpp
)

target_include_directories(yuv_converter PUBLIC libyuv/include)

target_link_libraries(yuv_converter
        log
        yuv
        )

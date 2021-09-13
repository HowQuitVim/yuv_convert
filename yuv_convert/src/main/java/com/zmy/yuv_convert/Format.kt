package com.zmy.yuv_convert

enum class Format {
    RGBA {
        override fun getComponentCapacity(component: Int, width: Int, height: Int): Int {
            return width * height * 4
        }
        override fun getComponentCount() = 1
        override fun getStandardStride(width: Int, height: Int) = intArrayOf(width * 4)
    },
    YUV420 {
        override fun getComponentCapacity(component: Int, width: Int, height: Int): Int {
            return if (component == 0) {
                width * height
            } else {
                width * height / 4
            }
        }
        override fun getComponentCount() = 3
        override fun getStandardStride(width: Int, height: Int) = intArrayOf(width, width / 2, width / 2)
    },
    NV12 {
        override fun getComponentCapacity(component: Int, width: Int, height: Int): Int {
            return if (component == 0) {
                width * height
            } else {
                width * height / 2
            }
        }
        override fun getComponentCount() = 2
        override fun getStandardStride(width: Int, height: Int) = intArrayOf(width, width)
    },
    NV21 {
        override fun getComponentCapacity(component: Int, width: Int, height: Int): Int {
            return if (component == 0) {
                width * height
            } else {
                width * height / 2
            }
        }
        override fun getComponentCount() = 2
        override fun getStandardStride(width: Int, height: Int) = intArrayOf(width, width)
    };

    abstract fun getComponentCapacity(component: Int, width: Int, height: Int): Int
    abstract fun getComponentCount(): Int
    abstract fun getStandardStride(width: Int, height: Int): IntArray
    fun getMinFrameSize(width: Int, height: Int): Int {
        var size = 0
        for (i in 0 until this.getComponentCount()) {
            size += getComponentCapacity(i, width, height)
        }
        return size
    }
}
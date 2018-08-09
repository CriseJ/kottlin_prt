package com.crise.kottlin_pt_library.utils

import android.content.Context

/**
 * 注释：
 * ===========================
 * Author by Jack
 * on 2018/7/30 14:34
 */
open class ScreenUtil {

    companion object {

        /**
         *
         * @description 这种方法很简单，只有一行代码，查过参考手册后可以翻译一下：
         * 状态栏高度=取大于其的最小整数（25*上下文_获取应用包的资源实例_获取当前屏幕尺寸_屏幕密度比例）
         * 其中density并不是真实的屏幕密度，而是一个相对密度，基准密度为160dpi，
         * 比如我测试的手机为HTC one m8，查的屏幕密度为441dpi，
         * 相对160为2.75，density就取为3。各分辨率的density取值为：
         * 所以得到的状态栏高度为25*3=75
         * 由这种方法得到的状态栏高度具有较大局限性，
         * 比如因为某种需要去掉状态栏或本身没有状态栏，此时状态栏高度应为0，
         * 但是该方法依然能够得到一个非零的状态栏高度。
         * @author
         * */
        open fun getStatusBarHeight0(context: Context): Double {
            return Math.ceil((25 * context.resources.displayMetrics.density).toDouble())
        }

        /**
         * 这里我们用到了getIdentifier()的方法来获取资源的ID，其中第一个参数是要获取资源对象的名称，
         * 比如我们要获取状态栏的相关内容，这里填入"status_bar_height"；第二个参数是我们要获取什么属性，
         * 我们要获取高度内容，所以填入"dimen"；第三个是包名，状态栏是系统内容，故填入“android”。
         * 另外一个用到的办法是getDimensionPixelSize()，由函数名就能知道是根据资源ID获得资源像素尺寸，
         * 这里就直接获得状态栏的高度。
         * 这种方法在状态栏不存在的时候就会获得其高度为0.
         * */
        open fun getStatusBarHeight1(context: Context): Int {
            var result = 0
            val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId)
            }
            return result
        }
    }








}
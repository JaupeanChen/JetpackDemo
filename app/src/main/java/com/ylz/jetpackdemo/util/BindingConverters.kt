package com.ylz.jetpackdemo.util

import android.view.View
import androidx.databinding.BindingConversion

/**
 * Author: ChenGuiPing
 * Date: 2021/9/30
 * Description:
 */
object BindingConverters {

    @BindingConversion
    @JvmStatic
    fun booleanToVisibility(isNotVisible: Boolean): Int {
        return if (isNotVisible) View.GONE else View.VISIBLE
    }

}

object ConverterUtil {
    @JvmStatic
    fun isZero(number: Int): Boolean {
        return number == 0
    }
}
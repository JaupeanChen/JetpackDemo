package com.ylz.jetpackdemo.util

import android.widget.ImageView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

/**
 * Author: ChenGuiPing
 * Date: 2021/9/30
 * Description:
 */
@BindingMethods(
    value = [
        BindingMethod(
            type = ImageView::class,
            attribute = "srcCompat",
            method = "setImageResource"
        )]
)
class BindingMethods
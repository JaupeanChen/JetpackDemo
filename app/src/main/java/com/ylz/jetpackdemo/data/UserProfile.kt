package com.ylz.jetpackdemo.data

import androidx.databinding.ObservableInt

/**
 * Author: ChenGuiPing
 * Date: 2021/9/29
 * Description:
 */
data class UserProfile(
    val name: String,
    val lastName: String,
    val likes: ObservableInt
)

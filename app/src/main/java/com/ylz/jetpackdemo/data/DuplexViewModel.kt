package com.ylz.jetpackdemo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Author: ChenGuiPing
 * Date: 2021/10/13
 * Description:
 */
class DuplexViewModel : ViewModel() {
    private val isRefreshingInter = MutableLiveData(false)

    val isRefreshing: LiveData<Boolean> = isRefreshingInter

    fun onRefresh() {
        isRefreshingInter.value = !isRefreshingInter.value!!
    }

}
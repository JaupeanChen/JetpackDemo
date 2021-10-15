package com.ylz.jetpackdemo.util

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Author: ChenGuiPing
 * Date: 2021/10/13
 * Description:
 */
object TwoBindingAdapters {

    //第一步，单向绑定：DataSource -> UI
    @JvmStatic
    @BindingAdapter("bind_refreshing")
    fun setSwipeRefresh(swipeRefreshLayout: SwipeRefreshLayout, newValue: Boolean) {
        //状态不同时才进行UI更新，防止死循环
        if (swipeRefreshLayout.isRefreshing != newValue) {
            swipeRefreshLayout.isRefreshing = newValue
        }
    }

    //第二步，观察View的状态变更
    //当用户刷新时将状态传递给InverseBindingListener处理
    @JvmStatic
    @BindingAdapter("bind_refreshingAttrChanged", requireAll = false)
    fun setOnRefreshListener(
        swipeRefreshLayout: SwipeRefreshLayout,
        inverseBindingListener: InverseBindingListener?
    ) {
        if (inverseBindingListener != null) {
            swipeRefreshLayout.setOnRefreshListener {
                inverseBindingListener.onChange()
            }
        }
    }

    //第三步，通过InverseBindingAdapter提交数据给LiveData
    //UI -> DataSource
    @JvmStatic
    @InverseBindingAdapter(
        attribute = "bind_refreshing",
        event = "bind_refreshingAttrChanged"
    )
    fun getSwipeRefreshLayoutRefreshing(swipeRefreshLayout: SwipeRefreshLayout): Boolean {
        return swipeRefreshLayout.isRefreshing
    }

}
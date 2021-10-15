package com.ylz.jetpackdemo.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * Author: ChenGuiPing
 * Date: 2021/10/13
 * Description:
 */
class EventLiveData<T>: MutableLiveData<T>() {

    fun observe(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
        observe(owner, wrapObserver(sticky, observer))
    }

    private fun wrapObserver(sticky: Boolean, observer: Observer<in T>): Observer<T> {
        return EventObserverWrapper(this, sticky, observer)
    }
}
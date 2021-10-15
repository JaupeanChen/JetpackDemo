package com.ylz.jetpackdemo.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * Author: ChenGuiPing
 * Date: 2021/10/13
 * Description:
 */
object XEventBus {

    private val channels = HashMap<String, EventLiveData<*>>()

    private fun <T> with(eventName: String): EventLiveData<*> {
        synchronized(channels) {
            if (!channels.containsKey(eventName)){
                channels[eventName] = EventLiveData<T>()
            }
            return (channels[eventName] as EventLiveData<*>)
        }
    }

    fun <T> observe(owner: LifecycleOwner, eventName: String, sticky: Boolean = false,
                    observer: Observer<Any?>){
        with<T>(eventName).observe(owner, sticky, observer)
    }

    fun <T> post(eventName: String, message: T){
        val eventLiveData = with<T>(eventName)
//        eventLiveData.postValue(message!!)
        eventLiveData.value = message!!
    }

}
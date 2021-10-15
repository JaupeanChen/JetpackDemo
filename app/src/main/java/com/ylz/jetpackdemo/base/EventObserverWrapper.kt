package com.ylz.jetpackdemo.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.ylz.jetpackdemo.util.ReflectHelper

/**
 * Author: ChenGuiPing
 * Date: 2021/10/13
 * Description: 通过代理Observer，根据调用方是否接收粘性消息
 */
class EventObserverWrapper<T>(
    liveData: MutableLiveData<T>, sticky: Boolean,
    private val delegate: Observer<in T>
) : Observer<T> {

    //是否阻止下条消息发送
    private var isPreventNext = false

    companion object {
        private const val START_VERSION = -1
    }

    init {
        if (!sticky) {
            //根据反射拿到当前LiveData的信号版本
//            val javaClass = liveData.javaClass
//            val declaredField = javaClass.getDeclaredField("mVersion")
//            val version = declaredField.get(liveData) as Int
            val version = -1

//            val version = ReflectHelper.of(liveData).getField("mVersion")
//                    as? Int ?: START_VERSION

            //如果当前的LiveData的信号版本大于-1，也就是说已经有发送过消息了，
            // 那么我们就拒绝接收当次（其实也就是注册之后的next次）消息
            isPreventNext = version > START_VERSION
        }
    }


    override fun onChanged(t: T) {
        if (isPreventNext) {
            //当次粘性消息不接收，但下次开始就正常接收
            isPreventNext = false
            return
        }
        delegate.onChanged(t)
    }
}
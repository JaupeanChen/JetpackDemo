package com.ylz.jetpackdemo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData

/**
 * Author: ChenGuiPing
 * Date: 2021/10/12
 * Description:
 */
class DataViewModel(): BaseViewModel() {
    private val dataInternal = MutableLiveData<String>()
    val result: LiveData<String> = dataInternal

    fun getData(): LiveData<String> {
        launchOnMain {
            //do http data get...
            // val response = RetrofitClient.getXXX.getXXX(1)
            // if (response.isSuccess) {
            //  dataInternal.value = response.data.toString()
            // }
            dataInternal.value = "哈哈哈"
        }
        return result
    }

    //利用LiveData的协程构造器
    val resultData = liveData {
        //do http data get...
        // val response = RetrofitClient.getXXX.getXXX(1)
        //...
        emit("玛玛哈哈")
    }

}
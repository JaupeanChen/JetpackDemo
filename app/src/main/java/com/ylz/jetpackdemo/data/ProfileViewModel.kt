package com.ylz.jetpackdemo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * Author: ChenGuiPing
 * Date: 2021/9/30
 * Description:
 */
class ProfileViewModel : ViewModel() {

    //val是因为本身的MutableLiveData是不变的，持有的数据变化而已
    private val _name = MutableLiveData("Jay")
    private val _lastName = MutableLiveData("Javan")
    private val _likes = MutableLiveData(0)

    //对外提供不能更改的数据（LiveData），保证ViewModel的封装性
    val name: LiveData<String> = _name
    val lastName: LiveData<String> = _lastName
    val likes: LiveData<Int> = _likes

    val popularity: LiveData<Popular> = Transformations.map(
        _likes
    ) {
        when {
            it > 9 -> Popular.STAR
            it > 4 -> Popular.POPULAR
            else -> Popular.NORMAL
        }
    }

    fun onLike() {
        _likes.value = (_likes.value ?: 0) + 1
    }
}

enum class Popular {
    NORMAL,
    POPULAR,
    STAR
}
package com.ylz.jetpackdemo.base;

/**
 * Author: ChenGuiPing
 * Date: 2021/10/14
 * Description:
 */
public class MutableLiveData<T> extends LiveData<T> {

    public MutableLiveData(T value) {
        super(value);
    }

    public MutableLiveData() {
        super();
    }

    public void postValue(T value) {
        super.postValue(value);
    }

    public void setValue(T value) {
        super.postValue(value);
    }

}

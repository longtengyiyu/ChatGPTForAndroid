package com.ltyy.chatgpt.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel<T> extends ViewModel {
    protected final String TAG = BaseViewModel.class.getSimpleName();

    /**
     * data
     */
    private MutableLiveData<T> data = new MutableLiveData<>();
    /**
     * error data
     */
    private MutableLiveData<String> errorMsg = new MutableLiveData<>();

    public MutableLiveData<T> getData(){
        return data;
    }

    public MutableLiveData<String> getErrorMsg(){
        return errorMsg;
    }


}

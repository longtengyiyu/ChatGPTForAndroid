package com.ltyy.chatgpt.base;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ltyy.chatgpt.utils.LogUtils;

public abstract class BaseMVVMActivity<V extends BaseViewModel, B extends ViewDataBinding, D> extends BaseAbstractActivity implements Observer<D> {
  protected V viewModel;
  protected B binding;
  protected float statusBarHeight;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    LogUtils.i(TAG, "onCreate");
    super.onCreate(savedInstanceState);
//    initStatusBarHeight();
    initViewModel();
    setViewModel();
  }


  private void initViewModel(){
    binding = DataBindingUtil.setContentView(this, getContentLayoutRes());
    viewModel = new ViewModelProvider(this).get(getModelClass());
    viewModel.getData().observe(this, this);
    viewModel.getErrorMsg().observe(this, (Observer<String>) msg -> onResponseFail(msg));
    binding.setLifecycleOwner(this);
  }

  protected abstract Class<V> getModelClass();

  protected void setViewModel(){ }

  private void initStatusBarHeight(){
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      statusBarHeight = getResources().getDimension(resourceId);
      Log.d(TAG, "statusBarHeight:" + statusBarHeight);

    }
  }

  @Override
  public void onChanged(D d) {
    onResponseSuccess(d);
  }

  protected void onResponseSuccess(D d){ }

  /**
   * load failed
   * @param msg error
   */
  protected void onResponseFail(String msg){
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    return super.dispatchTouchEvent(ev);
  }

  @Override
  protected void onDestroy() {
    LogUtils.i(TAG, "onDestroy");
    super.onDestroy();
    if (viewModel != null){
      viewModel.getData().removeObservers(this);
    }
  }
}

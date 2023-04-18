package com.ltyy.chatgpt.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

import com.ltyy.chatgpt.R;
import com.ltyy.chatgpt.utils.AppDpiUtils;

public abstract class BaseMVVMFragmentDialog<B extends ViewDataBinding> extends DialogFragment {

    protected B binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppDpiUtils.setCustomDensity(getResources());
        return onSetContentView(inflater, container, getFragmentLayoutRes());
    }

    protected View onSetContentView(LayoutInflater inflater, ViewGroup container, int layoutId) {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Dialog(requireContext(), getTheme()){
            @Override
            public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
                if(ev.getAction() == MotionEvent.ACTION_UP){
                    getActivity().dispatchTouchEvent(ev);
                }
                return super.dispatchTouchEvent(ev);
            }
        };
    }

    protected abstract int getFragmentLayoutRes();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adjustWindow();
    }

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(onDismissListener != null){
            onDismissListener.onDismiss(dialog);
        }
    }

    private void adjustWindow() {
        if(getContext() == null){ return; }
        Context context = getContext();
        Dialog dialog = getDialog();
        if(dialog != null){
            dialog.setCancelable(windowCancelable());
            dialog.setCanceledOnTouchOutside(windowCancelable());

            Window window = dialog.getWindow();
            if(window != null){
                window.setBackgroundDrawable(backgroundDrawable());
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = windowWidth();
                lp.height = windowHeight();
                lp.dimAmount = windowDim();
                window.setAttributes(lp);
            }

        }
    }

    protected boolean windowCancelable() {
        return false;
    }

    protected Drawable backgroundDrawable() {
        return new ColorDrawable(Color.TRANSPARENT);
    }

    protected int windowWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    protected int windowHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected float windowDim() {
        return 0.6f;
    }

}

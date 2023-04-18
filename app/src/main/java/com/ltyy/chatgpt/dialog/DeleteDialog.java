package com.ltyy.chatgpt.dialog;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ltyy.chatgpt.R;
import com.ltyy.chatgpt.base.BaseMVVMFragmentDialog;
import com.ltyy.chatgpt.databinding.DialogDeleteBinding;
import com.ltyy.chatgpt.listener.ConfirmListener;

public class DeleteDialog extends BaseMVVMFragmentDialog<DialogDeleteBinding> {

    private ConfirmListener listener;
    public static DeleteDialog newInstance(){
        return new DeleteDialog();
    }

    public void setListener(ConfirmListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getFragmentLayoutRes() {
        return R.layout.dialog_delete;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.tvCancel.setOnClickListener(v -> dismissAllowingStateLoss());
        binding.tvConfirm.setOnClickListener(v -> {
            dismissAllowingStateLoss();
            if (listener != null){
                listener.onConfirm();
            }
        });
    }
}

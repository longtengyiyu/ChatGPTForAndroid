package com.ltyy.chatgpt.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder <T> extends RecyclerView.ViewHolder{
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindViewData(T data);

}

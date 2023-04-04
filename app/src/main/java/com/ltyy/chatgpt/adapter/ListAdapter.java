package com.ltyy.chatgpt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ltyy.chatgpt.base.BaseAdapter;
import com.ltyy.chatgpt.base.BaseViewHolder;
import com.ltyy.chatgpt.bean.Icon;
import com.ltyy.chatgpt.databinding.ItemChatBinding;

public class ListAdapter extends BaseAdapter<BaseViewHolder<Icon>, Icon> {

    public ListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<Icon> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChatBinding binding = ItemChatBinding.inflate(layoutInflater);
        return new ViewHolder(binding.getRoot(), binding);
    }

    public class ViewHolder extends BaseViewHolder<Icon> {
        private ItemChatBinding binding;
        public ViewHolder(@NonNull View itemView, ItemChatBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        @Override
        public void bindViewData(Icon data) {
            binding.tvName.setText(data.getName());
            binding.icIcon.setImageResource(data.getRes());
        }
    }
}

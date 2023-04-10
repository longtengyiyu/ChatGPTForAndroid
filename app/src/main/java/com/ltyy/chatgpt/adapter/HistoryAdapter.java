package com.ltyy.chatgpt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ltyy.chatgpt.activity.ChatHistoryActivity;
import com.ltyy.chatgpt.base.BaseAdapter;
import com.ltyy.chatgpt.base.BaseViewHolder;
import com.ltyy.chatgpt.databinding.ItemHistoryBinding;
import com.ltyy.chatgpt.entity.Group;
import com.ltyy.chatgpt.utils.CommonUtils;

public class HistoryAdapter extends BaseAdapter<BaseViewHolder<Group>, Group> {

    public HistoryAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<Group> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    public class ViewHolder extends BaseViewHolder<Group> {

        private ItemHistoryBinding binding;

        public ViewHolder(@NonNull View itemView, ItemHistoryBinding binding) {
            super(itemView);
            this.binding = binding;
            itemView.setOnClickListener(v ->ChatHistoryActivity.startChatHistoryActivity(context,
                    getItemData(getAdapterPosition()).getId()));
        }

        @Override
        public void bindViewData(Group group) {
            binding.tvContent.setText(group.getContent());
            binding.tvTimestamp.setText(CommonUtils.getCurrentDateFormat());
        }
    }
}

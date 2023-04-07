package com.ltyy.chatgpt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.ltyy.chatgpt.base.BaseAdapter;
import com.ltyy.chatgpt.base.BaseViewHolder;
import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.databinding.ItemMsgBinding;
import com.ltyy.chatgpt.widget.ViewUtils;

public class ChatAdapter extends BaseAdapter<BaseViewHolder<Chat>, Chat> {
    public ChatAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<Chat> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMsgBinding binding = ItemMsgBinding.inflate(layoutInflater,parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    public class ViewHolder extends BaseViewHolder<Chat> {

        ItemMsgBinding binding;
        public ViewHolder(View itemView, ItemMsgBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        @Override
        public void bindViewData(Chat data) {
            ViewUtils.setGone(binding.tvLeftMsg, binding.tvRightMsg, binding.tvCenterMsg);
            if (data.isLeftMessage()){
                ViewUtils.setVisible(binding.tvLeftMsg);
                binding.tvLeftMsg.setText(data.getContent());
            } else if (data.isRightMessage()){
                ViewUtils.setVisible(binding.tvRightMsg);
                binding.tvRightMsg.setText(data.getContent());
            } else {
                ViewUtils.setVisible(binding.tvCenterMsg);
                binding.tvCenterMsg.setText(data.getContent());
            }
        }
    }
}

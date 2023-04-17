package com.ltyy.chatgpt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.ltyy.chatgpt.base.BaseAdapter;
import com.ltyy.chatgpt.base.BaseViewHolder;
import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.databinding.ItemMsgBinding;
import com.ltyy.chatgpt.listener.OnItemClickListener;
import com.ltyy.chatgpt.utils.FlashingAnimatorUtils;
import com.ltyy.chatgpt.widget.ViewUtils;

public class ChatAdapter extends BaseAdapter<BaseViewHolder<Chat>, Chat> {

    private OnItemClickListener onItemClickListener;

    public ChatAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<Chat> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMsgBinding binding = ItemMsgBinding.inflate(layoutInflater,parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends BaseViewHolder<Chat> {

        ItemMsgBinding binding;
        public ViewHolder(View itemView, ItemMsgBinding binding) {
            super(itemView);
            this.binding = binding;
            itemView.setOnLongClickListener(v -> {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
                return true;
            });
        }

        @Override
        public void bindViewData(Chat data) {
            ViewUtils.setGone(binding.tvLeftMsg, binding.tvRightMsg, binding.tvCenterMsg, binding.viewInput, binding.aiLayout);
            FlashingAnimatorUtils.stopFlash();
            if (data.isLeftMessage()){
                ViewUtils.setVisible(binding.tvLeftMsg, binding.aiLayout);
                binding.tvLeftMsg.setText(data.getContent());
            } else if (data.isRightMessage()){
                ViewUtils.setVisible(binding.tvRightMsg);
                binding.tvRightMsg.setText(data.getContent());
            } else if (data.isInput()){
                ViewUtils.setVisible(binding.viewInput, binding.aiLayout);
                FlashingAnimatorUtils.flash(binding.viewInput);
            } else {
                ViewUtils.setVisible(binding.tvCenterMsg);
                binding.tvCenterMsg.setText(data.getContent());
            }
        }
    }
}

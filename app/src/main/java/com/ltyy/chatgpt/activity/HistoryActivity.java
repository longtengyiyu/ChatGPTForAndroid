package com.ltyy.chatgpt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ltyy.chatgpt.R;
import com.ltyy.chatgpt.adapter.HistoryAdapter;
import com.ltyy.chatgpt.app.AppConstants;
import com.ltyy.chatgpt.base.BaseMVVMActivity;
import com.ltyy.chatgpt.databinding.ActivityHistoryBinding;
import com.ltyy.chatgpt.dialog.DeleteDialog;
import com.ltyy.chatgpt.entity.Group;
import com.ltyy.chatgpt.listener.ConfirmListener;
import com.ltyy.chatgpt.viewmodel.HistoryViewModel;

import java.util.List;

public class HistoryActivity extends BaseMVVMActivity<HistoryViewModel, ActivityHistoryBinding, List<Group>> {

    private HistoryAdapter adapter;
    private DeleteDialog dialog;
    private int currentPosition;
    private int currentDeletePosition;

    public static void startHistoryActivity(Context context){
        context.startActivity(new Intent(context, HistoryActivity.class));
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_history;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();
        binding.chatBar.setTitle(R.string.history_chat);
        binding.chatBar.hideBack(false);
    }

    @Override
    protected Class<HistoryViewModel> getModelClass() {
        return HistoryViewModel.class;
    }

    private void initAdapter(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            currentPosition = position;
            if (currentPosition >= 0){
                getDialog().show(HistoryActivity.this.getSupportFragmentManager(), "deleteDialog");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getGroupList();
    }

    private DeleteDialog getDialog(){
        if (dialog == null){
            dialog = DeleteDialog.newInstance();
            dialog.setListener(() -> {
                Group group = adapter.getItemData(currentPosition);
                long groupId = group.getId();
                viewModel.removeGroupById(groupId);
                getDialog().dismissAllowingStateLoss();
                adapter.removeItem(currentPosition);
            });
        }
        return dialog;
    }

    @Override
    protected void onResponseSuccess(List<Group> groups) {
        adapter.removeAll();
        if (groups != null && !groups.isEmpty()){
            adapter.addItems(groups);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResponseFail(String msg) {

    }
}

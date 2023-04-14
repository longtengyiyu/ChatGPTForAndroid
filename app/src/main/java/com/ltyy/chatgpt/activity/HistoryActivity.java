package com.ltyy.chatgpt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ltyy.chatgpt.R;
import com.ltyy.chatgpt.adapter.HistoryAdapter;
import com.ltyy.chatgpt.base.BaseMVVMActivity;
import com.ltyy.chatgpt.databinding.ActivityHistoryBinding;
import com.ltyy.chatgpt.entity.Group;
import com.ltyy.chatgpt.viewmodel.HistoryViewModel;

import java.util.List;

public class HistoryActivity extends BaseMVVMActivity<HistoryViewModel, ActivityHistoryBinding, List<Group>> {

    private HistoryAdapter adapter;

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
        viewModel.getGroupList();
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
    }

    @Override
    protected void onResponseSuccess(List<Group> groups) {
        if (groups != null && !groups.isEmpty()){
            adapter.addItems(groups);
        }
    }

    @Override
    protected void onResponseFail(String msg) {

    }
}

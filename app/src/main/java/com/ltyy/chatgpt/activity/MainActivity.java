package com.ltyy.chatgpt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ltyy.chatgpt.R;
import com.ltyy.chatgpt.adapter.ListAdapter;
import com.ltyy.chatgpt.app.AppApplication;
import com.ltyy.chatgpt.base.BaseViewDataBindingActivity;
import com.ltyy.chatgpt.bean.Icon;
import com.ltyy.chatgpt.databinding.ActivityMainBinding;
import com.ltyy.chatgpt.listener.OnItemClickListener;
import com.ltyy.chatgpt.widget.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseViewDataBindingActivity<ActivityMainBinding> {

    private ListAdapter adapter;

    private List<Icon> ds = new ArrayList<>();

    public static void startMainActivity(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatBar();
        initData();
        initRy();
    }

    private void chatBar(){
        binding.chatBar.setTitle(R.string.main);
        binding.chatBar.hideSetting(false);
        binding.chatBar.getSetting().setOnClickListener(v ->{
            SettingActivity.startSettingActivity(MainActivity.this);
        });
    }

    private void initData(){
        {
            Icon icon = new Icon();
            icon.setName(AppApplication.getContext().getString(R.string.history_chat));
            icon.setRes(R.drawable.ic_history_chat);
            ds.add(icon);
        }
        {
            Icon icon = new Icon();
            icon.setName(AppApplication.getContext().getString(R.string.new_chat));
            icon.setRes(R.drawable.ic_new_chat);
            ds.add(icon);
        }
    }

    private void initRy(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        adapter.addItems(ds);
        adapter.setListener(position -> {
            switch (position){
                case 0:
                    HistoryActivity.startHistoryActivity(MainActivity.this);
                    break;
                case 1:
                    ChatActivity.startChatActivity(MainActivity.this);
                    break;
            }
        });
    }


}
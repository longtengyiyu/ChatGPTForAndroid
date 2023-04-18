package com.ltyy.chatgpt.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter <VH extends BaseViewHolder<T>, T> extends RecyclerView.Adapter<VH>{

    protected final String TAG = getClass().getSimpleName();

    protected final Context context;
    protected final LayoutInflater layoutInflater;

    public BaseAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    protected List<T> dataList = new ArrayList<>();

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bindViewData(getItemData(position));
    }

    public T getItemData(int position) {
        return (position >= 0 && position < dataList.size()) ? dataList.get(position) : null;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addItems(List<T> data) {
        addItems(data, dataList.size());
    }

    public void addItem(T data) {
        addItem(data, dataList.size());
    }

    public void addItem(T data, int position) {
        if (position >= 0 && position <= dataList.size()) {
            dataList.add(position, data);
            notifyItemInserted(position);
        }
    }

    public void addItems(List<T> data, int position) {
        if (position >= 0 && position <= dataList.size() && data != null && data.size() > 0) {
            dataList.addAll(position, data);
            notifyItemRangeChanged(position, data.size());
        }
    }

    public void removeItem(int position){
        if (position >= 0 && position < dataList.size()) {
            dataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeAll(){
        if (!dataList.isEmpty()){
            dataList.clear();
        }
    }
}

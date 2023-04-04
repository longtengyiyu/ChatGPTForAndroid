package com.ltyy.chatgpt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SharedPreferencesUtils {
    private static final String TAG = SharedPreferencesUtils.class.getSimpleName();

    private static class SharedPreferencesHelperHolder {
        private static final SharedPreferencesUtils appSharedPreferencesHelper = new SharedPreferencesUtils();
    }

    public static SharedPreferencesUtils getInstance() {
        return SharedPreferencesHelperHolder.appSharedPreferencesHelper;
    }

    private final Object lock = new Object();
    private volatile SharedPreferences sPreferences = null;
    private SharedPreferencesUtils() { }

    /**
     * 需要在APP启动时注册一下,最好是在Application里面注册
     * @param context 上下文
     */
    public void init(Context context) {
        if (sPreferences == null) {
            synchronized (SharedPreferencesUtils.class){
                if (sPreferences == null) {
                    sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                }
            }
        }
    }

    public void init(Context context, String spName) {
        if (sPreferences == null) {
            synchronized (SharedPreferencesUtils.class){
                if (sPreferences == null) {
                    sPreferences = context.getSharedPreferences(spName + "_preferences", Context.MODE_PRIVATE);
                }
            }
        }
    }

    public int getInt(String key) {
        int res = 0;
        synchronized (lock){
            res = sPreferences.getInt(key, 0);
        }
        return res;
    }

    public int getInt(String key, int defaultValue) {
        int res = 0;
        synchronized (lock){
            res = sPreferences.getInt(key, defaultValue);
        }
        return res;
    }

    public long getLong(String key) {
        long res = 0;
        synchronized (lock){
            res = sPreferences.getLong(key, 0L);
        }
        return res;
    }

    public long getLong(String key, long defaultValue) {
        long res = 0;
        synchronized (lock){
            res = sPreferences.getLong(key, defaultValue);
        }
        return res;
    }

    public String getString(String key) {
        String res = null;
        synchronized (lock){
            res = sPreferences.getString(key, "");
        }
        return res;
    }

    public String getString(String key, String defaultValue) {
        String res = null;
        synchronized (lock){
            res = sPreferences.getString(key, defaultValue);
        }
        return res;
    }

    public double getDouble(String key) {
        String res = null;
        synchronized (lock){
            res = sPreferences.getString(key, "");
        }
        if(null == res || "".equals(res)){ return 0; }

        try {
            return Double.parseDouble(res);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void putInt(String key, int value) {
        boolean res;
        synchronized (lock){
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.putInt(key, value);
            res = editor.commit();
        }
        if(!res){
            LogUtils.e(TAG,"sharedPreferences committed failed!");
        }
    }

    public void putLong(String key, long value) {
        boolean res;
        synchronized (lock){
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.putLong(key, value);
            res = editor.commit();
        }
        if(!res){
            LogUtils.e(TAG,"sharedPreferences committed failed!");
        }
    }

    public void putDouble(String key, double value) {
        String tmp = String.valueOf(value);
        boolean res;
        synchronized (lock){
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.putString(key, tmp);
            res = editor.commit();
        }
        if(!res){
            LogUtils.e(TAG,"sharedPreferences committed failed!");
        }
    }

    public void putString(String key, String value) {
        boolean res;
        synchronized (lock){
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.putString(key, value);
            res = editor.commit();
        }
        if(!res){
            LogUtils.e(TAG,"sharedPreferences committed failed!");
        }
    }

    public void putList(String key, List<String> list) {
        String tmp = "";
        if (list != null && list.size() > 0) {
            String[] lists = list.toArray(new String[]{});
            // the comma like character used below is not a comma it is the SINGLE
            // LOW-9 QUOTATION MARK unicode 201A and unicode 2017 they are used for
            // seprating the items in the list
            tmp = TextUtils.join("‚‗‚", lists);
        } else{ }

        boolean res;
        synchronized (lock){
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.putString(key, tmp);
            res = editor.commit();
        }
        if(!res){
            LogUtils.e(TAG,"sharedPreferences committed failed!");
        }
    }

    public ArrayList<String> getList(String key) {
        String value;
        synchronized (lock){

            // the comma like character used below is not a comma it is the SINGLE
            // LOW-9 QUOTATION MARK unicode 201A and unicode 2017 they are used for
            // seprating the items in the list
            value = sPreferences.getString(key, "");
        }
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        String[] lists = TextUtils.split(value, "‚‗‚");
        return new ArrayList<>(Arrays.asList(lists));
    }

    public void putListInt(String key, ArrayList<Integer> list) {
        String tmp = "";
        if (list != null && list.size() > 0) {
            Integer[] lists = list.toArray(new Integer[]{});
            // the comma like character used below is not a comma it is the SINGLE
            // LOW-9 QUOTATION MARK unicode 201A and unicode 2017 they are used for
            // seprating the items in the list
            tmp = TextUtils.join("‚‗‚", lists);
        } else{ }

        boolean res;
        synchronized (lock){
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.putString(key, tmp);
            res = editor.commit();
        }
        if(!res){
            LogUtils.e(TAG,"sharedPreferences committed failed!");
        }
    }

    public ArrayList<Integer> getListInt(String key) {
        String value;
        synchronized (lock){

            // the comma like character used below is not a comma it is the SINGLE
            // LOW-9 QUOTATION MARK unicode 201A and unicode 2017 they are used for
            // seprating the items in the list
            value = sPreferences.getString(key, "");
        }
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        String[] lists = TextUtils.split(value, "‚‗‚");
        ArrayList<Integer> res = new ArrayList<>();
        for (String list : lists) {
            res.add(Integer.parseInt(list));
        }
        return res;
    }

    public void putBoolean(String key, boolean value) {
        boolean res;
        synchronized (lock){
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.putBoolean(key, value);
            res = editor.commit();
        }

        if(!res){
            LogUtils.e(TAG,"sharedPreferences committed failed!");
        }
    }

    public boolean getBoolean(String key) {
        boolean res;
        synchronized (lock){
            res = sPreferences.getBoolean(key, false);
        }
        return res;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        boolean res;
        synchronized (lock){
            res = sPreferences.getBoolean(key, defaultValue);
        }
        return res;
    }

    public void putFloat(String key, float value) {
        boolean res;
        synchronized (lock){
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.putFloat(key, value);
            res = editor.commit();
        }

        if(!res){
            LogUtils.e(TAG,"sharedPreferences committed failed!");
        }
    }

    public boolean containKey(String key) {
        boolean res = false;
        synchronized (lock){
            res = sPreferences.contains(key);
        }
        return res;
    }

    public float getFloat(String key) {
        float res;
        synchronized (lock){
            res = sPreferences.getFloat(key, 0f);
        }
        return res;
    }

    public void remove(String key) {
        boolean res;
        synchronized (lock){
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.remove(key);
            res = editor.commit();
        }

        if(!res){
            LogUtils.e(TAG,"sharedPreferences committed failed!");
        }
    }

    public void clear() {
        boolean res;
        synchronized (lock){
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.clear();
            res = editor.commit();
        }

        if(!res){
            LogUtils.e(TAG,"sharedPreferences committed failed!");
        }
    }

    public Map<String, ?> getAll() {
        Map<String, ?> res;
        synchronized (lock){
            res = sPreferences.getAll();
        }
        return res;
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
    
    
}

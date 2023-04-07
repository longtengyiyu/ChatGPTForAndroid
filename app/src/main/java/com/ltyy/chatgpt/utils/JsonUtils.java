package com.ltyy.chatgpt.utils;

import com.google.gson.Gson;

public class JsonUtils {

    private static volatile JsonUtils INSTANCE = null;
    private Gson gson;

    private JsonUtils(){
        gson = new Gson();
    }

    private static void init(){
        if (INSTANCE == null){
            synchronized (JsonUtils.class){
                if (INSTANCE == null){
                    INSTANCE = new JsonUtils();
                }
            }
        }
    }

    public static JsonUtils get(){
        init();
        return INSTANCE;
    }

    public String toJson(Object o){
        return gson.toJson(o);
    }

}

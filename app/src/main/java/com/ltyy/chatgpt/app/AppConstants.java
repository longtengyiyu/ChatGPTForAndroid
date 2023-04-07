package com.ltyy.chatgpt.app;

public class AppConstants {
    public static String APP_ROOT_PATH = AppApplication.getContext().getFilesDir().getAbsolutePath();

    public final static String APP_DB_PATH = APP_ROOT_PATH + "/cache/";

    public final static String APP_DB = APP_DB_PATH + "chat-db";
}

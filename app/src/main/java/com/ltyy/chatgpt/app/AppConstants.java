package com.ltyy.chatgpt.app;

public class AppConstants {
    public static String APP_ROOT_PATH = AppApplication.getContext().getFilesDir().getAbsolutePath();

    public final static String APP_DB_PATH = APP_ROOT_PATH + "/cache/";

    public final static String APP_DB = APP_DB_PATH + "chat-db";

    public static final String MSG_EMPTY = "msg_empty";

    public static final String NEED_TURN = "needTurn";

    public static final String GROUP_ID = "groupId";

    public static final long DEFAULT_TIMEOUT = 60;
    public static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;//10MB

    public static final String HTTP_INPUT_TYPE = "application/json";

    public static final String CHAT_STOP ="[DONE]";

    public static final int REQUEST_CODE_HISTORY = 1001;
}

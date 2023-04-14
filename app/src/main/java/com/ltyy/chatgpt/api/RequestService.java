package com.ltyy.chatgpt.api;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.ltyy.chatgpt.app.AppConstants;
import com.ltyy.chatgpt.app.AppSPContact;
import com.ltyy.chatgpt.bean.Message;
import com.ltyy.chatgpt.bean.Prompt;
import com.ltyy.chatgpt.bean.PromptRes;
import com.ltyy.chatgpt.param.PromptParams;
import com.ltyy.chatgpt.utils.JsonUtils;
import com.ltyy.chatgpt.utils.LogUtils;
import com.ltyy.chatgpt.utils.SharedPreferencesUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.zhxu.okhttps.HTTP;
import cn.zhxu.okhttps.HttpResult;
import cn.zhxu.okhttps.gson.GsonMsgConvertor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class RequestService {
    private static final String TAG = RequestService.class.getSimpleName();
    private static volatile RequestService INSTANCE = null;

    private HTTP http;
    private String apiKey;
    private String apiHost;
    private RequestService(){
        http = HTTP.builder()
                .config(builder -> {
                    builder.readTimeout(60000, TimeUnit.SECONDS);
                    builder.connectTimeout(60000, TimeUnit.SECONDS);
                    builder.writeTimeout(60000, TimeUnit.SECONDS);
                })
                .addMsgConvertor(new GsonMsgConvertor())
                .bodyType("application/json")
                .build();
        apiKey = SharedPreferencesUtils.getInstance().getString(AppSPContact.SP_PARAM_API_KEY);
        apiHost = SharedPreferencesUtils.getInstance().getString(AppSPContact.SP_PARAM_API_HOST,
                "https://api.openai.com/");
    }

    public static void create(){
        if (INSTANCE == null){
            synchronized (RequestService.class){
                if (INSTANCE == null){
                    INSTANCE = new RequestService();
                }
            }
        }
    }

    public static RequestService getInstance(){
        return INSTANCE;
    }

    private RequestBody createRequestBody(String json) {
        LogUtils.d(TAG, json);
        return RequestBody.create(MediaType.parse(AppConstants.HTTP_INPUT_TYPE), json);
    }

    public void getPrompt(MutableLiveData<String> data, MutableLiveData<String> error,PromptParams param) {
        http.async(apiHost + "v1/chat/completions").
                addHeader("Authorization", "Bearer " + apiKey).
                addHeader("Content-Type", "application/json").
                setBodyPara(param).
                setOnResponse((HttpResult res) -> {
                    InputStream inputStream = res.getBody().toByteStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    boolean stop = false;
                    while (!stop) {
                        try {
                            String line = reader.readLine();
                            if (TextUtils.isEmpty(line))continue;
                            if (AppConstants.CHAT_STOP.equals(line)) {
                                stop = true;
                                data.postValue(AppConstants.CHAT_STOP);
                            }else {
                                PromptRes promptRes = JsonUtils.
                                        get().
                                        getGson().
                                        fromJson(line.replace("data: ", ""), PromptRes.class);
                                LogUtils.d(TAG, "promptRes-->" + JsonUtils.get().toJson(promptRes));
                                if (promptRes != null && promptRes.getChoices() != null &&
                                        !promptRes.getChoices().isEmpty()) {
                                    List<Prompt> prompts = promptRes.getChoices();
                                    for (Prompt prompt : prompts) {
                                        if (prompt != null) {
                                            Message message = prompt.getDelta();
                                            if (message != null) {
                                                String content = message.getContent();
                                                if (!TextUtils.isEmpty(content)) {
                                                    LogUtils.d(TAG, "content-->" + content);
                                                    data.postValue(content);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Thread.sleep(500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).
                post();
    }

}

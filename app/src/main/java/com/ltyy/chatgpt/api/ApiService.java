package com.ltyy.chatgpt.api;

import com.ltyy.chatgpt.bean.PromptRes;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("v1/completions")
    Observable<PromptRes> getPrompt(@Body RequestBody body);
}

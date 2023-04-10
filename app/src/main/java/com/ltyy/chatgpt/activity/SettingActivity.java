package com.ltyy.chatgpt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.ltyy.chatgpt.R;
import com.ltyy.chatgpt.api.ApiMethods;
import com.ltyy.chatgpt.app.AppConstants;
import com.ltyy.chatgpt.app.AppSPContact;
import com.ltyy.chatgpt.base.BaseViewDataBindingActivity;
import com.ltyy.chatgpt.databinding.ActivitySettingBinding;
import com.ltyy.chatgpt.utils.CommonUtils;
import com.ltyy.chatgpt.utils.LogUtils;
import com.ltyy.chatgpt.utils.TextCheckedUtils;
import com.ltyy.chatgpt.utils.ToastUtils;

public class SettingActivity extends BaseViewDataBindingActivity<ActivitySettingBinding> {

    public static void startSettingActivity(Context context){
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra(AppConstants.NEED_TURN, false);
        context.startActivity(intent);
    }

    private boolean showPwd = false;
    private boolean needTurn = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.chatBar.setTitle(R.string.setting);
        load();
        if (getIntent() != null){
            needTurn = getIntent().getBooleanExtra(AppConstants.NEED_TURN, false);
        }
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_setting;
    }

    private void load(){
        String apiKey = getSharedPreferencesUtils().getString(AppSPContact.SP_PARAM_API_KEY);
        LogUtils.d(TAG, "apiKey:" + apiKey);
        if (!TextUtils.isEmpty(apiKey) && needTurn){
            finishAndStartActivity();
        }
    }

    public void save(View v){
        hideSoft(v);
        String apiKey = binding.etApiKey.getText().toString();
        String apiHost = binding.etApiHost.getText().toString();
        if (TextCheckedUtils.checked(apiKey)){
            getSharedPreferencesUtils().putString(AppSPContact.SP_PARAM_API_KEY, apiKey);
            if (TextUtils.isEmpty(apiHost)){
                apiHost = "https://api.openai.com/";
            }
            getSharedPreferencesUtils().putString(AppSPContact.SP_PARAM_API_HOST, apiHost);
            ApiMethods.instance(SettingActivity.this);
            finishAndStartActivity();
        }
    }

    public void cancel(View v){
        hideSoft(v);
        finishActivity();
    }

    private void finishAndStartActivity(){
        finishActivity();
        MainActivity.startMainActivity(this);
    }

    public void finishActivity(){
        finish();
    }

    public void press(View v){
        hideSoft(v);
        String apiKey = binding.etApiKey.getText().toString();
        if (TextCheckedUtils.checked(apiKey)){
            showPwd = !showPwd;
            updatePwdIcon();
            updatePwdTextStyle();
            binding.etApiKey.setSelection(apiKey.length());
        }
    }

    public void hideSoft(View v){
        CommonUtils.hideSoftInput(this);
    }

    private void updatePwdIcon(){
        binding.ivPwdStatus.setImageResource(showPwd ? R.drawable.ic_show_pwd : R.drawable.ic_hide_pwd);
    }

    private void updatePwdTextStyle(){
        binding.etApiKey.setInputType(showPwd ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

}

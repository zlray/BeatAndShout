package com.xqlh.beatandshout.login;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.xqlh.beatandshout.Entity.EntityLogin;
import com.xqlh.beatandshout.MainActivity;
import com.xqlh.beatandshout.R;
import com.xqlh.beatandshout.base.BaseActivity;
import com.xqlh.beatandshout.brainwave.BrainWaveActivity;
import com.xqlh.beatandshout.util.Constants;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.bt_forget_password)
    Button bt_forget_password;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_password)
    EditText et_password;

    String json;
    private EntityLogin mUserInfo;

    @Override
    public int setContent() {
        return R.layout.activity_login;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.bt_login, R.id.bt_forget_password})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                Log.i(TAG, "OnClick: " + json);
                setWebSocketClient(Constants.BASE_URL, Constants.VALUE,
                        et_account.getText().toString().trim(), et_password.getText().toString().trim());
                break;
            case R.id.bt_forget_password:
                startActivity(new Intent(this, UpdatePasswordActivity.class));
                break;
        }
    }

    public void setWebSocketClient(String address, String value, String account, String password) {
        httpHeaders = new HashMap<String, String>();
        httpHeaders.put("Cookie", value);
        webSocketClient = new WebSocketClient(URI.create(address), new Draft_6455(), httpHeaders) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i(Constants.TAG, "onOpen: " + serverHandshake.getHttpStatusMessage() + serverHandshake.getHttpStatus());
            }

            @Override
            public void onMessage(String s) {
                Log.i(Constants.TAG, "onMessage: " + s);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i(Constants.TAG, "onClose: " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i(Constants.TAG, "onError: " + e.getMessage());
            }
        };
        mUserInfo = new EntityLogin();
        Log.i(TAG, "login account password：" + account + "  " + password);
        mUserInfo.setUserLoginName(account);
        mUserInfo.setUserLoginPassword(password);
        json = new Gson().toJson(mUserInfo, EntityLogin.class);
        try {
            webSocketClient.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (webSocketClient.isOpen()) {
            Log.i(TAG, "setWebSocketClient: " + json);
            webSocketClient.send("AdminLogin " + json);
        }
    }
}

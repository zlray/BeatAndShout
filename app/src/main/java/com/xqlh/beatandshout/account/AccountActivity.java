package com.xqlh.beatandshout.account;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.xqlh.beatandshout.Entity.EntityAccount;
import com.xqlh.beatandshout.R;
import com.xqlh.beatandshout.base.BaseActivity;
import com.xqlh.beatandshout.util.Constants;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountActivity extends BaseActivity {

    @BindView(R.id.bt)
    Button bt;
    private EntityAccount mEntityAccount;
    private String json;

    @Override
    public int setContent() {
        return R.layout.activity_account;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.bt})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt:
                setWebSocketClient(Constants.BASE_URL, Constants.VALUE);
                break;
        }
    }

    public void setWebSocketClient(String address, String value) {
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
        try {
            String name = URLDecoder.decode("张磊", "utf-8");
            String sex = URLDecoder.decode("男","utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mEntityAccount = new EntityAccount();
        mEntityAccount.setAdmin(true);
        mEntityAccount.setUserName("zl");
        mEntityAccount.setPassword("123456");
        mEntityAccount.setName("name");
        mEntityAccount.setUserId(1);
        mEntityAccount.setOpenId("1111");
        mEntityAccount.setRole("A");
        mEntityAccount.setHeight(172);
        mEntityAccount.setSex("男");
        mEntityAccount.setWeChatId("1111");
        mEntityAccount.setWeight(70);
        mEntityAccount.setBirthday(new Date(System.currentTimeMillis()));
        mEntityAccount.setRegDate(new Date(System.currentTimeMillis()));
        json = new Gson().toJson(mEntityAccount, EntityAccount.class);
        try {
            webSocketClient.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "setWebSocketClient: " + json);
        webSocketClient.send("UserAdd " + json);

//        UserDelete
//        UserUpdate
//        UserDelete
//        UserGet
    }
}

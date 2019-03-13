package com.xqlh.beatandshout;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xqlh.beatandshout.base.BaseActivity;
import com.xqlh.beatandshout.util.Constants;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    @Override
    public int setContent() {
        return R.layout.activity_main;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        setWebSocketClient(Constants.BASE_URL, Constants.VALUE);
    }

    @OnClick({})
    public void OnClick(View view) {
        switch (view.getId()) {

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
        webSocketClient.connect();
    }
}

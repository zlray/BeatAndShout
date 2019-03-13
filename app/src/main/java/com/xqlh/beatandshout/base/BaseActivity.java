package com.xqlh.beatandshout.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

import butterknife.ButterKnife;


public abstract class BaseActivity extends FragmentActivity {

    /**
     * 定义一个成员变量
     * Define BaseWebScoket  member arivable that is isFullScreen.
     * it's default value is false,which means not full-screen.
     */
    private boolean isFullScreen = false;
    protected Context mContext;
    public WebSocketClient webSocketClient;
    public Map<String, String> httpHeaders;
    public String TAG = "lz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 必须设置在加载布局前
         * Must be set before loading the Layout
         */
        isFullScreen = setFullScreen();

        if (isFullScreen) {
            /**
             * 设置为无标题，全屏
             *
             * setting to Untitled,Full Screen
             */
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );

        } else {
            /**
             * 设置为无标题
             * set to Untitled
             */
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        }
        setContentView(setContent());
        webSocketClient = new WebSocketClient(URI.create("")) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {

            }

            @Override
            public void onMessage(String message) {

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {

            }

            @Override
            public void onError(Exception ex) {

            }
        };
        //绑定注解
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        webSocketClient.close();
        Log.i(TAG, "onStop: " + "************");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webSocketClient.close();
        Log.i(TAG, "onDestroy: " + "************");
    }

    /**
     * @return
     * @description 加载该Acitivit的布局
     * loading the layout of current Activity
     */
    public abstract int setContent();

    /**
     * @description 是否设置当前Activity为全屏
     * Weather to set the current Activity to full screen
     */
    public abstract boolean setFullScreen();

    /**
     * @description 加载当前Activity的控件
     * Loading Controls of current Activity
     */
    public abstract void init();

}

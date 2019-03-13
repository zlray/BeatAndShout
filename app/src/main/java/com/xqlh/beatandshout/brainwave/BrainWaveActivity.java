package com.xqlh.beatandshout.brainwave;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;
import com.xqlh.beatandshout.Entity.EntityBrainWave;
import com.xqlh.beatandshout.Entity.EntityLogin;
import com.xqlh.beatandshout.R;
import com.xqlh.beatandshout.account.AccountActivity;
import com.xqlh.beatandshout.base.BaseActivity;
import com.xqlh.beatandshout.util.Constants;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class BrainWaveActivity extends BaseActivity {

    TGDevice tgDevice;
    BluetoothAdapter mBluetoothAdapter;

    @BindView(R.id.tv_infor)
    TextView tv_infor;
    @BindView(R.id.bt_connect)
    Button bt_connect;
    @BindView(R.id.bt)
    Button bt;
    private EntityBrainWave entityBrainWave;
    private String json;
    private boolean onClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setContent() {
        return R.layout.activity_brain_wave;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //实例化实体类
        entityBrainWave = new EntityBrainWave();
        //连接后台
        setWebSocketClient(Constants.BASE_URL, Constants.VALUE);

        //实体类添加数据，向后台发送
        checkBleDevice();

    }

    @OnClick({R.id.bt_connect,R.id.bt})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_connect:
                //如果连接成功
                if (onClick) {
                    Toast.makeText(this, "连接成功", Toast.LENGTH_LONG).show();

                } else {
                    tgDevice.connect(true);
                }
                break;
            case R.id.bt:
                startActivity(new Intent(this, AccountActivity.class));
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
            webSocketClient.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void checkBleDevice() {
        if (mBluetoothAdapter != null) {
            // create the TGDevice
            tgDevice = new TGDevice(mBluetoothAdapter, handler);
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(enableBtIntent);
            }

        } else {
            Toast.makeText(this, "该手机不支持蓝牙", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Handles messages from TGDevice
     */
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TGDevice.MSG_MODEL_IDENTIFIED: //TGDevice 已经连接，可以接受数据
                    tgDevice.setBlinkDetectionEnabled(true);
                    tgDevice.setTaskDifficultyRunContinuous(true);
                    tgDevice.setTaskDifficultyEnable(true);
                    tgDevice.setTaskFamiliarityRunContinuous(true);
                    tgDevice.setTaskFamiliarityEnable(true);
                    tgDevice.setRespirationRateEnable(true); /// not allowed on EEG hardware, here to show the override message
                    tgDevice.setPositivityEnable(true);
                    break;
                case TGDevice.MSG_STATE_CHANGE: //状态信息变化
                    switch (msg.arg1) {
                        case TGDevice.STATE_IDLE:
                            break;
                        case TGDevice.STATE_CONNECTING:
                            tv_infor.setText("正在连接");
                            break;
                        case TGDevice.STATE_CONNECTED:
                            tv_infor.setText("连接成功");
                            tgDevice.start();
                            onClick = true;
                            break;
                        case TGDevice.STATE_NOT_FOUND:
                            tv_infor.setText("脑波发带未扫描到");
                            break;
                        case TGDevice.STATE_ERR_NO_DEVICE:
                            tv_infor.setText("请打开脑波发带进行蓝牙匹配");
                            break;
                        case TGDevice.STATE_ERR_BT_OFF:
                            tv_infor.setText("手机蓝牙没打开或者不可用");
                            break;
                        case TGDevice.STATE_DISCONNECTED:
                            tv_infor.setText("断开连接");


                    } /* end switch on msg.ar0g1 */
                    break;

                case TGDevice.MSG_ATTENTION: //专注度等级
                    Log.i(TAG, "专注度" + msg.arg1);
                    entityBrainWave.setAttention(msg.arg1);
                    break;
                case TGDevice.MSG_MEDITATION:
                    Log.i(TAG, "冥想度" + msg.arg1);
                    entityBrainWave.setMeditation(msg.arg1);
                    break;
                case TGDevice.MSG_LOW_BATTERY:
                    tv_infor.setText("发带电量低，请及时更换电池");

                case TGDevice.MSG_EEG_POWER:
                    TGEegPower e = (TGEegPower) msg.obj;
                    entityBrainWave.setDelta((int) Math.log10(e.delta));
                    entityBrainWave.setTheta((int) Math.log10(e.theta));
                    entityBrainWave.setAlphaHigh((int) Math.log10(e.highAlpha));
                    entityBrainWave.setAlphaBottom((int) Math.log10(e.lowAlpha));
                    entityBrainWave.setBetaHigh((int) Math.log10(e.highBeta));
                    entityBrainWave.setBetaBottom((int) Math.log10(e.lowBeta));
                    entityBrainWave.setGammaHigh((int) Math.log10(e.midGamma));
                    entityBrainWave.setGammaBottom((int) Math.log10(e.lowGamma));

                    
                    json = new Gson().toJson(entityBrainWave, EntityBrainWave.class);
                    Log.i(TAG, "json: " + json);
                    if (webSocketClient.isOpen()){
                        webSocketClient.send("EEGByModel " + json);
                        entityBrainWave = new EntityBrainWave();
                    }

                    Log.i(TAG, "脑波");
                    Log.i(TAG, "delta" + (int) Math.log10(e.delta));
                    Log.i(TAG, "theta" + (int) Math.log10(e.theta));
                    Log.i(TAG, "highAlpha" + (int) Math.log10(e.highAlpha));
                    Log.i(TAG, "lowAlpha" + (int) Math.log10(e.lowAlpha));
                    Log.i(TAG, "highBeta" + (int) Math.log10(e.highBeta));
                    Log.i(TAG, "lowBeta" + (int) Math.log10(e.lowBeta));
                    Log.i(TAG, "midGamma" + (int) Math.log10(e.midGamma));
                    Log.i(TAG, "lowGamma" + (int) Math.log10(e.lowGamma));
                    Log.i(TAG, "脑波");
                    break;
                case TGDevice.MSG_BLINK:
//                    tv_infor.setText("眨眼: " + msg.arg1 + "\n");
//                    Log.i(TAG, "眨眼: " + msg.arg1);
                    break;
                case TGDevice.MSG_RELAXATION:
//                    instrumentView_relaxation.setProgress(msg.arg1);
//                    relaxation.add(msg.arg1);
//                    instrumentView_relaxation.setText("放松度");
                    break;
                case TGDevice.MSG_HEART_RATE:
                    Log.i(TAG, "心率: " + msg.arg1);
                    break;
                case TGDevice.MSG_RESPIRATION:
                    Log.i(TAG, "呼吸率: " + msg.arg1);
                    break;
                case TGDevice.MSG_HEART_AGE:
                    Log.i(TAG, "呼吸率: " + msg.arg1);
                    break;
                default:
                    break;

            }
            /* end switch on msg.what */

        } /* end handleMessage() */


    }; /* end Handler */

    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        //if (!bluetoothAdapter.isEnabled()) {
        //  Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //startActivityForResult(enableIntent, 1);
        //}
    }

    @Override
    public void onPause() {
        // tgDevice.close();
        super.onPause();
    }

    @Override
    public void onStop() {
        tgDevice.close();
        super.onStop();

    }
}

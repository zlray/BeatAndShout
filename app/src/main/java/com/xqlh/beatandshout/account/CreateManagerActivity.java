package com.xqlh.beatandshout.account;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xqlh.beatandshout.Entity.EntityAccount;
import com.xqlh.beatandshout.R;
import com.xqlh.beatandshout.base.BaseActivity;
import com.xqlh.beatandshout.util.Constants;
import com.xqlh.beatandshout.util.MySqliteOpenHelper;

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

public class CreateManagerActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_real_name)
    EditText et_real_name;
    @BindView(R.id.et_sex)
    EditText et_sex;
    @BindView(R.id.bt_save)
    Button bt_save;
    @BindView(R.id.bt_cancel)
    Button bt_cancel;

    String account;
    String password;
    String realName;
    String sex;

    private EntityAccount mEntityAccount;
    private String json;
    SQLiteDatabase db;
    MySqliteOpenHelper mySqliteOpenHelper;


    @Override
    public int setContent() {
        return R.layout.activity_create_manager;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        mySqliteOpenHelper = new MySqliteOpenHelper(this);
        db = mySqliteOpenHelper.getReadableDatabase();
    }

    @OnClick({R.id.bt_save, R.id.bt_cancel})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_save:
                account = et_account.getText().toString().trim();
                password = et_password.getText().toString().trim();
                realName = et_real_name.getText().toString().trim();
                sex = et_sex.getText().toString().trim();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(realName) && !TextUtils.isEmpty(sex)) {
                    saveAccount(account, password, realName, sex);
                } else {
                    Toast.makeText(this, "请全部填写", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(this,CreateUserActivity.class));
                break;
        }
    }

    public void saveAccount(String account, String password, String realName, String sex) {
        String sql = null;
        sql = "select account from manageTB where account = " + "'" + account + "'";
        Cursor cursor = db.rawQuery(sql, null);
        Log.i(TAG, "saveAccount: " + cursor.getColumnCount());
        if (cursor.getCount() > 0) {
            Toast.makeText(this, "该账号已存在", Toast.LENGTH_SHORT).show();
//            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//                if (cursor.getCount() != 0) {
//                    accountDB = cursor.getString(cursor.getColumnIndex("account"));
//                    if (accountDB.equals(account)) {
//
//                    } else {
//                    }
//                }
//            }
            cursor.close();
        } else {
            db.beginTransaction();
            db.execSQL("insert into manageTB(account,password,realName,sex) " +
                            "values(?,?,?,?)",
                    new Object[]{account, password, realName, sex});
            db.setTransactionSuccessful();
            db.endTransaction();

            //传递给后台
            setWebSocketClient(Constants.BASE_URL, Constants.VALUE, account, password, realName, sex);
        }
    }

    public void setWebSocketClient(String address, String value, String account, String password, String realName, String sex) {
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
        mEntityAccount = new EntityAccount();
        mEntityAccount.setAdmin(true);
        mEntityAccount.setUserName(account);
        mEntityAccount.setPassword(password);
        mEntityAccount.setName(realName);
        mEntityAccount.setUserId(1);
        mEntityAccount.setOpenId("1111");
        mEntityAccount.setRole("A");
        mEntityAccount.setHeight(172);
        mEntityAccount.setSex(sex);
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

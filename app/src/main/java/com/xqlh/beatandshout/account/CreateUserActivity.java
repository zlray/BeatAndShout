package com.xqlh.beatandshout.account;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateUserActivity extends BaseActivity {

    @BindView(R.id.et_account)
    EditText et_account;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.et_real_name)
    EditText et_real_name;

    @BindView(R.id.et_sex)
    EditText et_sex;

    @BindView(R.id.et_height)
    EditText et_height;

    @BindView(R.id.et_weight)
    EditText et_weight;

    @BindView(R.id.bt_age)
    Button bt_age;

    private String account;
    private String password;
    private String realName;
    private String sex;
    private String height;
    private String weight;
    private String age;
    private int mYear;
    private int mMonth;
    private int mDay;
    private static final int SHOW_START = 0;
    private static final int START = 1;
    SQLiteDatabase db;
    MySqliteOpenHelper mySqliteOpenHelper;
    private EntityAccount mEntityAccount;
    private String json;

    @Override
    public int setContent() {
        return R.layout.activity_create_user;
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

    @OnClick({R.id.bt_age, R.id.bt_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_age:
                setDateTime1();
                Message msg = new Message();
                if (bt_age.equals((Button) view)) {
                    msg.what = CreateUserActivity.SHOW_START;
                    CreateUserActivity.this.dateandtimeHandler.sendMessage(msg);
                }
                break;

            case R.id.bt_save:
                account = et_account.getText().toString().trim();
                password = et_password.getText().toString().trim();
                realName = et_real_name.getText().toString().trim();
                sex = et_sex.getText().toString().trim();
                height = et_height.getText().toString().trim();
                weight = et_weight.getText().toString().trim();
                age = bt_age.getText().toString().trim();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(realName) && !TextUtils.isEmpty(sex)
                        && !TextUtils.isEmpty(height + "") && !TextUtils.isEmpty(weight + "")
                        && !TextUtils.isEmpty(age)) {
                    saveAccount(account, password, realName, sex,height,weight,age);
                } else {
                    Toast.makeText(this, "请全部填写", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void saveAccount(String account, String password, String realName, String sex,String height,String weight,String age) {
        String sql = null;
        sql = "select account from userTB where account = " + "'" + account + "'";
        Cursor cursor = db.rawQuery(sql, null);
        Log.i(TAG, "saveAccount: " + cursor.getColumnCount());
        if (cursor.getCount() > 0) {
            Toast.makeText(this, "该账号已存在", Toast.LENGTH_SHORT).show();
            cursor.close();
        } else {
            db.beginTransaction();
            db.execSQL("insert into userTB(account,password,realName,sex,height,weight,age) " +
                            "values(?,?,?,?,?,?,?)",
                    new Object[]{account, password, realName, sex,height,weight,age});
            db.setTransactionSuccessful();
            db.endTransaction();
            setWebSocketClient(Constants.BASE_URL, Constants.VALUE, account, password, realName, sex,height,weight,age);
        }
    }
    public void setWebSocketClient(String address, String value, String account, String password, String realName,
                                   String sex,String height,String weight,String age) {
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
        mEntityAccount.setAdmin(false);
        mEntityAccount.setUserName(account);
        mEntityAccount.setPassword(password);
        mEntityAccount.setName(realName);
        mEntityAccount.setUserId(1);
        mEntityAccount.setOpenId("1111");
        mEntityAccount.setRole("A");
        mEntityAccount.setHeight(Integer.parseInt(height));
        mEntityAccount.setSex(sex);
        mEntityAccount.setWeChatId("1111");
        mEntityAccount.setWeight(Integer.parseInt(weight));
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
    }

    /**
     * 设置日期
     */
    private void setDateTime1() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDateDisplay1();
    }

    /**
     * 更新日期显示
     */
    private void updateDateDisplay1() {
        bt_age.setText(new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay));
    }

    /**
     * 处理日期和时间控件的Handler
     */
    Handler dateandtimeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CreateUserActivity.SHOW_START:
                    showDialog(START);
                    break;
            }
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case START:
                return new DatePickerDialog(this, mDateSetListener1, mYear, mMonth, mDay);
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case START:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;

        }
    }

    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDateDisplay1();
        }
    };
}

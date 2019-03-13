package com.xqlh.beatandshout.util;

/**
 * Created by zl on 2018/11/7.
 */

public class Constants {

    public static final String BASE_URL = "ws://192.168.1.195:2019";
    public static final String TAG = "lz";
    public static final String VALUE = "uk=5E937292-B101-4B79-9D5D-D3ECFC1853F6";

    public static final String DB = "beat_shout.db";


    public static final int DB_VERSION = 1;

    public static final String CREATE_MANAGE =

            "create table manageTB ("
                    + "_id integer PRIMARY KEY AUTOINCREMENT NOT NULL," //
                    + "account text," //用户名
                    + "password text," //密码
                    + "realName text," //真是姓名
                    + "sex text)";  //性别

    public static final String CREATE_USER =
            "create table userTB ("
                    + "_id integer PRIMARY KEY AUTOINCREMENT NOT NULL," //
                    + "account text," //用户名
                    + "password text," //密码
                    + "realName text," //真实姓名
                    + "sex text," //性别
                    + "height text," //身高
                    + "weight text," //体重
                    + "age text)";  //年龄

}

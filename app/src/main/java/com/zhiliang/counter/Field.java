package com.zhiliang.counter;

/**
 * Created by YZL19 on 2018/4/7 0007.
 */

public class Field {
    public String SP_USER_KEY = "SpUserKey";

    private static final Field ourInstance = new Field();

    public static Field getInstance() {
        return ourInstance;
    }

    private Field() {
    }
}

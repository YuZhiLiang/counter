package com.zhiliang.counter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.zhiliang.counter.Bean.CounterUser;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscription;

public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private int mPermissionTips = 1;
    private int mPermissionRequestFotIntenet = 1;
    private int mPermissionRequestFotPhoneState = 2;
    private EditText mEditTextUserName;//账户
    private EditText mEditTextPassWord;//密码
    private CheckBox mCheckBoxRememberUserName;//记住我
    private CheckBox mCheckBoxRememberPassWord;//记住密码
    private Button mButtonLogin;//登陆
    private Button mButtonRegister;//注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        permissionCheck();
    }

    private void permissionCheck() {
        checkInterneePermission();
    }

    private void checkInterneePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            checkPhoneStatePermission();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, mPermissionRequestFotIntenet);
        }
    }

    private void checkPhoneStatePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            permissionCheckComplete();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, mPermissionRequestFotIntenet);
        }
    }

    private void permissionCheckComplete() {
        if (BmobUser.getCurrentUser(CounterUser.class) == null) {
            showLoginView();
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void showLoginView() {
        setContentView(R.layout.login_view);
        initLoginView();
    }

    private void doLogin() {
        if (validationOfLegality()) {
            String userName = mEditTextUserName.getText().toString();
            String passWord = mEditTextPassWord.getText().toString();
            BmobUser counterUser = new BmobUser();
            counterUser.setUsername(userName);
            counterUser.setPassword(passWord);
            counterUser.login(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser s, BmobException e) {
                    if (e == null) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } else {
                        ToastUtils.showShort(R.string.register_failed);
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void initLoginView() {
        mEditTextUserName = findViewById(R.id.editTextUserName);
        mEditTextPassWord = findViewById(R.id.editTextPassWord);
        mCheckBoxRememberUserName = findViewById(R.id.checkBoxRememberAccount);
        mCheckBoxRememberPassWord = findViewById(R.id.checkBoxRememberPassword);
        mButtonLogin = findViewById(R.id.btnLogin);
        mButtonRegister = findViewById(R.id.btnRegister);

        mButtonLogin.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);
    }

    private void showPermissionRefuseDialog() {
        showConfirmDialog(getString(R.string.permissions_confirm), getString(R.string.permissions_tips), mPermissionTips, false);
    }

    @Override
    protected void onConfirmClick(int flage) {
        super.onConfirmClick(flage);
        finish();
    }

    @Override
    protected void onCancelClick(int flage) {
        super.onCancelClick(flage);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, mPermissionRequestFotIntenet);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mPermissionRequestFotIntenet) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPhoneStatePermission();
            } else {
                showPermissionRefuseDialog();
            }
        } else if (requestCode == mPermissionRequestFotPhoneState) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionCheckComplete();
            } else {
                showPermissionRefuseDialog();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editTextUserName:

                break;
            case R.id.editTextPassWord:

                break;
            case R.id.checkBoxRememberAccount:

                break;
            case R.id.checkBoxRememberPassword:

                break;
            case R.id.btnLogin:
                onLoginClick();
                break;
            case R.id.btnRegister:
                onRegisterClick();
                break;
        }
    }

    private void onRegisterClick() {
        if (validationOfLegality()) {
            String userName = mEditTextUserName.getText().toString();
            String passWord = mEditTextPassWord.getText().toString();
            BmobQuery<CounterUser> bmobQuery = new BmobQuery<CounterUser>();
            bmobQuery.addWhereEqualTo("username", mEditTextUserName.getText().toString());
            Subscription count = bmobQuery.count(CounterUser.class, new CountListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e == null) {
                        if (integer > 0) {
                            ToastUtils.showShort(R.string.user_name_already_exist);
                        } else {
                            CounterUser counterUser = new CounterUser();
                            counterUser.setUsername(userName);
                            counterUser.setPassword(passWord);
                            counterUser.signUp(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        doLogin();
                                    } else {
                                        ToastUtils.showShort(R.string.register_failed);
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void onLoginClick() {
        doLogin();
    }

    private boolean validationOfLegality() {
        String uaerName = mEditTextUserName.getText().toString();
        if (TextUtils.isEmpty(uaerName) || uaerName.length() < 3) {
            ToastUtils.showShort(R.string.user_name_lenght_exception);
            return false;
        }
        String passWord = mEditTextPassWord.getText().toString();
        if (TextUtils.isEmpty(passWord) || passWord.length() < 6) {
            ToastUtils.showShort(R.string.pass_word_lenght_exception);
            return false;
        }
        return true;
    }
}

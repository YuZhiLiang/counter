package com.zhiliang.counter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhiliang.counter.Bean.CounterUser;
import com.zhiliang.counter.Bean.Rule;
import com.zhiliang.counter.R;
import com.zhiliang.counter.utils.ListUtil;
import com.zhiliang.counter.utils.WeakActivityHandler;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends BaseActivity {
    private static WeakActivityHandler<MainActivity> sWeakActivityHandler;
    private ImageView mImageViewLeft;
    private ImageView mImageViewRight;
    private ProgressBar mProgressBar;
    private TextView mLeftText;
    private TextView mRightText;
    private FloatingActionButton mFloatingActionButton;
    private Toolbar mToolbar;
    private static long DELAYED_TIME = 2000;

    private final int REQUEST_CODE_BIND = 1;
    private final int REQUEST_CODE_RULE = 2;


    private final int FLAGE_ERROR = 1;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(getOnMenuItenClickListener());

        mImageViewLeft = findViewById(R.id.imageLeft);
        mImageViewRight = findViewById(R.id.imageRight);
        mProgressBar = findViewById(R.id.progressBar);
        mLeftText = findViewById(R.id.text_left);
        mRightText = findViewById(R.id.text_right);

        mFloatingActionButton = findViewById(R.id.fab);
        CounterUser currentUser = BmobUser.getCurrentUser(CounterUser.class);
        mFloatingActionButton.setOnClickListener(view -> {
            BmobUser binduser = currentUser.getBinduser();
            if (binduser == null || StringUtils.isEmpty(binduser.getObjectId())) {
                startActivityForResult(new Intent(MainActivity.this, BindActivity.class), REQUEST_CODE_RULE);
            } else {
                startActivityForResult(new Intent(MainActivity.this, RuleActivity.class), REQUEST_CODE_RULE);
            }
        });
        upDataUserInfo();
        setImageViewAvatat();
        clearHandler();
        sWeakActivityHandler = new WeakActivityHandler<>(this);
        BmobUser binduser = BmobUser.getCurrentUser(CounterUser.class).getBinduser();
        if (binduser == null || StringUtils.isEmpty(binduser.getObjectId())) {
            startActivityForResult(new Intent(this, BindActivity.class), REQUEST_CODE_BIND);
        } else {
            getProgress();
        }
    }

    private void upDataUserInfo() {
        CounterUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Gson gson = new Gson();
                CounterUser counterUser = gson.fromJson(s, CounterUser.class);

                CounterUser currentUser = CounterUser.getCurrentUser();
                currentUser.setAvatat(counterUser.getAvatat());
                currentUser.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            setImageViewAvatat();
                        }else {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void setImageViewAvatat() {
        CounterUser currentUser = CounterUser.getCurrentUser();
        String avatat = currentUser.getAvatat();
        if (!TextUtils.isEmpty(avatat)) {
            Glide.with(this).load(avatat).into(mImageViewLeft);
        }
        CounterUser binduser = currentUser.getBinduser();
        if (binduser != null && !TextUtils.isEmpty(binduser.getAvatat())) {
            Glide.with(this).load(binduser.getAvatat()).into(mImageViewRight);
        }
    }

    //如果有Menu,创建完后,系统会自动添加到ToolBar上
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener getOnMenuItenClickListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.action_overflow:
                    //弹出自定义overflow
                    startActivity(new Intent(MainActivity.this, MyInfoActivity.class));
                    return true;
            }
            return false;
        };
    }

    private void getProgress() {
        sWeakActivityHandler.removeCallbacksAndMessages(null);
        sWeakActivityHandler.postDelayed(mPollRunnable, 0);
    }

    private void postDelayed() {
        MainActivity reference = sWeakActivityHandler.getReference();
        if (reference == null || reference.isFinishing()) return;
        sWeakActivityHandler.removeCallbacksAndMessages(null);
        sWeakActivityHandler.postDelayed(mPollRunnable, DELAYED_TIME);
    }

    private List<Rule> myRules;
    private List<Rule> otherRules;

    private Runnable mPollRunnable = new Runnable() {
        @Override
        public void run() {
            sWeakActivityHandler.removeCallbacksAndMessages(null);
            BmobQuery<Rule> ruleBmobQuery1 = new BmobQuery<>();
            CounterUser currentUser1 = CounterUser.getCurrentUser(CounterUser.class);
            CounterUser binduser = currentUser1.getBinduser();
            ruleBmobQuery1.addWhereEqualTo(Rule.ColumnName.INITIATE_COUNTER_USER, currentUser1);
            ruleBmobQuery1.addWhereEqualTo(Rule.ColumnName.INITIATE_COUNTER_USER, binduser);

            showLoadingDialog();
            ruleBmobQuery1.findObjects(new FindListener<Rule>() {
                @Override
                public void done(List<Rule> list, BmobException e) {
                    if (e == null) {
                        myRules = list;
                        BmobQuery<Rule> ruleBmobQuery2 = new BmobQuery<>();
                        ruleBmobQuery2.addWhereEqualTo(Rule.ColumnName.THE_OTHER_ONE_COUNTERUSER, currentUser1);
                        ruleBmobQuery2.addWhereEqualTo(Rule.ColumnName.THE_OTHER_ONE_COUNTERUSER, binduser);
                        ruleBmobQuery2.findObjects(new FindListener<Rule>() {
                            @Override
                            public void done(List<Rule> list, BmobException e) {
                                dismissLoadingDialog();
                                if (e == null) {
                                    otherRules = list;
                                    configProgress();
                                } else {
                                    showConfirmDialog(getResources().getString(R.string.expection), getResources().getString(R.string.expection), FLAGE_ERROR);
                                }
                            }
                        });
                    } else {
                        dismissLoadingDialog();
                        showConfirmDialog(getResources().getString(R.string.expection), getResources().getString(R.string.expection), FLAGE_ERROR);
                    }
                }
            });
        }
    };

    private void configProgress() {
        int mySize = ListUtil.getSize(myRules);
        int otherSize = ListUtil.getSize(otherRules);
        int sum = mySize + otherSize;
        if (sum < 1) {
            mProgressBar.setProgress(50);
        } else {
            int i = 10 - otherSize;
            int i1 = i * 10;
            mProgressBar.setProgress(i1);
            mProgressBar.setSecondaryProgress(mySize * 10);
        }
        mLeftText.setText("已决定" + mySize + "次");
        mRightText.setText("已决定" + otherSize + "次");
    }

    private void clearHandler() {
        if (sWeakActivityHandler != null) {
            sWeakActivityHandler.clear();
            sWeakActivityHandler.removeCallbacksAndMessages(null);
            sWeakActivityHandler = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BIND) {
            if (resultCode == RESULT_OK) {

            } else {

            }
        } else if (requestCode == REQUEST_CODE_RULE) {
            if (resultCode == RESULT_OK) {
                getProgress();
            } else {

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearHandler();
    }
}

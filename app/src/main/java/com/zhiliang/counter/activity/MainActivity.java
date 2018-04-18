package com.zhiliang.counter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zhiliang.counter.Bean.CounterUser;
import com.zhiliang.counter.R;
import com.zhiliang.counter.utils.WeakActivityHandler;

import cn.bmob.v3.BmobUser;

public class MainActivity extends BaseActivity {
    private static WeakActivityHandler<MainActivity> sWeakActivityHandler;
    private ImageView mImageViewLeft;
    private ImageView mImageViewRight;
    private ProgressBar mProgressBar;
    FloatingActionButton mFloatingActionButton;
    private static long DELAYED_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageViewLeft = findViewById(R.id.imageLeft);
        mImageViewRight = findViewById(R.id.imageRight);
        mProgressBar = findViewById(R.id.progressBar);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        clearHandler();
        sWeakActivityHandler = new WeakActivityHandler<>(this);
        if (BmobUser.getCurrentUser(CounterUser.class).getBinduser() == null) {
            startActivity(new Intent(this, BindActivity.class));
        } else {
            sWeakActivityHandler.postDelayed(mPollRunnable, DELAYED_TIME);
        }
    }

    private Runnable mPollRunnable = new Runnable() {
        @Override
        public void run() {

        }
    };

    private void clearHandler() {
        if (sWeakActivityHandler != null) {
            sWeakActivityHandler.clear();
            sWeakActivityHandler.removeCallbacks(null);
            sWeakActivityHandler = null;
        }
    }

    BaseTransientBottomBar.BaseCallback<Snackbar> mBaseCallback;

    /**
     * 发起一次裁决
     */
    private void initiateARuling() {
        if (mBaseCallback != null) {
            return;
        }
        mBaseCallback = new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);

            }
        };

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar make = Snackbar.make(view, R.string.master_decisions, Snackbar.LENGTH_LONG);
                make.setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        make.removeCallback(mBaseCallback);
                    }
                }).addCallback(mBaseCallback)
                        .show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearHandler();
    }
}

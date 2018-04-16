package com.zhiliang.counter;

import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends BaseActivity {
    private ImageView mImageViewLeft;
    private ImageView mImageViewRight;
    private ProgressBar mProgressBar;
    FloatingActionButton mFloatingActionButton;

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
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
}

package com.zhiliang.counter.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.zhiliang.counter.R;

/**
 * Created by 04816381 on 2018-04-17.
 */

public class LoadingView extends FrameLayout {
    private ProgressBar mProgressBar;

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.loading_view, this, true);
        mProgressBar = findViewById(R.id.loading_progress_bar);
    }
}

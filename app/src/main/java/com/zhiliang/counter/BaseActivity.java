package com.zhiliang.counter;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

/**
 * Created by YZL19 on 2018/4/6 0006.
 */

public class BaseActivity extends AppCompatActivity {
    protected AlertDialog mConfirmDialog;
    private boolean mConfirmDialogCancelable = true;

    protected void showConfirmDialog(String title, String message, int flag) {
        showConfirmDialog(title, message, flag, true);
    }

    protected void showConfirmDialog(String title, String message, int flag, boolean canCancelConfirmDialog) {
        if (mConfirmDialog != null && mConfirmDialog.isShowing() && !mConfirmDialogCancelable) {
            return;
        }

        if (mConfirmDialog != null && mConfirmDialog.isShowing()) {
            mConfirmDialog.dismiss();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (!TextUtils.isEmpty(message)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }

        mConfirmDialogCancelable = canCancelConfirmDialog;
        builder.setCancelable(mConfirmDialogCancelable);

        builder.setPositiveButton(R.string.confirm, (dialog, which) -> onConfirmClick(flag));

        builder.setOnCancelListener(dialog -> onCancelClick(flag));
    }

    protected void onConfirmClick(int flage) {

    }

    protected void onCancelClick(int flage) {

    }
}

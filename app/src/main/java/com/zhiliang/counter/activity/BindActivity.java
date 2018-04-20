package com.zhiliang.counter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zhiliang.counter.Bean.CounterUser;
import com.zhiliang.counter.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class BindActivity extends BaseActivity {
    private EditText mEditText;
    private Button mButton;
    public static final String KEY_BALANCE_ID = "balance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        mEditText = findViewById(R.id.bind_edit);
        mButton = findViewById(R.id.bind_confirm);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind();
            }
        });
    }

    private void bind() {
        String s = mEditText.getText().toString();
        if (TextUtils.isEmpty(s) || !RegexUtils.isMobileSimple(s)) {
            ToastUtils.showShort(R.string.user_not_exist);
        }
        showLoadingDialog();
        BmobQuery<CounterUser> query = new BmobQuery<CounterUser>();
        query.addWhereEqualTo(CounterUser.ColumnName.MOBILE_PHONE_NUMBER, s);
        query.setLimit(1);
        query.findObjects(new FindListener<CounterUser>() {
            @Override
            public void done(List<CounterUser> list, BmobException e) {
                if (e == null) {
                    if (ObjectUtils.isEmpty(list)) {
                        ToastUtils.showShort(R.string.user_not_exist);
                        dismissLoadingDialog();
                    } else {
                        doBindRequest(list);
                    }
                } else {
                    ToastUtils.showShort(R.string.expection);
                    dismissLoadingDialog();
                }
            }
        });
    }

    private void doBindRequest(List<CounterUser> list) {
        CounterUser currentUser = CounterUser.getCurrentUser(CounterUser.class);
        CounterUser counterUser = list.get(0);
        currentUser.setBinduser(counterUser);
        currentUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showShort(R.string.bind_success);
                    dismissLoadingDialog();
                    setResult(RESULT_OK);
                    finish();
                }else {
                    e.printStackTrace();
                    ToastUtils.showShort(R.string.expection);
                    dismissLoadingDialog();
                }
            }
        });
    }

    private final int mCancelBind = 1;

    @Override
    public void onBackPressed() {
        if (mLoadingDialog == null || !mLoadingDialog.isShowing()) {
            showConfirmDialog(getString(R.string.cancel_bind), getString(R.string.cancel_bind_summary), mCancelBind, false);
        }
    }

    @Override
    protected void onConfirmClick(int flage) {
        super.onConfirmClick(flage);
        if (flage == mCancelBind) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}

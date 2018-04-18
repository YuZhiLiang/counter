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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class BindActivity extends BaseActivity {
    private EditText mEditText;
    private Button mButton;

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
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo(CounterUser.ColumnName.MOBILE_PHONE_NUMBER, s);
        query.setLimit(1);
        query.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> list, BmobException e) {
                if (e == null) {
                    if (ObjectUtils.isEmpty(list)) {
                        ToastUtils.showShort(R.string.user_not_exist);
                        dismissLoadingDialog();
                    } else {
                        doBind(list);
                    }
                } else {
                    ToastUtils.showShort(R.string.expection);
                    dismissLoadingDialog();
                }
            }
        });
    }

    private void doBind(List<BmobUser> list) {
        CounterUser currentUser = BmobUser.getCurrentUser(CounterUser.class);
        currentUser.setBinduser(list.get(0));
        currentUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showShort(R.string.bind_success);
                } else {
                    ToastUtils.showShort(R.string.expection);
                }
                dismissLoadingDialog();
            }
        });
    }
}

package com.zhiliang.counter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.zhiliang.counter.Bean.CounterUser;
import com.zhiliang.counter.Bean.Rule;
import com.zhiliang.counter.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RuleActivity extends BaseActivity {
    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);
        mEditText = findViewById(R.id.rule);
        mButton = findViewById(R.id.rule_confirm);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRule();
            }
        });
    }

    private void addRule() {
        Rule rule = new Rule();
        String s = mEditText.getText().toString();
        if (!TextUtils.isEmpty(s)) {
            rule.setCause(s);
        }
        CounterUser currentUser = BmobUser.getCurrentUser(CounterUser.class);
        rule.setTheCounterUser(currentUser);
        rule.setOtherCounterUser(currentUser.getBinduser());
        showLoadingDialog();
        rule.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    dismissLoadingDialog();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    dismissLoadingDialog();
                    ToastUtils.showShort(R.string.add_rule_exception);
                }
            }
        });
    }
}

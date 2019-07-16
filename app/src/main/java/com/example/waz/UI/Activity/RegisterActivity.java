package com.example.waz.UI.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.waz.R;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private int i=60;
    private Toolbar mTb;
    /**
     * 请输入用户名
     */
    private EditText mLogin;
    /**
     * 请输入密码
     */
    private EditText mPwd;
    /**
     * 请输入电话号码
     */
    private EditText mPhone;
    /**
     * 请输入验证码
     */
    private EditText mVirify;
    /**
     * 获取验证码
     */
    private Button mBtnVirify;
    /**
     * 注册
     */
    private Button mRegisterbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mTb = (Toolbar) findViewById(R.id.tb);
        mLogin = (EditText) findViewById(R.id.login);
        mPwd = (EditText) findViewById(R.id.pwd);
        mPhone = (EditText) findViewById(R.id.phone);
        mVirify = (EditText) findViewById(R.id.virify);
        mBtnVirify = (Button) findViewById(R.id.btn_virify);
        mBtnVirify.setOnClickListener(this);
        mRegisterbtn = (Button) findViewById(R.id.registerbtn);
        mRegisterbtn.setOnClickListener(this);
        mTb.setTitle("");
        setSupportActionBar(mTb);
        mTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_virify:
                mVirify.setText("287d");
                break;
            case R.id.registerbtn:
                if (!mLogin.getText().toString().equals("")&&!mPwd.getText().toString().equals("")){
                    Intent intent = new Intent();
                    intent.putExtra("username",mLogin.getText().toString());
                    intent.putExtra("password",mPwd.getText().toString());
                    setResult(200,intent);
                    finish();
                }else{
                    Toast.makeText(this, "请填写账号或密码", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}

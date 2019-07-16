package com.example.waz.UI.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.waz.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mTb;
    /**
     * 登录
     */
    private TextView mTv;
    /**
     * 请输入用户名
     */
    private EditText mLogin;
    /**
     * 请输入密码
     */
    private EditText mPwd;
    /**
     * 登录
     */
    private Button mLoginbtn;
    /**
     * 注册
     */
    private Button mRegisterbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==200){
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            mLogin.setText(username);
            mPwd.setText(password);
        }
    }
    private void initView() {
        mTb = (Toolbar) findViewById(R.id.tb);
        mTv = (TextView) findViewById(R.id.tv);
        mLogin = (EditText) findViewById(R.id.login);
        mPwd = (EditText) findViewById(R.id.pwd);
        mLoginbtn = (Button) findViewById(R.id.loginbtn);
        mLoginbtn.setOnClickListener(this);
        mRegisterbtn = (Button) findViewById(R.id.registerbtn);
        mRegisterbtn.setOnClickListener(this);
        SharedPreferences token = getSharedPreferences("loginToken", 0);
        if (token!=null){
            mLogin.setText(token.getString("username",null));
            mPwd.setText(token.getString("pwd",null));
        }
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
            case R.id.loginbtn:
                String name = mLogin.getText().toString();
                String pwd = mPwd.getText().toString();
                SharedPreferences token = getSharedPreferences("loginToken", 0);
                SharedPreferences.Editor edit = token.edit();
                edit.putString("username",name);
                edit.putString("pwd",pwd);
                edit.commit();
                Intent intent1 = new Intent();
                intent1.putExtra("username",name);
                intent1.putExtra("pwd",pwd);
                setResult(22,intent1);
                finish();
                break;
            case R.id.registerbtn:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
               startActivityForResult(intent,100);
                break;
        }
    }
}

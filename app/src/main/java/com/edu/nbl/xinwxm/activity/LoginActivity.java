package com.edu.nbl.xinwxm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.nbl.xinwxm.R;
import com.edu.nbl.xinwxm.biz.CommonUtil;
import com.edu.nbl.xinwxm.network.EasyShopApi;
import com.edu.nbl.xinwxm.network.OkhttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.ed_userName)
    EditText mEdUserName;
    @BindView(R.id.ed_password)
    EditText mEdPassword;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示箭头和标题
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @OnClick({R.id.bt_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login() {
        //获取信息
        String mUserName = mEdUserName.getText().toString().trim();
        String mPaw = mEdPassword.getText().toString().trim();
        //非空判断
        if (mUserName.length() == 0) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPaw.length() == 0) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CommonUtil.verifyPassword(mPaw)) {
            Toast.makeText(this, "密码格式不正确，6-16位", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = EasyShopApi.APP_URL + "user_login?ver=1&uid=" + mUserName + "&pwd=" + mPaw + "&device=0";
        OkhttpUtils.getOkhttp(url, LoginActivity.this, new OkhttpUtils.CallBack() {
            @Override
            public void successed(Call call, String jsonStr) {
                try {
                    JSONObject mJSONObject = new JSONObject(jsonStr);
                    if (mJSONObject.getInt("status") == 0) {
                        mJSONObject = mJSONObject.getJSONObject("data");
                        int mResult = mJSONObject.getInt("result");
                        if (mResult == 0) {
                            String mToken = mJSONObject.getString("token");//用户令牌
                            Bundle mBundle = new Bundle();
                            mBundle.putString("token", mToken);
                            Intent mIntent = new Intent();
                            mIntent.putExtras(mBundle);
                            setResult(1, mIntent);
                            LoginActivity.this.finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail(Call call, IOException e) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Bundle mBundle = data.getExtras();
            String mUserName = mBundle.getString("userName");
            String mPaw = mBundle.getString("paw");
            //将用户名和密码回显
            mEdUserName.setText(mUserName);
            mEdPassword.setText(mPaw);
        }
    }


}

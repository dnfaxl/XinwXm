package com.edu.nbl.xinwxm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.ed_userName)
    EditText mEdUserName;
    @BindView(R.id.ed_email)
    EditText mEdEmail;
    @BindView(R.id.ed_password)
    EditText mEdPassword;
    @BindView(R.id.bt_register)
    Button mBtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
    @OnClick(R.id.bt_register)
    public void onViewClicked() {
        register();
    }

    private void register() {
        //获取用户信息
        //trim()去空格
        final String mUserName= mEdUserName.getText().toString().trim();
        String mEmail = mEdEmail.getText().toString().trim();
        final String mPwd= mEdPassword.getText().toString().trim();
        //非空
        if (mUserName.length()==0){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mEmail.length()==0){
            Toast.makeText(this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPwd.length()==0){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CommonUtil.verifyEmail(mEmail)){
            Toast.makeText(this,"邮箱不正确",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CommonUtil.verifyPassword(mPwd)){
            Toast.makeText(this,"密码格式不正确，6-16位",Toast.LENGTH_SHORT).show();
            return;
        }
        //注册
        String url= EasyShopApi.APP_URL+"user_register?ver=1&uid="+mUserName+"&email="+mEmail+"&pwd="+mPwd;
        OkhttpUtils.getOkhttp(url, RegisterActivity.this, new OkhttpUtils.CallBack() {
            @Override
            public void successed(Call call, String jsonStr) {
                Log.e("result", jsonStr);
                try {
                    JSONObject mJSONObject = new JSONObject(jsonStr);
                    if (mJSONObject.getInt("status")==0){
                        mJSONObject=mJSONObject.getJSONObject("data");
                        int mResult = mJSONObject.getInt("result");
                        if (mResult==0){
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

                            Bundle mBundle = new Bundle();
                            mBundle.putString("userName",mUserName);
                            mBundle.putString("paw",mPwd);
                            Intent mIntent = new Intent();
                            mIntent.putExtras(mBundle);
                            setResult(1,mIntent);
                            //关闭界面
                            RegisterActivity.this.finish();
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
}

package com.banquan.ljb.banquan0924;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    private RadioButton radioButton_stu;
    private RadioButton radioButton_manager;
    private EditText edt_reg_account;
    private EditText edt_reg_password;
    private Button btn_register;
    private ProgressDialog mProgressDialog;
    private String reg_identity;
    private String reg_account;
    private String reg_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this, "e9730bea7f047ed30463895c16f14c46");

        initView();

    }

    private void initView() {

        radioButton_stu = (RadioButton) findViewById(R.id.radio_student);
        radioButton_manager = (RadioButton) findViewById(R.id.radio_manager);
        edt_reg_account = (EditText) findViewById(R.id.reg_accounts);
        edt_reg_password = (EditText) findViewById(R.id.reg_password);
        btn_register = (Button) findViewById(R.id.register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


    }

    private void register() {

        if(radioButton_stu.isChecked()==true){
            reg_identity="1";//1代表学生   0代表管理员
        }else{
            reg_identity="0";
        }

        reg_account = edt_reg_account.getText().toString().trim();
        reg_password = edt_reg_password.getText().toString().trim();

        if(reg_account.length()>0 && reg_password.length()>0){
            BmobQuery<User> query_account = new BmobQuery<User>();
            query_account.addWhereEqualTo("username",reg_account);
            query_account.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if(list.isEmpty() == true){
                        registerToBomb();
                    }else {
                        showUserExistDialog();
                    }
                }
            });
        }else{
            showTextLengthMustMoreThanZeroDialog();
        }





    }

    private void showTextLengthMustMoreThanZeroDialog() {
        Toast.makeText(this,"账号和密码长度必须大于0",Toast.LENGTH_SHORT).show();
    }

    private void showUserExistDialog() {
        Toast.makeText(this,"账号已经存在",Toast.LENGTH_SHORT).show();
    }

    private void registerToBomb() {
        User user = new User();
        user.setIdentity(reg_identity);
        user.setUsername(reg_account);
        user.setPassword(reg_password);
        user.signUp(new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if(e == null){
                    onRegisterSuccess();
                    finish();
                }else {
                    onRegisterFailed();
                    Log.e("注册",e.toString());

                }
            }


        });

    }


    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        goTo(LoginActivity.class);
    }

    @Override
    public void onRegisterFailed() {
        Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAccountError() {

    }

    @Override
    public void onPasswordError() {

    }


    @Override
    public void onStartRegister() {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage("正在注册...");
        mProgressDialog.show();

    }

    public void goTo(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        finish();
    }
}

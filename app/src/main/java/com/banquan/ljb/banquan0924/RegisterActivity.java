package com.banquan.ljb.banquan0924;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    private RadioButton radioButton_stu;
    private RadioButton radioButton_manager;
    private EditText edt_reg_account;
    private EditText edt_reg_password;
    private Button btn_register;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
        String reg_identity;
        if(radioButton_stu.isChecked()==true){
            reg_identity="1";
        }else{
            reg_identity="0";
        }

        String reg_account = edt_reg_account.getText().toString().trim();
        String reg_password = edt_reg_password.getText().toString().trim();


        User user = new User();
        user.setIdentity(reg_identity);
        user.setAccount(reg_account);
        user.setPassword(reg_password);

        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    onRegisterSuccess();
                }else {
                    onRegisterFailed();

                }
            }
        });
    }


    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
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
}

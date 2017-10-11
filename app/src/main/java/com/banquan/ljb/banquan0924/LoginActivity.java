package com.banquan.ljb.banquan0924;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_accounts;
    private EditText editText_password;
    private Button button_login;
    private TextView textView_register;
    private String login_accounts;
    private String login_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "e9730bea7f047ed30463895c16f14c46");

        initView();
    }

    private void initView() {
        editText_accounts = (EditText) findViewById(R.id.accounts);
        editText_password = (EditText) findViewById(R.id.password);


        button_login = (Button) findViewById(R.id.login);
        textView_register = (TextView) findViewById(R.id.register_Text);

        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //登录
                login();

            }
        });

    }

    private void login() {
        login_accounts = editText_accounts.getText().toString().trim();
        login_password = editText_password.getText().toString().trim();

        if(login_accounts.length()>0 && login_password.length()>0){
            User user = new User();
            user.setUsername(login_accounts);
            user.setPassword(login_password);
            user.login(new SaveListener<User>() {

                @Override
                public void done(User User, BmobException e) {
                    if (e == null) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        String currentUserIdentity = User.getIdentity();
                        if(currentUserIdentity.equals("1")){
                            //可能传递用户名到相应的主界面，暂时不传参数 ！！！！！
                            goTo(StudentMainActivity.class);

                        }else{
                            //可能传递用户名到相应的主界面，暂时不传参数 ！！！！！
                            goTo(ManagerMainActivity.class);

                        }

                    } else {
                        Log.e("登录失败", e.toString());
                        if(e.getErrorCode()==101){
                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        }

                        if(e.getErrorCode()==9016){
                            Toast.makeText(LoginActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            });

        }else {
            Toast.makeText(LoginActivity.this, "账号和密码长度必须大于0", Toast.LENGTH_SHORT).show();
        }


    }

    public void goTo(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        finish();
    }


}

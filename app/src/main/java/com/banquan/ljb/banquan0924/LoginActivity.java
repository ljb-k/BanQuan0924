package com.banquan.ljb.banquan0924;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_accounts;
    private EditText editText_password;
    private Button button_login;
    private TextView textView_register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}

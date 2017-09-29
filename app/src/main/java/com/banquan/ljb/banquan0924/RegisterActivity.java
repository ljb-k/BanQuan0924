package com.banquan.ljb.banquan0924;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class RegisterActivity extends AppCompatActivity {

    private RadioButton radioButton_stu;
    private RadioButton radioButton_manager;
    private EditText edt_reg_account;
    private EditText edt_reg_password;
    private Button btn_register;


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


    }
}

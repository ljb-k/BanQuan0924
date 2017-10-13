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

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity{

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

        initTOBmob();
        initHuanXin();
        initView();

    }

    private void initHuanXin() {
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        int pid = android.os.Process.myPid();

// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

//初始化
        EMClient.getInstance().init(getApplicationContext(), options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        if (BuildConfig.DEBUG) {
            EMClient.getInstance().setDebugMode(true);
        }
    }

    private void initTOBmob() {
        Bmob.initialize(this, "e9730bea7f047ed30463895c16f14c46");
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
                        registerToBomb();//注册到bmob
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
                    registerToEaseMob();//注册到环信
                    finish();
                }else {
                    onRegisterFailed();
                    Log.e("注册",e.toString());

                }
            }


        });

    }

    private void registerToEaseMob() {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(reg_account, reg_password);
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onRegisterSuccess();

                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onRegisterFailed();

                        }
                    });
                }
            }
        });


    }


    public void onRegisterSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        goTo(LoginActivity.class);
    }

    public void onRegisterFailed() {
        Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();

    }


    public void goTo(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        finish();
    }
}

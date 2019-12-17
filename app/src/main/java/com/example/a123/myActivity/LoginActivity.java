package com.example.a123.myActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a123.R;
import com.example.a123.myClass.User;
import com.example.a123.myService.LoginService;
import com.wang.avi.AVLoadingIndicatorView;

public class LoginActivity extends BaseActivity  implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private MyHandler myHandler;

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_edit);
        password = findViewById(R.id.password_edit);
        myHandler = new MyHandler();

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        TextView new_text = (TextView) findViewById(R.id.new_text);
        new_text.setOnClickListener(this);

        Button login_but = (Button) findViewById(R.id.login_button);
        login_but.setOnClickListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        avi.setVisibility(View.GONE);
        email.setEnabled(true);
        password.setEnabled(true);
        //获取上次注册成功或登陆成功的用户信息
        SharedPreferences pref = getSharedPreferences("LoginData", MODE_PRIVATE);
        String emailS = pref.getString("email", "");
        String passwordS = pref.getString("password", "");
        email.setText(emailS);
        email.setSelection(email.length());
        password.setText(passwordS);
        password.setSelection(password.length());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.new_text:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.login_button:
                if(email.length() == 0 || password.length() == 0)
                    Toast.makeText(LoginActivity.this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                else {
                    avi.setVisibility(View.VISIBLE);
                    email.setEnabled(false);
                    password.setEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                        //暂停1秒看加载动画
//                        try {
//                            Thread.sleep(1000);
//                        } catch (Exception e) {
//
//                        }
                            User user = new User();
                            user.setEmail(email.getText().toString());
                            user.setPassword(password.getText().toString());
                            String res = LoginService.getCheckResult(user, LoginActivity.this);
                            Log.d("LoginActivity", res);
                            if(res.equals("login succeed")) {
                                SharedPreferences.Editor editor = getSharedPreferences("LoginData", MODE_PRIVATE).edit();
                                editor.putString("email", email.getText().toString());
                                editor.putString("password", password.getText().toString());
                                editor.apply();
                                myHandler.obtainMessage(1).sendToTarget();
                            } else if(res.equals("No registration")){
                                myHandler.obtainMessage(2).sendToTarget();
                            } else if(res.equals("e")) {
                                myHandler.obtainMessage(3).sendToTarget();
                            } else if(res.equals("contact_error")) {
                                myHandler.obtainMessage(4).sendToTarget();
                            } else if(res.equals("time_out")) {
                                myHandler.obtainMessage(5).sendToTarget();
                            }
                        }
                    }).start();
                }
                break;
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Intent intent = new Intent(LoginActivity.this, FamilyActivity.class);
                    intent.putExtra("email", email.getText().toString());
                    startActivity(intent);
                    break;
                case 2:
                    Toast.makeText(LoginActivity.this, "密码错误或用户不存在！", Toast.LENGTH_SHORT).show();
                    avi.setVisibility(View.GONE);
                    email.setEnabled(true);
                    password.setEnabled(true);
                    break;
                case 3:
                    Toast.makeText(LoginActivity.this, "登陆异常！", Toast.LENGTH_SHORT).show();
                    avi.setVisibility(View.GONE);
                    email.setEnabled(true);
                    password.setEnabled(true);
                    break;
                case 4:
                    Toast.makeText(LoginActivity.this, "连接异常！", Toast.LENGTH_SHORT).show();
                    avi.setVisibility(View.GONE);
                    email.setEnabled(true);
                    password.setEnabled(true);
                    break;
                case 5:
                    Toast.makeText(LoginActivity.this, "连接超时！", Toast.LENGTH_SHORT).show();
                    avi.setVisibility(View.GONE);
                    email.setEnabled(true);
                    password.setEnabled(true);
                    break;
            }
        }
    }
}

package com.example.a123.myActivity;

import android.content.Intent;
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

public class LoginActivity extends BaseActivity  implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_edit);
        password = findViewById(R.id.password_edit);
        myHandler = new MyHandler();

        TextView new_text = (TextView) findViewById(R.id.new_text);
        new_text.setOnClickListener(this);

        Button login_but = (Button) findViewById(R.id.login_button);
        login_but.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.new_text:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.login_button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = new User();
                        user.setEmail(email.getText().toString());
                        user.setPassword(password.getText().toString());
                        String res = LoginService.getCheckResult(user);
                        Log.d("LoginActivity: ", res);
                        myHandler.obtainMessage(1).sendToTarget();
//                        if(res.equals("login succeed")) {
//                            myHandler.obtainMessage(1).sendToTarget();
//                        } else if(res.equals("No registration")){
//                            myHandler.obtainMessage(2).sendToTarget();
//                        } else if(res.equals("e")) {
//                            myHandler.obtainMessage(3).sendToTarget();
//                        } else if(res.equals("contact_error")) {
//                            myHandler.obtainMessage(4).sendToTarget();
//                        } else if(res.equals("time_out")) {
//                            myHandler.obtainMessage(5).sendToTarget();
//                        }
                    }
                }).start();
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
                    startActivity(intent);
                    break;
                case 2:
                    Toast.makeText(LoginActivity.this, "密码错误或用户不存在！", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(LoginActivity.this, "登陆异常！", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(LoginActivity.this, "连接异常！", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(LoginActivity.this, "连接超时！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}

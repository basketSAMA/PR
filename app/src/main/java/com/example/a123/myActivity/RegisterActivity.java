package com.example.a123.myActivity;

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
import com.example.a123.myService.RegisterService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private EditText passwordAck;
    private MyHandler myHandler;

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText)findViewById(R.id.email_edit_r);
        password = (EditText)findViewById(R.id.password_edit_r);
        passwordAck = (EditText)findViewById(R.id.password_ack_r);
        myHandler = new MyHandler();

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        TextView log_text = (TextView) findViewById(R.id.login_text);
        log_text.setOnClickListener(this);

        Button register_but = (Button)findViewById(R.id.register_button);
        register_but.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_text:
                RegisterActivity.this.finish();
                break;
            case R.id.register_button:
                if(!isEmail(email.getText().toString()))
                    Toast.makeText(RegisterActivity.this, "邮件格式错误！", Toast.LENGTH_SHORT).show();
                else if(!password.getText().toString().equals(passwordAck.getText().toString()))
                    Toast.makeText(RegisterActivity.this, "前后输入密码不一致！", Toast.LENGTH_SHORT).show();
                else if(password.length() == 0)
                    Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                else{
                    avi.setVisibility(View.VISIBLE);
                    email.setEnabled(false);
                    password.setEnabled(false);
                    passwordAck.setEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User();
                            user.setEmail(email.getText().toString());
                            user.setPassword(password.getText().toString());
                            String res = RegisterService.register(user);
                            Log.d("RegisterActivity: ", res);
                            if(res.equals("success")) {
                                SharedPreferences.Editor editor = getSharedPreferences("LoginData", MODE_PRIVATE).edit();
                                editor.putString("email", email.getText().toString());
                                editor.putString("password", password.getText().toString());
                                editor.apply();
                                myHandler.obtainMessage(1).sendToTarget();
                            } else if(res.equals("Already registered")){
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
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, "用户已存在！", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(RegisterActivity.this, "注册异常！", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(RegisterActivity.this, "连接异常！", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(RegisterActivity.this, "连接超时！", Toast.LENGTH_SHORT).show();
                    break;
            }
            avi.setVisibility(View.GONE);
            email.setEnabled(true);
            password.setEnabled(true);
            passwordAck.setEnabled(true);
        }
    }

    private boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
}

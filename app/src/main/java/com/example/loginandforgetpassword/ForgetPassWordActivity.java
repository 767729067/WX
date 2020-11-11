package com.example.loginandforgetpassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ForgetPassWordActivity extends AppCompatActivity implements View.OnClickListener {
    private ConstraintLayout cl_goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置activity全屏并且清除导航栏样式
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_word);
        initView();
    }

    private void initView() {
        cl_goBack=findViewById(R.id.cl_goBack);
        cl_goBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.cl_goBack){
            finish();
        }
    }
}

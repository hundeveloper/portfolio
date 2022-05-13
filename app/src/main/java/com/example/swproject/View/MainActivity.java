package com.example.swproject.View;

import android.content.Intent;
import android.widget.TextView;

import com.example.swproject.Model.BaseActivity;
import com.example.swproject.Model.UserData;
import com.example.swproject.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding mainbinding;
    private TextView tv_id, tv_pass;
    private UserData userdata;

    @Override
    protected void createActivity() {
        mainbinding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);
        setContentView(mainbinding.getRoot());

        tv_id = mainbinding.tvId;
        tv_pass = mainbinding.tvPass;

        Intent intent = getIntent();
        userdata = new UserData();
        userdata.userId = intent.getStringExtra("userID");

        String Userpass = intent.getStringExtra("userPass");

        tv_id.setText(userdata.userId);
    }

    @Override
    protected void resumeActivity() {

    }

    @Override
    protected void startActivity() {

    }


    @Override
    protected void pauseActivity() {

    }

    @Override
    protected void onRestartActivity() {

    }

    @Override
    protected void destroyActivity() {

    }

}
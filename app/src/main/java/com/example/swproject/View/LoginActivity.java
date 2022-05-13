package com.example.swproject.View;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.swproject.Model.BaseActivity;
import com.example.swproject.R;
import com.example.swproject.ViewModel.LoginRequest;
import com.example.swproject.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText txtId, txtPw;
    private Button btn_login, btn_register;
    private ActivityLoginBinding loginBinding;

    @Override
    protected void createActivity() {
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.btnRegister.setOnClickListener(this);
        loginBinding.loginClick.setOnClickListener(this);

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

    // ********************************************************************************
    // event
    // ********************************************************************************
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_click:
                login();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    // ********************************************************************************
    // function
    // ********************************************************************************
    private void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void login() {
        txtId = loginBinding.txtId;
        txtPw = loginBinding.txtPw;

        //EditText에 현재 입력되어있는 값을 가져오도록 한다.
        String userID = txtId.getText().toString();
        String userPass = txtPw.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    //TODO : 인코딩 문제떄문에 한글 DB인 경우 로그인 불가
                    JSONObject jsonObject = new JSONObject(response);

                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        String userID = jsonObject.getString("userID");
                        String userPass = jsonObject.getString("userPw");
                        Toast.makeText(getApplicationContext(),"로그인에 성공",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userID", userID);
                        intent.putExtra("userPw", userPass);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }
}

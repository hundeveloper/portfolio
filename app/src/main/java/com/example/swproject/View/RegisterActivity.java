package com.example.swproject.View;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.swproject.Model.BaseActivity;
import com.example.swproject.R;
import com.example.swproject.Support.ToastSupport;
import com.example.swproject.ViewModel.RegisterRequest;
import com.example.swproject.ViewModel.Registeridcheck;
import com.example.swproject.databinding.ActivityRegisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText txt_id, txt_pw, txt_name, txt_email;
    private ActivityRegisterBinding registerbinding;
    private int overlap = 0;

    private ToastSupport showtoast = new ToastSupport(this);


    @Override
    protected void createActivity() {
        registerbinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerbinding.getRoot());

        registerbinding.btnRegister.setOnClickListener(this);
        registerbinding.btnCancel.setOnClickListener(this);
        registerbinding.checkId.setOnClickListener(this);
        registerbinding.backButton.setOnClickListener(this);

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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                register();
                break;
            case R.id.check_id:
                check_id();
                break;
            case R.id.btn_cancel :
                finish();
                break;
            case R.id.back_button:
                finish();
                break;

        }
    }

    private void check_id() {
        txt_id = registerbinding.txtId;
        String userId = txt_id.getText().toString();

        Response.Listener<String> responseListener = response -> {
            try {
                // String으로 그냥 못 보냄으로 JSON Object 형태로 변형하여 전송
                // 서버 통신하여 회원가입 성공 여부를 jsonResponse로 받음
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");
                if (success) { // TODO: 아이디 중복
                    showtoast.showToast("아이디가 존재합니다.");
                    //Toast.makeText(getApplicationContext(), "아이디가 존재합니다!", Toast.LENGTH_SHORT).show();
                    overlap = 0;
                    return;
                } else { // TODO: 아이디 중복 X
                    showtoast.showToast("사용가능한 아이디입니다.");
//                    Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다!.", Toast.LENGTH_SHORT).show();
                    overlap = 1;
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("에러", "데이터베이스 에러");
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        if(userId.isEmpty()) {
            //Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            showtoast.showToast("아이디를 입력해주세요.");
        } else{
            Registeridcheck registeridcheck = new Registeridcheck(userId, responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registeridcheck);
        }

    }

    private void register() {
        txt_id = registerbinding.txtId;
        txt_pw = registerbinding.txtPw;
        txt_name = registerbinding.txtName;
        txt_email = registerbinding.txtEmail;

        // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
        String userId = txt_id.getText().toString();
        String userPw = txt_pw.getText().toString();
        String userName = txt_name.getText().toString();
        String userEmail = txt_email.getText().toString();

        Response.Listener<String> responseListener = response -> {
            try {
                // String으로 그냥 못 보냄으로 JSON Object 형태로 변형하여 전송
                // 서버 통신하여 회원가입 성공 여부를 jsonResponse로 받음
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");

                if (success) { // TODO: 회원등록에 성공한 경우
                    showtoast.showToast("회원 등록에 성공하였습니다.");
//                    Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else { // TODO: 회원등록에 실패한 경우
                    showtoast.showToast("회원 등록에 실패하였습니다.");
//                    Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("에러","데이터베이스 에러");
            }

        };
        // 서버로 Volley를 이용해서 요청을 함.
        if(userId != null || userPw != null || userName != null ||  userEmail != null) {
            if (overlap == 1) { // overlap = 아이디 중복체크 여부
                RegisterRequest registerRequest = new RegisterRequest(userId, userPw, userName, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
                Log.d("출력값", queue.toString() + registerRequest.toString());
            } else {
                showtoast.showToast("아이디 중복체크를 해주세요.");
//                Toast.makeText(getApplicationContext(), "아이디 중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
            }
        } else{
            showtoast.showToast("빈 공란을 채워주세요.");
//            Toast.makeText(getApplicationContext(), "빈 공란을 채워주세요.", Toast.LENGTH_SHORT).show();
        }

    }
}

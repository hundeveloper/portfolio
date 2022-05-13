package com.example.swproject.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.35.205/Register.php";
    private Map<String, String> map;

    public RegisterRequest(String userId, String userPw, String userName, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userId",userId);
        map.put("userPw", userPw);
        map.put("userName", userName);
        map.put("userEmail", userEmail);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

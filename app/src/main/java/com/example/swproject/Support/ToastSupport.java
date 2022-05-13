package com.example.swproject.Support;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swproject.R;

public class ToastSupport {
    public Context context = null;
    public static Toast Utiltoast = null;
    public View layout = null;

    public ToastSupport(Context context){
        this.context = context;
    }

    public void showToast(String content){

        if(Utiltoast != null){
            Utiltoast.cancel(); // 중간에 떠있는 toast를 삭제
            Utiltoast = null;
        }

        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
        TextView message = layout.findViewById(R.id.toast_message);

        Utiltoast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        message.setText(content);
        Utiltoast.setView(layout);
        Utiltoast.show();


    }


}

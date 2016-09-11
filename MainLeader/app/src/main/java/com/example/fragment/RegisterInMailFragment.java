package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.activity.R;

/**
 * Created by ${hcc} on 2016/05/27.
 */
public class RegisterInMailFragment extends Fragment {

    private EditText mailEdt,passWdEdt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vp_register_mail, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mailEdt = (EditText) view.findViewById(R.id.mailEdt);
        passWdEdt = (EditText) view.findViewById(R.id.passWdEdt);
    }

    /*获取输入的内容*/
    public String getMail(){
        String num = mailEdt.getText().toString().trim();
        return num;
    }

    /*获取输入的内容*/
    public String getPassWd(){
        String passWd = passWdEdt.getText().toString().trim();
        return passWd;
    }
}

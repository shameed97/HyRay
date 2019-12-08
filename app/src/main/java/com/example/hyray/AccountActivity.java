package com.example.hyray;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {

    private TextView textMan,textCode,textEmail,textTel,textPhone,textFax;
    private String emp,empCode,empEmail,empTel,empPhone,empFax;
    private String []data;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Code For getting Value via Intent
        Intent intent=getIntent();
        data = intent.getStringArrayExtra("data");
        if (data != null) {
            emp=data[0];
            empCode=data[1];
            empEmail=data[2];
            empTel=data[3];
            empPhone=data[4];
            empFax=data[5];
        }
        //End Code For getting Value via Intent

        textMan=findViewById(R.id.manText);
        textCode=findViewById(R.id.codeText);
        textEmail=findViewById(R.id.emailText);
        textTel=findViewById(R.id.telText);
        textPhone=findViewById(R.id.phoneText);
        textFax=findViewById(R.id.faxText);
        imgBack=findViewById(R.id.back_icon);

        textMan.setText(emp);
        textCode.setText(empCode);
        textEmail.setText(empEmail);
        textTel.setText(empTel);
        textPhone.setText(empPhone);
        textFax.setText(empFax);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(AccountActivity.this,ContentActivity.class);
                intent1.putExtra("data",data);
                startActivity(intent1);
            }
        });

    }
}

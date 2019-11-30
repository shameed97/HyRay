package com.example.hyray;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ContentActivity extends AppCompatActivity {

    private TextView textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent=getIntent();
        String empName=intent.getStringExtra("EmpName");

        textName=findViewById(R.id.saleName);
        textName.setText(empName);
    }
}

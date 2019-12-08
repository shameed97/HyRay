package com.example.hyray;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class SalesproductActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonAll, buttonActive, buttonInactive;
    private ImageView imgBack;
    private FragmentManager fragmentManager;
    private String empId, data[];
    private ViewPager viewPager;
    private ScrollAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesproduct);

        //Code For getting Value via Intent
        Intent intent = getIntent();
        data = intent.getStringArrayExtra("data");
        if (data != null) {
            empId = data[6];
        }

        //End Code For getting Value via Intent

        buttonAll = findViewById(R.id.allButton);
        buttonActive = findViewById(R.id.activeButton);
        buttonInactive = findViewById(R.id.inactiveButton);
        imgBack = findViewById(R.id.back_icon);

       /* viewPager = findViewById(R.id.container_view);
        adapter=new ScrollAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);*/


        //Code Add AllFragment within Activity
        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("EmpID", empId);
            AllFragment allFragment = new AllFragment();
            allFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, allFragment, null);
            fragmentTransaction.commit();
            buttonAll.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        //Code Add AllFragment within Activity

        buttonAll.setOnClickListener(this);
        buttonActive.setOnClickListener(this);
        buttonInactive.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }


    //Code All ButtonClick
    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("EmpID", empId);

        switch (view.getId()) {

            case R.id.allButton:
                if (buttonAll.isClickable()) {
                    new ActiveFragment().onDetach();
                    new InactiveFragment().onDetach();
                    AllFragment allFragment = new AllFragment();
                    allFragment.setArguments(bundle);
                    this.fragmentManager.beginTransaction().replace(R.id.fragment_container, allFragment, null).commit();
                    buttonAll.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    buttonActive.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    buttonInactive.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    buttonAll.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                break;

            case R.id.activeButton:
                if (buttonActive.isClickable()) {
                    new AllFragment().onDetach();
                    ActiveFragment activeFragment = new ActiveFragment();
                    activeFragment.setArguments(bundle);
                    this.fragmentManager.beginTransaction().replace(R.id.fragment_container, activeFragment, null).commit();
                    buttonActive.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    buttonAll.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    buttonInactive.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    buttonActive.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                break;

            case R.id.inactiveButton:
                if (buttonInactive.isClickable()) {
                    new AllFragment().onDetach();
                    new ActiveFragment().onDetach();
                    InactiveFragment inactiveFragment = new InactiveFragment();
                    inactiveFragment.setArguments(bundle);
                    this.fragmentManager.beginTransaction().replace(R.id.fragment_container, inactiveFragment, null).commit();
                    buttonInactive.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    buttonActive.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    buttonAll.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    buttonInactive.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                break;
            case R.id.back_icon:
                Intent intent = new Intent(this, ContentActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
        }

    }
    //End Code All ButtonClick
}

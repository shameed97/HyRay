package com.example.hyray;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;

public class ContentActivity extends AppCompatActivity  {

    private TextView textName,textId;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavigationMenuView navigationMenuView;
    private String emp,empCode,empEmail,empTel,empPhone,empFax;
    private String []data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

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

        textName=findViewById(R.id.saleName);
        textId=findViewById(R.id.salesCode);
        textName.setText(emp);
        textId.setText(empCode);

        setNavigation();

    }

    public void setNavigation()
    {
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        navigationMenuView= (NavigationMenuView) navigationView.getChildAt(0);
        navigationMenuView.addItemDecoration(new DividerItemDecoration(ContentActivity.this,DividerItemDecoration.VERTICAL));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Log.d("sha","Its working");
                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        displayMessage("Home");
                        break;
                    case R.id.sales:
                        displayMessage("Sales Report");
                        break;
                    case R.id.listing:
                        displayMessage("AR Listing");
                        break;
                    case R.id.deck:
                        displayMessage("Training Deck");
                        break;
                    case R.id.about:
                        displayMessage("About");
                        break;
                    case R.id.acct:
                       Intent intent=new Intent(ContentActivity.this,AccountActivity.class);
                       intent.putExtra("data",data);
                       startActivity(intent);
                        break;

                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        LinearLayout layout=navigationView.findViewById(R.id.linear);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayMessage("Logout");
                drawerLayout.closeDrawers();
            }
        });
    }

    public void displayMessage(String message) {
        Toast.makeText(ContentActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    public void set(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}

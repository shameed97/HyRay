package com.example.hyray;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editE,editP;
    private AlertDialog.Builder builder;
    private String email,password,emp,status,serverMessage;
    private int id=1;
    private String log_url="http://52.163.56.202/hyrayapi/api/user/login";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editE=findViewById(R.id.userEdit);
        editP=findViewById(R.id.userPass);
        builder=new AlertDialog.Builder(this);
        progressBar=new ProgressBar(this);
    }

    public void signIn(View view) {
       login();
    }

    private void login() {

        email=editE.getText().toString();
        password=editP.getText().toString();

        if (email.equals("") || password.equals("")) {
            builder.setTitle("Something Went Wrong :");
            builder.setMessage("Please Fill All The Fields...");
            displayAlerts("input_error");
            //End Code For Checking Username and Password Field Empty
        }
        else
        {
            //Code For Getting Data From Mysql
            StringRequest stringRequest = new StringRequest(Request.Method.POST, log_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("res",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("jsonObject", jsonObject.toString());
                            serverMessage = jsonObject.getString("Message");
                            status = jsonObject.getString("StatusCode");
                            builder.setTitle("Login Information :");

                            if (status.equals("1"))
                            {
                                    JSONObject Object = jsonObject.getJSONObject("Content");
                                    emp=Object.getString("EmpName");
                                    Log.d("EmpName",emp);
                                    builder.setMessage(serverMessage+"\n"+"Welcome "+emp);

                            }
                            else
                            {
                                builder.setMessage(serverMessage);
                            }
                            displayAlerts(status);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("kee", error.toString());
                    error.printStackTrace();

                }
            }) {
                //Code For Send Data's to PHP file
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("AppTypeID",String.valueOf(id));
                    params.put("EmailAddress", email);
                    params.put("Password", password);
                    return params;
                }
                //End `Code For Send Data's to PHP file
            };
            Mysingleton.getInstance(LoginActivity.this).addToRequest(stringRequest);
        }


    }

    public void displayAlerts(final String message) {
        //Code For Alert Dialog
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (message.equals("input_error")) {
                    editE.setText("");
                    editP.setText("");
                }
                if (message.equals("1")) {
                    Intent intent=new Intent(LoginActivity.this,ContentActivity.class);
                    intent.putExtra("EmpName",emp);
                    startActivity(intent);
                }
                if (message.equals("2")) {
                    editE.setText("");
                    editP.setText("");
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //End Code For Alert Dialog
    }
}

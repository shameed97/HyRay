package com.example.hyray;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editE,editP;
    private AlertDialog.Builder builder;
    private String email,password,status,serverMessage;
    private String emp,empCode,empEmail,empTel,empPhone,empFax,empId;
    private int id=1;
    private String log_url="http://52.163.56.202/hyrayapi/api/user/login";
    private ProgressBar progressBar;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editE=findViewById(R.id.userEdit);
        editP=findViewById(R.id.userPass);
        builder=new AlertDialog.Builder(this);
        progressBar=findViewById(R.id.progressBar);
        button=findViewById(R.id.signButton);
    }

    public void signIn(View view)
    {
        button.setVisibility(View.INVISIBLE);
        login();
        progressBar.setVisibility(View.VISIBLE);
    }


    private void login() {

        email=editE.getText().toString();
        password=editP.getText().toString();

        if (email.equals("") || password.equals("")) {
            builder.setTitle("Something Went Wrong :");
            builder.setMessage("Please Fill All The Fields...");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    progressBar.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.VISIBLE);
                    editE.setText("");
                    editP.setText("");
                }

            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            //End Code For Checking Username and Password Field Empty
        }
        else
        {
            //Code for Volley,StringRequest.....
            StringRequest stringRequest = new StringRequest(Request.Method.POST, log_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("res",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("jsonObject", jsonObject.toString());
                            serverMessage = jsonObject.getString("Message");
                            status = jsonObject.getString("StatusCode");

                            if (status.equals("1"))
                            {
                                    JSONObject Object = jsonObject.getJSONObject("Content");
                                    emp=Object.getString("EmpName");
                                    empCode=Object.getString("EmpCode");
                                    empEmail=Object.getString("UserLoginEmailID");
                                    empTel=Object.getString("TelephoneNo");
                                    empPhone=Object.getString("PhoneNo");
                                    empFax=Object.getString("FaxNo");
                                    empId=Object.getString("EmpID");


                                progressBar.setVisibility(View.INVISIBLE);
                                Intent intent=new Intent(LoginActivity.this,ContentActivity.class);
                                //intent.putExtra("EmpName",emp);
                                String[] data = {emp, empCode, empEmail, empTel, empPhone, empFax,empId};
                                intent.putExtra("data",data);
                                startActivity(intent);
                            }
                            else
                            {
                                builder.setTitle("Login Information :");
                                builder.setMessage(serverMessage);
                                builder.setCancelable(false);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        progressBar.setVisibility(View.INVISIBLE);
                                        button.setVisibility(View.VISIBLE);
                                        editE.setText("");
                                        editP.setText("");
                                    }
                                });
                                AlertDialog alertDialog=builder.create();
                                alertDialog.show();
                            }

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
                //Code For Send Data's to Server
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("AppTypeID",String.valueOf(id));
                    params.put("EmailAddress", email);
                    params.put("Password", password);
                    return params;
                }
                //End Code For Send Data's to Server
            };
            Mysingleton.getInstance(LoginActivity.this).addToRequest(stringRequest);
        }


    }
    //End Code for Volley,StringRequest.....


}

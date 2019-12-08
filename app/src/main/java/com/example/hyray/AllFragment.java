package com.example.hyray;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {

    private String EmpId, EmpName, EmpCode, EmpActive;
    private ListView listView;
    private ListViewAdapter listviewAdapter;
    private ArrayList<detail> arrayList = new ArrayList<detail>();
    private SearchView searchView;

    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        listView = view.findViewById(R.id.listView_all);
        assert getArguments() != null;
        EmpId = getArguments().getString("EmpID");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://52.163.56.202/hyrayapi/api/customer/list?CustomerType=all&SalesmanId=" + EmpId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sha", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Content");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject Object = jsonArray.getJSONObject(i);
                        EmpName = Object.getString("CustomerName");
                        EmpCode = Object.getString("CustomerCode");
                        EmpActive = Object.getString("isActive");
                        detail det=new detail(EmpName,EmpCode,EmpActive);
                        //bind all values in an array
                        arrayList.add(det);
                        listviewAdapter = new ListViewAdapter(getContext(),arrayList);
                        listView.setAdapter(listviewAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", error.toString());
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        Mysingleton.getInstance(getContext()).addToRequest(stringRequest);

        //Code for SearchView Listener
        searchView=getActivity().findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                if (TextUtils.isEmpty(s))
                {
                    listviewAdapter.filter("");
                    listView.clearTextFilter();
                }
                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://52.163.56.202/hyrayapi/api/customer/list?CustomerType=all&SalesmanId="+EmpId+"&KeywordSearch="+s, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("sha", "http://52.163.56.202/hyrayapi/api/customer/list?CustomerType=all&SalesmanId="+EmpId+"&KeywordSearch="+s+"");
                            Log.d("sha", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("Content");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject Object = jsonArray.getJSONObject(i);
                                    EmpName = Object.getString("CustomerName");
                                    EmpCode = Object.getString("CustomerCode");
                                    EmpActive = Object.getString("isActive");
                                    listviewAdapter.filter(EmpName);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("response", error.toString());
                            error.printStackTrace();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            return params;
                        }
                    };
                    Mysingleton.getInstance(getContext()).addToRequest(stringRequest);
                }

                return false;
            }
        });
        //End Code for SearchView Listener

        return view;
    }

}

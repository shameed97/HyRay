package com.example.hyray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActiveViewAdapter extends BaseAdapter {

    private ArrayList<detail> arrayList = new ArrayList<>();
    private List<detail> list;
    private Context Mcontext;
    LayoutInflater inflater;

    public ActiveViewAdapter(Context context, List<detail> list) {
        Mcontext = context;
        this.list = list;
        inflater = LayoutInflater.from(Mcontext);
        this.arrayList.addAll(list);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ProductHolder {
        TextView textView, textView1;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ListViewAdapter.ProductHolder productHolder;
        if (convertView == null) {
            row = inflater.inflate(R.layout.active_list, null);
            productHolder = new ListViewAdapter.ProductHolder();
            productHolder.textView = row.findViewById(R.id.Id_list);
            productHolder.textView1 = row.findViewById(R.id.name_list);
            row.setTag(productHolder);
        } else {
            productHolder = (ListViewAdapter.ProductHolder) row.getTag();
        }

        final detail det = (detail) getItem(position);
        String Active = det.getEmpActive();
        if (Active.equals("true")) {
            productHolder.textView.setText(det.getEmpCode());
            productHolder.textView1.setText(det.getEmpName());
        } else if (det.equals("") || det.equals(null)) {
            row = inflater.inflate(R.layout.list_empty, null);

        }

        //OnClickListener for Listview row Click
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ename = det.getEmpName();
                String Ecode = det.getEmpCode();
                Toast.makeText(Mcontext, Ename + Ecode + "" + "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        // End OnClickListener for Listview row Click

        return row;
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault()).trim();
        list.clear();
        if (charText.length() == 0) {
            list.addAll(arrayList);
        } else {
            for (detail detail : arrayList) {
                if (detail.getEmpName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(detail);
                }
            }
        }
        notifyDataSetChanged();
    }

}



package com.example.hyray;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<detail> arrayList = new ArrayList<>();
    private List<detail> list;
    private Context Mcontext;
    private LayoutInflater inflater;
    private Dialog dialog;
    private TextView textIN;
    private Button buttonCancel, buttonConfirm;

    public ListViewAdapter(Context context, List<detail> list) {
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
        ImageView imageView;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ProductHolder productHolder;
        if (convertView == null) {
            row = inflater.inflate(R.layout.active_list, null);
            productHolder = new ProductHolder();
            productHolder.textView = row.findViewById(R.id.Id_list);
            productHolder.textView1 = row.findViewById(R.id.name_list);
            productHolder.imageView = row.findViewById(R.id.Image_list);


            row.setTag(productHolder);
        } else {
            productHolder = (ProductHolder) row.getTag();
        }

        final detail det = (detail) getItem(position);
        productHolder.textView.setText(det.getEmpCode());
        productHolder.textView1.setText(det.getEmpName());
        String Active = det.getEmpActive();
        if (!Active.equals("true")) {
            productHolder.imageView.setColorFilter(ContextCompat.getColor(Mcontext, R.color.grey), PorterDuff.Mode.MULTIPLY);
        } else if (det.equals("") || det == null) {
            row = inflater.inflate(R.layout.list_empty, parent, false);
        }

        //OnClickListener for Listview row Click
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ename = det.getEmpName();
                String Ecode = det.getEmpCode();

                //Code for Dialog box
                dialog = new Dialog(Mcontext);
                dialog.setContentView(R.layout.popup_row);
                dialog.setCancelable(false);
                textIN = dialog.findViewById(R.id.IdandName);
                buttonCancel = dialog.findViewById(R.id.cancel);
                buttonConfirm = dialog.findViewById(R.id.confirm);

                textIN.setText("Place an order for " + Ecode + " " + Ename);

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                buttonConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Mcontext, PlacorderActivity.class);
                        Mcontext.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                //Code for Dialog box


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



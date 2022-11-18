package com.dku.projectmuckansang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dku.projectmuckansang.Database.ProductData;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    ArrayList<ProductData> items = new ArrayList<>();
    Context context;
    int layoutID;

    public ListViewAdapter(Context context, int layoutID, ArrayList<ProductData> items) {
        this.items = items;
        this.context = context;
        this.layoutID = layoutID;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layoutID, viewGroup, false);

        //find item view
        TextView textView = view.findViewById(R.id.listItem);

        //set view
        textView.setText(items.get(i).toString());
        return view;
    }

    public void deleteItem(ProductData productData) {
        items.remove(productData);
        notifyDataSetChanged();
    }
}
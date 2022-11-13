package com.dku.projectmuckansang.Trash;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.dku.projectmuckansang.R;

import java.util.ArrayList;

public class TrashActivity extends AppCompatActivity {
    ListView trashListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);

        trashListView = findViewById(R.id.trashListView);
        ArrayList<String> trashList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            trashList.add("trash " + i);
        }
        setListViews(trashListView, trashList);
    }

    private void setListViews(ListView listView, ArrayList<String> arrayList) {
        ArrayList<String> mArrayList = arrayList;
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mArrayList);
        listView.setAdapter(listAdapter);
        setListViewHeightBasedOnChildren(listView);
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            int numberOfItems = listAdapter.getCount();
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }
            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }
}
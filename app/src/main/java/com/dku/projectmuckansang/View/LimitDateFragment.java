package com.dku.projectmuckansang.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.dku.projectmuckansang.R;

import java.util.ArrayList;

public class LimitDateFragment extends Fragment {
    private ListView dangerList;
    private ListView becareList;
    private ListView safeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_limit_date, container, false);

        initializeListView(rootView);

        ArrayList<String> danger = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            danger.add("danger" + i);
        }
        setListViews(dangerList, danger);

        ArrayList<String> becare = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            becare.add("becare " + i);
        }
        setListViews(becareList, becare);

        return rootView;
    }

    private void initializeListView(ViewGroup viewGroup) {
        dangerList = viewGroup.findViewById(R.id.dangerList);
        becareList = viewGroup.findViewById(R.id.becareList);
        safeList = viewGroup.findViewById(R.id.safeList);
    }

    private void setListViews(ListView listView, ArrayList<String> arrayList) {
        ArrayList<String> mArrayList = arrayList;
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mArrayList);
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
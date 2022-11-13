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

public class CategoryFragment extends Fragment {
    private ListView meetList;
    private ListView vegetableList;
    private ListView dairyProductsList;
    private ListView eggList;
    private ListView beanList;
    private ListView sourceList;
    private ListView drinkList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_category, container, false);

        /*
         * 각 카테고리별 식재료 정보를 DB로부터 가져와 출력
         */
        initializeListView(rootView);

        ArrayList<String> meetArrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            meetArrayList.add("meet " + i);
        }
        setListViews(meetList, meetArrayList);


        ArrayList<String> vegetableArrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            vegetableArrayList.add("vegetable " + i);
        }
        setListViews(vegetableList, vegetableArrayList);

        return rootView;
    }

    private void initializeListView(ViewGroup view) {
        meetList = view.findViewById(R.id.meetList);
        vegetableList = view.findViewById(R.id.vegetableList);
        dairyProductsList = view.findViewById(R.id.dairyProductsList);
        eggList = view.findViewById(R.id.eggList);
        beanList = view.findViewById(R.id.beanList);
        sourceList = view.findViewById(R.id.sourceList);
        drinkList = view.findViewById(R.id.drinkList);
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
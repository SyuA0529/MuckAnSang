package com.dku.projectmuckansang.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.dku.projectmuckansang.Database.DatabaseHelper;
import com.dku.projectmuckansang.Database.ProductData;
import com.dku.projectmuckansang.R;

import java.util.ArrayList;

public class LimitDateFragment extends Fragment {
    private ListView[] listViews = new ListView[3];
    private final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
    private boolean wantDelete = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_limit_date, container, false);
        initializeListView(rootView);
        alert.setMessage("정말로 삭제하시겠습니까 ?").setCancelable(false)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wantDelete = true;
                    }
                }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                });
        return rootView;
    }

    private void initializeListView(ViewGroup viewGroup) {
        listViews[0] = viewGroup.findViewById(R.id.dangerList);
        listViews[1] = viewGroup.findViewById(R.id.becareList);
        listViews[2] = viewGroup.findViewById(R.id.safeList);
    }

    private void setListViews(ListView listView, ArrayList<ProductData> arrayList) {
        ArrayList<ProductData> mArrayList = arrayList;
        ArrayAdapter<ProductData> listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mArrayList);
        listView.setAdapter(listAdapter);
        setListViewHeightBasedOnChildren(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // check delete it
                alert.create().show();
                // if yes, delete it and update list view
                if(wantDelete) {
                    wantDelete = false;
                    //delete
                    ProductData product = (ProductData) adapterView.getItemAtPosition(i);
                    deleteProductFromDatabase(product);
                    updateListView();
                }
            }
        });
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

    private void updateListView() {
        DatabaseHelper helper = new DatabaseHelper(getActivity());
        setListViews(listViews[0], helper.getProductListBetweenSpecificPeriod(0, 3));
        setListViews(listViews[1], helper.getProductListBetweenSpecificPeriod(3, 10));
        setListViews(listViews[2], helper.getProductListOverSpecificPeriod(10));
    }

    private void deleteProductFromDatabase(ProductData product) {
        DatabaseHelper helper = new DatabaseHelper(getActivity());
        helper.deleteProductById(product.getProductID());
    }
}
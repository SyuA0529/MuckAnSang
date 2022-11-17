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

public class CategoryFragment extends Fragment {
    private ListView[] listViews = new ListView[7];
    private final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
    private boolean wantDelete = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_category, container, false);
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
        /*
         * 각 카테고리별 식재료 정보를 DB로부터 가져와 출력
         */
        initializeListView(rootView);
        updateListView();
        return rootView;
    }

    private void initializeListView(ViewGroup view) {
        listViews[0] = view.findViewById(R.id.meetList);
        listViews[1] = view.findViewById(R.id.vegetableList);
        listViews[2] = view.findViewById(R.id.dairyProductsList);
        listViews[3] = view.findViewById(R.id.eggList);
        listViews[4] = view.findViewById(R.id.beanList);
        listViews[5] = view.findViewById(R.id.sourceList);
        listViews[6] = view.findViewById(R.id.drinkList);

        updateListView();
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
        String[] bigCategoryList = helper.getBigCategoryList();

        for (int i = 0; i < bigCategoryList.length; i++) {
            //get specific ProductList of Category
            ArrayList<ProductData> curCategoryProductList = helper.getSpecificCategoryProductList(bigCategoryList[i]);
            //updateListView
            setListViews(listViews[i], curCategoryProductList);
        }
    }

    private void deleteProductFromDatabase(ProductData product) {
        DatabaseHelper helper = new DatabaseHelper(getActivity());
        helper.deleteProductById(product.getProductID());
    }
}
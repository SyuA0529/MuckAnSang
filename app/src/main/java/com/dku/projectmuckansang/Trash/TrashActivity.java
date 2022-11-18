package com.dku.projectmuckansang.Trash;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.dku.projectmuckansang.Database.DatabaseHelper;
import com.dku.projectmuckansang.Database.ProductData;
import com.dku.projectmuckansang.R;

import java.util.ArrayList;

public class TrashActivity extends AppCompatActivity {
    private ListView[] listViews = new ListView[7];
    private final AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
    private boolean wantDelete = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
        updateListView();
    }

    private void setListViews(ListView listView, ArrayList<ProductData> arrayList) {
        ArrayList<ProductData> mArrayList = arrayList;
        ArrayAdapter<ProductData> listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, mArrayList);
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
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        String[] bigCategoryList = helper.getBigCategoryList();

        for (int i = 0; i < bigCategoryList.length; i++) {
            //get specific ProductList of Category
            ArrayList<ProductData> curCategoryProductList = helper.getTrashListNyCategory(bigCategoryList[i]);
            //updateListView
            setListViews(listViews[i], curCategoryProductList);
        }
    }

    private void deleteProductFromDatabase(ProductData product) {
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        helper.deleteProductById(product.getProductID());
    }
}
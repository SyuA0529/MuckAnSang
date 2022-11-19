package com.dku.projectmuckansang.Trash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.dku.projectmuckansang.Database.DatabaseHelper;
import com.dku.projectmuckansang.Database.ProductData;
import com.dku.projectmuckansang.ListViewAdapter;
import com.dku.projectmuckansang.R;

import java.util.ArrayList;

public class TrashActivity extends AppCompatActivity {
    private ListView listView;
    private AlertDialog.Builder alert;

    private ProductData selectedProduct = null;
    private AdapterView<?> selectedAdapterView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
        listView = findViewById(R.id.trashListView);

        alert = new AlertDialog.Builder(getApplicationContext());
        alert.setMessage("정말로 삭제하시겠습니까 ?").setCancelable(false)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListViewAdapter adapter = (ListViewAdapter) selectedAdapterView.getAdapter();
                        deleteProductFromDatabase(selectedProduct);
                        adapter.deleteItem(selectedProduct);

                        selectedAdapterView = null;
                        selectedProduct = null;

                        setListViewHeightBasedOnChildren();
                    }
                }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedProduct = null;
                        selectedAdapterView = null;
                    }
                });
        updateListView();
    }

    private void setListView(ArrayList<ProductData> arrayList) {
        ArrayList<ProductData> mArrayList = arrayList;
        ListViewAdapter listAdapter = new ListViewAdapter(this, R.layout.list_view, mArrayList);
        listView.setAdapter(listAdapter);
        setListViewHeightBasedOnChildren();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // check delete it
                selectedProduct = (ProductData) adapterView.getItemAtPosition(i);
                selectedAdapterView = adapterView;
                alert.create().show();
            }
        });
    }

    private void setListViewHeightBasedOnChildren() {
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

        ArrayList<ProductData> trashProductList = new ArrayList<>();
        for (int i = 0; i < bigCategoryList.length; i++) {
            //get specific ProductList of Category
            ArrayList<ProductData> curCategoryProductList = helper.getTrashListNyCategory(bigCategoryList[i]);
            //updateListView
            for (int j = 0; j < curCategoryProductList.size(); j++) {
                trashProductList.add(curCategoryProductList.get(j));
            }
        }
        setListView(trashProductList);
    }

    private void deleteProductFromDatabase(ProductData product) {
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        helper.deleteProductById(product.getProductID());
    }
}
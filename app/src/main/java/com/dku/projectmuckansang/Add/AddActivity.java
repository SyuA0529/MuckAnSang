package com.dku.projectmuckansang.Add;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dku.projectmuckansang.R;

public class AddActivity extends AppCompatActivity {
    TextView itemNameText;
    TextView itemCountText;

    Spinner bigCategorySpinner;
    Spinner detailCategorySpinner;
    String[] bigCategorys;
    String bigCategory;
    String[] detailCategorys;
    String detailCategory;

    LinearLayout periodLayout;
    TextView editPeriod;

    int year, month, day;
    boolean periodVisible = false;

    Button onlyAddButton;
    Button addNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        itemNameText = findViewById(R.id.nameEdit);
        itemCountText = findViewById(R.id.countEdit);

        year = 0; month = 0; day = 0;

        initializeSpinners();
        initializeCheckBox();
        initializeButtons();
    }

    public void getDatePickerResult(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        editPeriod.setText(year + "년 " + month + "월 " + day + "일");
    }

    private boolean addItemToDatabse() {
        //check input is correct
        try {
            //check period is auto
            if(periodVisible) {
                if(editPeriod.getText().equals("")) {
                    return false; //check period is setted
                }
            }

            //check input value is correct
            String itemName = String.valueOf(itemNameText.getText());
            int itemCount = Integer.parseInt(String.valueOf(itemCountText.getText()));
            if(itemName.equals("") || (itemCount <= 0)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        //add item to database
        return true;
    }

    /*
     * About Period and CheckBox
     */
    private void initializeCheckBox() {
        CheckBox checkBox = findViewById(R.id.checkButton);
        periodLayout = findViewById(R.id.periodLayout);
        editPeriod = findViewById(R.id.editPeriod);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                periodVisible = b;
                if(periodVisible) {
                    periodLayout.setVisibility(View.VISIBLE);
                    new DatePickerFragment().show(getSupportFragmentManager(), "datePicker");
                }
                else periodLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    /*
     * About Buttons
     */
    private void initializeButtons() {
        onlyAddButton = findViewById(R.id.onlyAdd);
        addNextButton = findViewById(R.id.addNext);

        onlyAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AddActivity", "startAddItem");
                if(addItemToDatabse()) {
                    Toast.makeText(AddActivity.this, "상품이 추가되었습니다", Toast.LENGTH_SHORT).show();
                    finish();
                }

                else Toast.makeText(AddActivity.this, "다시 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        addNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AddActivity", "startAddItem");
                if(addItemToDatabse()) {
                    Toast.makeText(AddActivity.this, "상품이 추가되었습니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent()
                            .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            .setClass(getApplicationContext(), AddActivity.class);
                    startActivity(intent);
                    finish();
                }

                else Toast.makeText(AddActivity.this, "다시 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * About Spinners
     */
    private void initializeSpinners() {
        bigCategorySpinner = findViewById(R.id.bigCategory);
        detailCategorySpinner = findViewById(R.id.detailCategory);

        bigCategorys = getResources().getStringArray(R.array.category);

        bigCategorySpinner.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, bigCategorys
        ));
        bigCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bigCategory = bigCategorys[i];
                selectDetailCategory(bigCategory);
                setDetailCategorySpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                bigCategory = "";
                detailCategory = "";
                detailCategorys = null;
                detailCategorySpinner.setVisibility(View.INVISIBLE);
            }
        });

        selectDetailCategory(bigCategorys[0]);
        setDetailCategorySpinner();
    }

    private void setDetailCategorySpinner() {
        if(detailCategorys != null) {
            detailCategorySpinner.setVisibility(View.VISIBLE);
            detailCategorySpinner.setAdapter(new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, detailCategorys
            ));
        }
        else {
            detailCategory = "";
            detailCategorySpinner.setVisibility(View.INVISIBLE);
        }
    }

    private void selectDetailCategory(String bigCategory) {
        if(bigCategory.equals("육류")) detailCategorys = getResources().getStringArray(R.array.meat);
        else if(bigCategory.equals("채소류")) detailCategorys = getResources().getStringArray(R.array.vegetable);
        else if(bigCategory.equals("유제품류")) detailCategorys = getResources().getStringArray(R.array.dairyProducts);
        else if(bigCategory.equals("난류")) detailCategorys = getResources().getStringArray(R.array.egg);
        else if(bigCategory.equals("콩류")) detailCategorys = getResources().getStringArray(R.array.bean);
        else detailCategorys = null;

        if(detailCategorys != null) detailCategory = detailCategorys[0];
    }
}
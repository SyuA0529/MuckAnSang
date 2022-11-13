package com.dku.projectmuckansang.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.dku.projectmuckansang.R;

public class ViewActivity extends AppCompatActivity {
    CategoryFragment categoryFragment;
    LimitDateFragment limitDateFragment;

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_activity);

        categoryFragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.categoryFragment);
        limitDateFragment = new LimitDateFragment();

        Button button = findViewById(R.id.changeFragmentButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, limitDateFragment).commit();
                    button.setText("종류별 정렬");
                    index++;
                }

                else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, categoryFragment).commit();
                    button.setText("유통기한 임박순 정렬");
                    index--;
                }
            }
        });
    }
}
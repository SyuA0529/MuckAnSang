package com.dku.projectmuckansang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dku.projectmuckansang.Add.AddActivity;
import com.dku.projectmuckansang.Database.DatabaseHelper;
import com.dku.projectmuckansang.Noti.BootReceiver;
import com.dku.projectmuckansang.Noti.NotiService;
import com.dku.projectmuckansang.Trash.TrashActivity;
import com.dku.projectmuckansang.View.ViewActivity;

public class MainActivity extends AppCompatActivity {
    TextView welcomeText;
    String nickName = "";

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        nickName = preferences.getString("NickName", "");
        welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("\"" + nickName + "님, 오늘도 건강한 하루 되세요!" + "\"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();
        Intent intent = new Intent(getApplicationContext(), BootReceiver.class);
        sendBroadcast(intent);

        Log.d("SyuA", "\n" +
                " _____                 ___  \n" +
                "/  ___|               / _ \\ \n" +
                "\\ `--.  _   _  _   _ / /_\\ \\\n" +
                " `--. \\| | | || | | ||  _  |\n" +
                "/\\__/ /| |_| || |_| || | | |\n" +
                "\\____/  \\__, | \\__,_|\\_| |_/\n" +
                "         __/ |              \n" +
                "        |___/               \n");

        Button button = findViewById(R.id.testDecrease);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                helper.updateProductPeriod();
            }
        });

        Button button2 = findViewById(R.id.notiTest);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startForeground = new Intent(getApplicationContext(), NotiService.class);
                startService(startForeground);
            }
        });
    }

    private void initializeButtons() {
        Button viewButton = findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent()
                        .setClass(getApplicationContext(), ViewActivity.class);
                startActivity(intent);
            }
        });

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent()
                        .setClass(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });

        Button deleteButton = findViewById(R.id.trashButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent()
                        .setClass(getApplicationContext(), TrashActivity.class);
                startActivity(intent);
            }
        });
    }
}
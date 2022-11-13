package com.dku.projectmuckansang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NickNameActivity extends AppCompatActivity {
    EditText nickNameEdit;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        //check nickname is setted
        String nickName = preferences.getString("NickName", "");
        if(!nickName.equals("")) startMainActivity();

        //if not
        nickNameEdit = findViewById(R.id.nickNameEdit);
        Button button = findViewById(R.id.nickNameButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickName = String.valueOf(nickNameEdit.getText());
                if(nickName.equals(""))
                    Toast.makeText(NickNameActivity.this, "잘못된 닉네임입니다", Toast.LENGTH_SHORT).show();
                else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("NickName", nickName);
                    editor.commit();
                    Toast.makeText(NickNameActivity.this, "닉네임이 설정되었습니다", Toast.LENGTH_SHORT).show();
                    startMainActivity();
                }
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent()
                .setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
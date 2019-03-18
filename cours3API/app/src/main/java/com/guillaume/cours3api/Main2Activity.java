package com.guillaume.cours3api;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        GetData();
    }

    private void GetData()
    {
        String intent = getIntent().getStringExtra("txtEdit.Text");
        executeToast(intent);
    }
    private void executeToast(String toToast)
    {
        Toast.makeText(getApplicationContext(), toToast, Toast.LENGTH_LONG).show();
    }
}

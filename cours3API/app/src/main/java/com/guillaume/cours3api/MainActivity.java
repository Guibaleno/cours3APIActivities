package com.guillaume.cours3api;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtEdit = findViewById(R.id.txt);
        SetListeners();
    }

    private void SetListeners()
    {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToConnectActivity();
            }
        });
    }
    private void moveToConnectActivity(){
        Intent intent = new Intent(this, Main2Activity.class);
        String textToSend = txtEdit.getText().toString();
        intent.putExtra("txtEdit.Text", textToSend);
        startActivity(intent);
    }
}

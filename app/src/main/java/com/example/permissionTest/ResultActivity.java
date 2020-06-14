package com.example.permissionTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textView = findViewById(R.id.TextViewId);

        if(getIntent().getExtras() != null){
            String message = getIntent().getExtras().getString("message");
            textView.setText(message);
        }
    }
}

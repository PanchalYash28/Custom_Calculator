package com.yashp.calculator;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    private TextView tvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        tvHistory = findViewById(R.id.tv_history);

        // Retrieve history data passed from MainActivity
        Intent intent = getIntent();
        String history = intent.getStringExtra("HISTORY");
        tvHistory.setText(history);

        // Back button to return to calculator screen
        Button btnBack = findViewById(R.id.btn_back_to_calculator);
        btnBack.setOnClickListener(view -> finish());
    }
}

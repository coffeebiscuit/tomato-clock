package com.pizza.toma;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Homepage.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button timerBtn;
    private Button historyBtn;
    private TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init
        dateView = findViewById(R.id.dateView);
        timerBtn = findViewById(R.id.timerBtn);
        historyBtn = findViewById(R.id.historyBtn);
        getTime();

        // Set listeners
        timerBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
    }

    /** Get current system time. */
    void getTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("MMM.dd E yyyy");
        String str = sdf.format(date);
        dateView.setText(str);
    }

    @Override
    public void onClick(View v) {
        if (v == timerBtn){
            startActivity(new Intent(this, TimerActivity.class));
            overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);
            Log.d("MainActivity", "Timer button clicked.");
        }else if(v == historyBtn){
            startActivity(new Intent(this, HistoryActivity.class));
            overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);
            Log.d("MainActivity", "History button clicked.");
        }
    }

}

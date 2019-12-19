package com.example.toma;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener{
    private int timeValue;
    private CountDownTimer timer;
    private Button startBtn;
    private Button returnBtn;
    private ImageButton imgBtn;
    private NumberPicker numPicker;
    private TextView timerTextView;
    private String startTime;
    private SimpleDateFormat sdf;

    private SoundPool sound;
    private HashMap<Integer, Integer> soundPoolMap;
    private Vibrator vibrator;

    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // Init
        timeValue = 25;
        startBtn = findViewById(R.id.startBtn);
        returnBtn = findViewById(R.id.returnBtn);
        imgBtn = findViewById(R.id.imgBtn);
        numPicker = findViewById(R.id.numberPicker);
        timerTextView = findViewById(R.id.timerView);
        timerTextView.setText(timeValue + " : 00");

        sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(1, sound.load(this, R.raw.sound, 1));
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Set listeners
        startBtn.setOnClickListener(this);
        returnBtn.setOnClickListener(this);
        imgBtn.setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            TimerActivity.this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == startBtn){
            TimerStart();
            Log.d("TimerActivity", "Start button clicked.");
        }else if(v == returnBtn){
            TimerActivity.this.finish();
            overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);
            Log.d("TimerActivity", "Return button clicked.");
        }else if(v == imgBtn){
            TimerSetting();
            Log.d("TimerActivity", "Timer setting button clicked.");
        }
    }

    /** Set count-down duration. */
    public void TimerSetting(){
        startBtn.setVisibility(View.VISIBLE);
        // Display the transparent image
        imgBtn.setImageDrawable(getResources().getDrawable(R.mipmap.tomato_512px_1));
        imgBtn.setAlpha(1.0f);
        numPicker.setVisibility(View.VISIBLE);
        timerTextView.setVisibility(View.INVISIBLE);

        if (timer != null) {
            timer.cancel();
        }

        if (numPicker != null) {
            // Displayed values
            final String[] values = { "5", "8", "10", "15", "20", "25", "30", "35", "40", "45",
                    "50","55", "60", "65", "70", "75", "80", "85", "90"};
            numPicker.setDisplayedValues(values);
            numPicker.setMinValue(0);
            numPicker.setMaxValue(values.length - 1);
            // Default value is 25
            numPicker.setValue(5);
            numPicker.setWrapSelectorWheel(false);
            numPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            numPicker.setFocusable(true);
            numPicker.setFocusableInTouchMode(true);

            numPicker.setOnValueChangedListener((picker, oldVal, newVal) ->
                    timeValue = Integer.valueOf(numPicker.getDisplayedValues()[numPicker.getValue()]));
        }
    }

    /** Start count-down timer.
     * CountDownTimer starts with timeValue minutes and every onTick is 1 second.
     */
    public void TimerStart(){
        //get start time
        Date date = new Date(System.currentTimeMillis());
        startTime = sdf.format(date);

        startBtn.setVisibility(View.INVISIBLE);
        imgBtn.setImageDrawable(getResources().getDrawable(R.mipmap.tomato_512px));
        imgBtn.setAlpha(0.6f);
        numPicker.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.VISIBLE);

        timer = new CountDownTimer(timeValue * 60000, 1000) {
            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                timerTextView.setText(text);
            }

            public void onFinish() {
                // If longer than 25 min, produce a random result between 1-9
                int type = timeValue < 25 ? 0 : (int) ( 1 + Math.random() * (9));
                SaveData(startTime, timeValue, type);
                PlaySound(1);
            }
        }.start();
    }


    public void PlaySound(int sound){
        AudioManager mgr = (AudioManager) this
                .getSystemService(Context.AUDIO_SERVICE);
        float currentVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = currentVolume / maxVolume;
        vibrator.vibrate(8000);
        this.sound.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);
    }


    public void SaveData(String time, int duration, int type){
        Record record = new Record(time, duration, type);
        viewModel.insert(record);
        Log.d("saveData", time + "/" + duration + "/" + type);
    }

}

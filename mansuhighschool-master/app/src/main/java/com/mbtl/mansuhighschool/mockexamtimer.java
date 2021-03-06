package com.mbtl.mansuhighschool;
import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;
public class mockexamtimer extends AppCompatActivity {
    LinearLayout timeCountSettingLV, timeCountLV;
    TextView hourTV, minuteTV, secondTV, finishTV, subject;
    Button startBtn, stopBtn, pauseBtn;
    ImageButton ko, ma, en, hi, so, sc;
    private Thread timeThread = null;
    private Boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mockexamtimer);
        ko = (ImageButton)findViewById(R.id.korean);
        en = (ImageButton)findViewById(R.id.english);
        ma = (ImageButton)findViewById(R.id.math);
        hi = (ImageButton)findViewById(R.id.history);
        so = (ImageButton)findViewById(R.id.society);
        sc = (ImageButton)findViewById(R.id.science);
        subject = (TextView)findViewById(R.id.subject);
        timeCountSettingLV = (LinearLayout)findViewById(R.id.timeCountSettingLV);
        timeCountLV = (LinearLayout)findViewById(R.id.timeCountLV);
        hourTV = (TextView)findViewById(R.id.hourTV);
        minuteTV = (TextView)findViewById(R.id.minuteTV);
        secondTV = (TextView)findViewById(R.id.secondTV);
        finishTV = (TextView)findViewById(R.id.finishTV);
        startBtn = (Button)findViewById(R.id.startBtn);
        stopBtn = (Button)findViewById(R.id.btn_stop);
        pauseBtn = (Button)findViewById(R.id.btn_pause);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(minuteTV.getText())){
                    subject.setText("????????? ??????????????????");}
                else if ( TextUtils.isEmpty(subject.getText()) || subject.getText().toString() =="????????? ?????? ??????????????????"){
                    subject.setText("????????? ?????? ??????????????????");}
                else{
                v.setVisibility(View.GONE);
                stopBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.VISIBLE);
                pauseBtn.setText("????????????");
                isRunning = true;

                timeThread = new Thread(new mockexamtimer.timeThread());
                timeThread.start();
            }}

        });
        ko.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!isRunning) {
                    hourTV.setText("01");
                    minuteTV.setText("20");
                    secondTV.setText("00");
                    subject.setText("??????");
                }
            }
        });
        ma.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!isRunning) {
                    hourTV.setText("01");
                    minuteTV.setText("40");
                    secondTV.setText("00");
                    subject.setText("??????");
                }
            }
        });
        en.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!isRunning) {
                    hourTV.setText("01");
                    minuteTV.setText("10");
                    secondTV.setText("00");
                    subject.setText("??????");
                }
            }
        });
        hi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!isRunning) {
                    hourTV.setText("00");
                    minuteTV.setText("30");
                    secondTV.setText("00");
                    subject.setText("?????????");
                }
            }
        });
        so.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!isRunning) {
                    hourTV.setText("00");
                    minuteTV.setText("30");
                    secondTV.setText("00");
                    subject.setText("??????");
                }
            }
        });
        sc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!isRunning) {
                    hourTV.setText("00");
                    minuteTV.setText("30");
                    secondTV.setText("00");
                    subject.setText("??????");
                }
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = !isRunning;
                if (isRunning) {
                    pauseBtn.setText("????????????");
                } else {
                    pauseBtn.setText("??????");
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                startBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.GONE);
                timeThread.interrupt();
                isRunning = Boolean.FALSE;
                subject.setText("");
            }
        });

    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message m) {
            int sec = (m.arg1 / 100) % 60;
            int hour = (m.arg1 / 100) / 3600;
            int min = (m.arg1 / 100) / 60 % 60;

            if(min+sec+hour == 0){
                hourTV.setText("00");
                minuteTV.setText("00");
                secondTV.setText("00");
                timeThread.interrupt();
            } else {
                hourTV.setText(String.valueOf(hour));
                minuteTV.setText(String.valueOf(min));
                secondTV.setText(String.valueOf(sec));

            }

            }
        };

    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = Integer.parseInt(hourTV.getText().toString())*360000 + Integer.parseInt(minuteTV.getText().toString())*6000 +Integer.parseInt(secondTV.getText().toString())*100;

            while (true) {
                while (isRunning) { //??????????????? ????????? ??????

                    Message msg = new Message();
                    msg.arg1 = i--;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(9);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                hourTV.setText("00");
                                minuteTV.setText("00");
                                secondTV.setText("00");

                            }
                        });
                        return; // ???????????? ?????? ?????? return
                    }
                }
            }
        }
    }
}
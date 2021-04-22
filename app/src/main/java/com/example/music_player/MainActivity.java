package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static int dur;
    private Button play;
    private Button replay;
    private TextView ltimes;
    private TextView rtimes;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=findViewById(R.id.button2);
        replay=findViewById(R.id.button);
        ltimes=findViewById(R.id.textView);
        rtimes=findViewById(R.id.textView2);
        seekBar=findViewById(R.id.seekBar);
        MediaPlayer mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://jeremy.org/music/2020/Lockdown.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Handler handler=new Handler();
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(mCurrentPosition);
                    if(String.valueOf(mCurrentPosition).length()==4)
                    {
                        String ltime=String.valueOf(mCurrentPosition).substring(0,1);
                        ltimes.setText("0:0"+ltime);
                    }
                    if(String.valueOf(mCurrentPosition).length()>4) {
                        if (mCurrentPosition >= 60000) {
                            int min = mCurrentPosition / 60000;
                            int sec = mCurrentPosition % 60000;
                            if (String.valueOf(sec).length() == 4) {
                                String ltime = String.valueOf(sec).substring(0, 1);
                                String ltime1 = String.valueOf(min);
                                ltimes.setText(ltime1 + ":0" + ltime);
                            }
                            if (String.valueOf(sec).length() == 5) {
                                String ltime1 = String.valueOf(min);
                                String ltime = String.valueOf(sec).substring(0, 2);
                                ltimes.setText(ltime1 + ":" + ltime);
                            }
                            if (String.valueOf(sec).length() < 4) {
                                String ltime1 = String.valueOf(min);
                                ltimes.setText(ltime1 + ":00");
                            }
                        } else {
                            String ltime = String.valueOf(mCurrentPosition).substring(0, 2);
                            ltimes.setText("0:" + ltime);
                        }
                    }
                    int remainpos=dur-mediaPlayer.getCurrentPosition();
                    if(String.valueOf(remainpos).length()==4)
                    {
                        String rtime=String.valueOf(remainpos).substring(0,1);
                        rtimes.setText("0:0"+rtime);
                    }
                    if(String.valueOf(remainpos).length()>4) {
                        if (remainpos >= 60000) {
                            int min1 = remainpos / 60000;
                            int sec1 = remainpos % 60000;
                            if (String.valueOf(sec1).length() == 4) {
                                String rtime = String.valueOf(sec1).substring(0, 1);
                                String rtime1 = String.valueOf(min1);
                                rtimes.setText(rtime1 + ":0" + rtime);
                            }
                            if (String.valueOf(sec1).length() == 5) {
                                String rtime = String.valueOf(sec1).substring(0, 2);
                                String rtime1 = String.valueOf(min1);
                                rtimes.setText(rtime1 + ":" + rtime);
                            }
                            if (String.valueOf(sec1).length() < 4) {
                                String ritme = String.valueOf(min1);
                                rtimes.setText(ritme + ":00");
                            }
                        } else {
                            String rtime = String.valueOf(remainpos).substring(0, 2);
                            rtimes.setText("0:" + rtime);
                        }
                    }
                }
                handler.postDelayed(this, 1000);
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Toast.makeText(MainActivity.this, "Ready to play", Toast.LENGTH_SHORT).show();
                seekBar.setMax(mediaPlayer.getDuration());
                dur=mediaPlayer.getDuration();
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(fromUser) {
                            mediaPlayer.seekTo(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        });
        mediaPlayer.prepareAsync();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(play.getText().toString().equals("Play"))
                {
                    mediaPlayer.start();
                    play.setText("Pause");
                }
                else
                {
                    mediaPlayer.pause();
                    play.setText("Play");
                }
            }
        });
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });
    }
}
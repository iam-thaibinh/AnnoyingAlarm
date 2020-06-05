package com.example.annoyingalarm.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.annoyingalarm.R;

public class AlarmScreenActivity extends AppCompatActivity {
    private Ringtone ringtone;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_alarm_screen);
        String name = getIntent().getStringExtra(AlarmManagerHelper.NAME);
        int timeHour = getIntent().getIntExtra(AlarmManagerHelper.TIME_HOUR, 0);
        int timeMinute = getIntent().getIntExtra(AlarmManagerHelper.TIME_MINUTE, 0);
        int volumn = getIntent().getIntExtra(AlarmManagerHelper.VOL, 7);
        String tone = getIntent().getStringExtra(AlarmManagerHelper.TONE);

        audioManager = (AudioManager) getApplication().getSystemService(AUDIO_SERVICE);

        TextView tvName =  findViewById(R.id.tvName);
        tvName.setText(name);

        TextView tvTime =  findViewById(R.id.tvTime);
        tvTime.setText(String.format("%02d : %02d", timeHour, timeMinute));

        Button dismissBtn = findViewById(R.id.alarm_screen_dismiss);
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //ringtone.stop();
                mediaPlayer.stop();
                finish();
            }
        });

        try {
            if (tone != null && !tone.equals("")) {
                Uri toneUri = Uri.parse(tone);
                if (toneUri != null) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volumn,AudioManager.FLAG_PLAY_SOUND);
                    //ringtone = RingtoneManager.getRingtone(this,toneUri);
                    //ringtone.play();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),toneUri);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    Toast.makeText(this,"WAKE UPPP",Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}
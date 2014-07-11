package com.office.officegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class MyActivity extends Activity implements View.OnClickListener {

    SoundPool soundPool;
    HashMap<Integer, Integer> soundPoolMap;
    int soundID = 1;

    Button chooseButton;
    Button exitButton;
    Button voiceButton;


    private static long back_pressed;

    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        }
        else
            Toast.makeText(getBaseContext(), "press again to exit",
                Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(soundID, soundPool.load(this, R.raw.piano_test, 1));

        voiceButton = (Button) findViewById(R.id.voiceButton);
        chooseButton = (Button) findViewById(R.id.chooseButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        voiceButton.setOnClickListener(this);
        chooseButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }


    public void onClick(View first) {

            AlertDialog.Builder alertExitDialog = new AlertDialog.Builder(MyActivity.this);
        TextView myMessage = new TextView(this);
        myMessage.setText("\nAre you sure?");
        myMessage.setTextSize(20);
        myMessage.setGravity(Gravity.CENTER_HORIZONTAL);


        alertExitDialog.setTitle("Exit");
        alertExitDialog.setView(myMessage);
        alertExitDialog.setPositiveButton("Yea!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

        alertExitDialog.setNegativeButton("No!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            switch (first.getId()) {
                case R.id.chooseButton:
                    Intent intent = new Intent(MyActivity.this, ChooseGameMenu.class);
                    startActivity(intent);
                    break;

                case R.id.voiceButton:
                    if (voiceButton.getText().equals("off")) {
                        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        float leftVolume = curVolume/maxVolume;
                        float rightVolume = curVolume/maxVolume;
                        int priority = 1;
                        int loop = -1; // -1 - cycle, 0 - no cycle
                        float normal_playback_rate = 1f; // 2f = 2x score, 0,5f = 0.5x score
                        soundPool.play(soundID, leftVolume, rightVolume, priority, loop, normal_playback_rate);
                        voiceButton.setText("on");
                        Toast.makeText(MyActivity.this,
                                "sound On",
                                Toast.LENGTH_LONG).show();

                    }

                    else  {
                        soundPool.pause(soundID);
                        voiceButton.setText("off");
                        Toast.makeText(MyActivity.this,
                                "sound Off",
                                Toast.LENGTH_LONG).show();

                    }
                    break;

                case R.id.exitButton:
                    alertExitDialog.show();
                    return;

                default:
                    throw new RuntimeException("error: ");

            }
        }


}

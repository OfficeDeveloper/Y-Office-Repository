package com.office.officegame;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * @author Gavlovich Maksim (reverff@gmail.com)
 * @author Yakubenko Andrii (ayakubenko92@gmail.com)
 * 2014(c)
 */

public class Main extends Activity implements View.OnClickListener {

    private MediaPlayer player;

    private AdView mAdView;

    private String helloUserAlertTitle = "Hello friend!"; //title
    private String aboutUsAlertTextForButton = "This game is created by three students from Ukraine. Compete with your friends in the reaction and do not be bored :)";
    public static boolean soundOn = true;
    private SoundPool sPool;
    private int popTileTouchSound;          //sound pop on touch tile
    private Button  arcadeButton, infoButton, soundButton, timeSprintButton, timeAttackButton;
    private static long back_pressed;

    public void onBackPressed() {        //exit when pressed double 'back' in main menu
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
            Toast.makeText(getBaseContext(), "press again to exit",
                Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdListener(new ToastAdListener(this));
        mAdView.loadAd(new AdRequest.Builder().build());

        sPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        popTileTouchSound = sPool.load(this, R.raw.poptile, 1);

        arcadeButton = (Button) findViewById(R.id.arcadeButton);
        timeSprintButton = (Button) findViewById(R.id.timeSprintButton);
        timeAttackButton = (Button) findViewById(R.id.timeAttackButton);
        infoButton = (Button) findViewById(R.id.infoButton);
        soundButton = (Button) findViewById(R.id.soundButton);

        arcadeButton.setOnClickListener(this);
        timeSprintButton.setOnClickListener(this);
        timeAttackButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);
        soundButton.setOnClickListener(this);

        int background = soundOn ? R.drawable.button_voice : R.drawable.button_no_voice;
        soundButton.setBackgroundResource(background);

        player = MediaPlayer.create(this, R.raw.main_back);
        player.setLooping(true);
        if(soundOn) player.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(player.isPlaying()) player.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(player.isPlaying()) player.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(soundOn) player.start();
    }

    public void showInfo() {
        AlertDialog.Builder aboutGameAlert = new AlertDialog.Builder(Main.this);
        aboutGameAlert.setTitle(helloUserAlertTitle)
                .setMessage(aboutUsAlertTextForButton)
                .setCancelable(true)
                .setNegativeButton("Ok!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        }
                );
        AlertDialog aboutAlert = aboutGameAlert.create();
        aboutAlert.show();
    }

    public void playSound() {
        if (soundOn) {
            sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
        }
    }

    public void onClick(View first) {
            switch (first.getId()) {
                case R.id.soundButton:
                    soundOn = !soundOn;
                    playSound();
                    int background = soundOn ? R.drawable.button_voice : R.drawable.button_no_voice;
                    soundButton.setBackgroundResource(background);
                    if(soundOn) {
                        player.start();
                    } else {
                        player.pause();
                    }
                    break;

                case R.id.arcadeButton:
                    playSound();
                    Intent beginFirstGame = new Intent(Main.this, Firstgame.class);
                    startActivity(beginFirstGame);
                    break;

                case R.id.timeSprintButton:
                    playSound();
                    Intent beginSecondGame = new Intent(Main.this, SecondGame.class);
                    startActivity(beginSecondGame);
                    break;

                case R.id.timeAttackButton:
                    playSound();
                    Intent beginThirdGame = new Intent(Main.this, ThirdGame.class);
                    startActivity(beginThirdGame);
                    break;

                case R.id.infoButton:
                    playSound();
                    Intent showInfo = new Intent(Main.this, Info.class);
                    startActivity(showInfo);
                    break;
                default:
                    break;
            }
    }
}


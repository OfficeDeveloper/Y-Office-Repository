package com.office.officegame;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.achievement.Achievement;

/**
 * @author Gavlovich Maksim (reverff@gmail.com)
 * @author Yakubenko Andrii (ayakubenko92@gmail.com)
 * 2014(c)
 */

public class Main extends BaseGameActivity implements View.OnClickListener {

    private MediaPlayer player;

    private AdView mAdView;
    public static GoogleApiClient googleApiClient;

    public static boolean soundOn = true;
    private SoundPool sPool;
    private int popTileTouchSound;          //sound pop on touch tile
    private ImageButton  arcadeButton, timeSprintButton, timeAttackButton;
    private static long back_pressed;
    private Button playServicesButton, infoButton, soundButton;

    private Game main;
    private int signed;

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
        setRequestedClients(BaseGameActivity.CLIENT_GAMES);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        main = new Game(this);
        main.connectDb();
        signed = main.getSignStatus();

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdListener(new ToastAdListener(this));
        mAdView.loadAd(new AdRequest.Builder().build());

        googleApiClient = getApiClient();

        sPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        popTileTouchSound = sPool.load(this, R.raw.poptile, 1);

        arcadeButton = (ImageButton) findViewById(R.id.arcadeButton);
        timeSprintButton = (ImageButton) findViewById(R.id.timeSprintButton);
        timeAttackButton = (ImageButton) findViewById(R.id.timeAttackButton);
        infoButton = (Button) findViewById(R.id.infoButton);
        soundButton = (Button) findViewById(R.id.soundButton);
        playServicesButton = (Button) findViewById(R.id.playServicesButton);

        arcadeButton.setOnClickListener(this);
        timeSprintButton.setOnClickListener(this);
        timeAttackButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);
        soundButton.setOnClickListener(this);
        playServicesButton.setOnClickListener(this);

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

                case R.id.playServicesButton:
                    playSound();
                    if (signed == 0) {
                        showPrompt();
                        break;
                    }
                    if (isSignedIn()) startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), 0);
                    break;
                default:
                    break;
            }
    }

    @Override
    public void onSignInFailed() {
        Toast.makeText(this,"Failed signing to Google Play Games", Toast.LENGTH_SHORT).show();
        main.setSignStatus(0);
        signed = 0;
    }

    @Override
    public void onSignInSucceeded() {
        main.setSignStatus(1);
        signed = 1;
    }

    private void showPrompt() {
        AlertDialog.Builder prompt = new AlertDialog.Builder(this);
        prompt.setTitle("Google Play Services")
                .setMessage("Sign In Google Play Services to see achievments?")
                .setCancelable(false)
                .setNegativeButton("No, thanks",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                )
                .setPositiveButton("Sign In",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                beginUserInitiatedSignIn();
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = prompt.create();
        alert.show();
    }
}


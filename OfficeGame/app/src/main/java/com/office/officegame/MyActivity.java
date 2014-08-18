package com.office.officegame;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;




public class MyActivity extends Activity implements View.OnClickListener {
    private SoundPool sPool;
    private int popTileTouchSound;          //sound pop on touch tile
    private Button  chooseButton, exitButton, settingsButton, muteButton;
    private static long back_pressed;
    private boolean boolMusicCheckVariable = true;

    public void onBackPressed() {        //exit when pressed double 'back' in main menu

        if (back_pressed + 2000 > System.currentTimeMillis()) {
            stopService(new Intent(this, MyService.class));     //stop background music
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
        setContentView(R.layout.activity_my);

        sPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        popTileTouchSound = sPool.load(this, R.raw.poptile, 1);

        muteButton = (Button) findViewById(R.id.muteButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);
        chooseButton = (Button) findViewById(R.id.chooseButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        muteButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        chooseButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);


        startService(new Intent(this, MyService.class));  // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    }

    public void onClick(View first) {


            AlertDialog.Builder alertExitDialog = new AlertDialog.Builder(MyActivity.this);
        TextView myMessage = new TextView(this);
        myMessage.setText("\nAre you sure?");
        myMessage.setTextSize(16);
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
                    Intent goToChooseGameMenu = new Intent(MyActivity.this, ChooseGameMenu.class);
                    sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                    startActivity(goToChooseGameMenu);
                    break;

                case R.id.settingsButton:
                    Intent goToSettings = new Intent(MyActivity.this, SettingsMenu.class);
                    sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                    startActivity(goToSettings);
                    break;

                case R.id.exitButton:
                    sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                    alertExitDialog.show();
                    return;

                case R.id.muteButton:
                    if (boolMusicCheckVariable == false) {
                        startService(new Intent(this, MyService.class));
                        boolMusicCheckVariable = true;
                    }
                    else {
                        stopService(new Intent(this, MyService.class));
                        boolMusicCheckVariable = false;
                    }
                    break;
                default:
                    throw new RuntimeException("error: ");

            }
        }


}

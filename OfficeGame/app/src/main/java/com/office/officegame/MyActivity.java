package com.office.officegame;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity implements View.OnClickListener {

    private SoundPool sPool;
    private int popTileTouchSound;          //sound pop on touch tile
    private Button  chooseButton, exitButton, settingsButton, muteButton, muteTileButton;
    private static long back_pressed;
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

        muteTileButton = (Button) findViewById(R.id.tileMuteButton);
        muteButton = (Button) findViewById(R.id.muteSoundButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);
        chooseButton = (Button) findViewById(R.id.chooseButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        muteTileButton.setOnClickListener(this);
        muteButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        chooseButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        if (MyService.backgroundMusicKey == 1) {
            startService(new Intent(this, MyService.class));  // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            MyService.backgroundMusicKey = 2;
        }
    }

   

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            stopService(new Intent(this, MyService.class));
        }
        return true;
    }

    /*@Override
    public void onPause() {
        super.onPause();
        stopService(new Intent(this, MyService.class));
        return;
    }
*/
    @Override
    public void onStop() {
        super.onStop();
        stopService(new Intent(this, MyService.class));
        return;
    }
    @Override
    public void onRestart() {
        super.onRestart();
            startService(new Intent(this, MyService.class));
            return;
    }
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_HOME) {
           stopService(new Intent(this, MyService.class));
        }
        stopService(new Intent(this, MyService.class));
        return false;
        }
 */
    public void onClick(View first) {
        AlertDialog.Builder alertExitDialog = new AlertDialog.Builder(MyActivity.this);
            TextView myMessage = new TextView(this);
            myMessage.setText("\nAre you sure?");
            myMessage.setTextSize(16);
            myMessage.setGravity(Gravity.CENTER_HORIZONTAL);

            alertExitDialog.setTitle("Quit");
            alertExitDialog.setView(myMessage);
            alertExitDialog.setPositiveButton("Yea!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                    MyService.backgroundMusicKey = 1;
                }
            });

        alertExitDialog.setNegativeButton("No!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
        });

            switch (first.getId()) {
                case R.id.tileMuteButton:
                    if (MyService.boolSoundTileCheck == false) {
                        MyService.boolSoundTileCheck = true;
                    }
                    else {
                        MyService.boolSoundTileCheck = false;
                    }
                    break;

                case R.id.chooseButton:
                    if (MyService.boolSoundTileCheck == true) {
                        Intent goToChooseGameMenu = new Intent(MyActivity.this, ChooseGameMenu.class);
                        sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                        startActivity(goToChooseGameMenu);
                    }
                    else {
                        Intent goToChooseGameMenu = new Intent(MyActivity.this, ChooseGameMenu.class);
                        startActivity(goToChooseGameMenu);
                    }
                    break;

                case R.id.settingsButton:
                    if (MyService.boolSoundTileCheck == true) {
                        Intent goToSettings = new Intent(MyActivity.this, SettingsMenu.class);
                        sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                        startActivity(goToSettings);
                    }
                    else {
                        Intent goToSettings = new Intent(MyActivity.this, SettingsMenu.class);
                        startActivity(goToSettings);
                    }
                    break;

                case R.id.exitButton:
                    if (MyService.boolSoundTileCheck == true) {
                        sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                        alertExitDialog.show();
                        stopService(new Intent(this, MyService.class));
                    }
                    else {
                        alertExitDialog.show();
                        stopService(new Intent(this, MyService.class));
                    }
                    return;

                case R.id.muteSoundButton:
                    if (MyService.boolMusicCheck == false) {
                        startService(new Intent(this, MyService.class));
                        MyService.boolMusicCheck = true;
                    }
                    else {
                        stopService(new Intent(this, MyService.class));
                        MyService.boolMusicCheck = false;
                    }
                    break;
                default:
                    throw new RuntimeException("error: ");

            }
    }
}


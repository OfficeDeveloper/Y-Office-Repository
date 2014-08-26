package com.office.officegame;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity implements View.OnClickListener {

    private String helloUserAlertTitle = "Hello friend!"; //title
    private String aboutUsAlertTextForButton = "This game is created by three students from the Ukraine. Compete with her friends in the reaction and do not be bored :)";
    public static boolean boolSoundTileCheck = true;
    private SoundPool sPool;
    private int popTileTouchSound;          //sound pop on touch tile
    private Button  chooseButton, exitButton, muteTileButton, aboutGameAndDevButton;
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
        setContentView(R.layout.activity_my);

        sPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        popTileTouchSound = sPool.load(this, R.raw.poptile, 1);

        aboutGameAndDevButton = (Button) findViewById(R.id.aboutGameAndDevButton);
        muteTileButton = (Button) findViewById(R.id.tileMuteButton);
        chooseButton = (Button) findViewById(R.id.chooseButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        aboutGameAndDevButton.setOnClickListener(this);
        muteTileButton.setOnClickListener(this);
        chooseButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    public void onShow() {
        AlertDialog.Builder aboutGameAlert = new AlertDialog.Builder(MyActivity.this);
        aboutGameAlert.setTitle(helloUserAlertTitle)
                .setMessage(aboutUsAlertTextForButton)
                .setIcon(R.drawable.ic_launcher)
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
                }
            });
            alertExitDialog.setNegativeButton("No!", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
        });

            switch (first.getId()) {
                case R.id.tileMuteButton:
                    if (this.boolSoundTileCheck == false) {
                        this.boolSoundTileCheck = true;
                    }
                    else {
                        this.boolSoundTileCheck = false;
                    }
                    break;

                case R.id.chooseButton:
                    if (this.boolSoundTileCheck == true) {
                        Intent goToChooseGameMenu = new Intent(MyActivity.this, ChooseGameMenu.class);
                        sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                        startActivity(goToChooseGameMenu);
                    }
                    else {
                        Intent goToChooseGameMenu = new Intent(MyActivity.this, ChooseGameMenu.class);
                        startActivity(goToChooseGameMenu);
                    }
                    break;
                case R.id.aboutGameAndDevButton:
                    if (this.boolSoundTileCheck == true) {
                        onShow();
                        sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                    }
                    else {
                        onShow();
                    }
                    break;
                case R.id.exitButton:
                    if (this.boolSoundTileCheck == true) {
                        sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                        alertExitDialog.show();
                    }
                    else {
                        alertExitDialog.show();
                    }
                    return;
                default:
                    throw new RuntimeException("error: ");

            }
    }
}


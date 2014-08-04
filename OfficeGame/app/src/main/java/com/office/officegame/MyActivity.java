package com.office.officegame;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;




public class MyActivity extends Activity implements View.OnClickListener {


    MediaPlayer player;
    Button chooseButton;
    Button exitButton;
    Button voiceButton;


    private static long back_pressed;

    public void onBackPressed() {                                                                   //exit when pressed double 'back' in main menu


        if (back_pressed + 2000 > System.currentTimeMillis()) {

            super.onBackPressed();
            player.pause();
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

    protected void onStop() {                   //stop player when pressed HOME BUTTON
    super.onStop();
    if(player.isPlaying()) {                //звук вырубается с нажатия ДОМОЙ но и пре переходе на другую активити тоже отрубается
        player.pause();
   }
    else {super.onStart();
            player.start();}
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        player = MediaPlayer.create(MyActivity.this, R.raw.guitarsound);
        player.start();

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
        myMessage.setTextSize(16);
        myMessage.setGravity(Gravity.CENTER_HORIZONTAL);


        alertExitDialog.setTitle("Exit");
        alertExitDialog.setView(myMessage);
        alertExitDialog.setPositiveButton("Yea!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    player.stop();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            });

        alertExitDialog.setNegativeButton("No!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    player.start();


                }
            });

            switch (first.getId()) {
                case R.id.chooseButton:
                    Intent intent = new Intent(MyActivity.this, ChooseGameMenu.class);
                    startActivity(intent);
                    break;

                case R.id.voiceButton:
                    if (player.isPlaying()) {
                        voiceButton.setText("off");
                        player.pause();

                    }
                    else  {
                        player.start();
                        voiceButton.setText("on");

                    }
                    break;

                case R.id.exitButton:
                    alertExitDialog.show();
                    player.pause();
                    return;

                default:
                    throw new RuntimeException("error: ");

            }
        }


}

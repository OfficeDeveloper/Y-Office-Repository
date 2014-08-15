package com.office.officegame;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;




public class MyActivity extends Activity implements View.OnClickListener {


    private  Button   chooseButton;
    private  Button   exitButton;



    private static long back_pressed;

    public void onBackPressed() {                                                                   //exit when pressed double 'back' in main menu


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        chooseButton = (Button) findViewById(R.id.chooseButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        chooseButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }
        protected void onResume() {
            super.onResume();
            Music.play(this, R.raw.main);
        }

        protected void onPause() {
            super.onPause();
            Music.stop(this);
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
                    Intent intent = new Intent(MyActivity.this, ChooseGameMenu.class);
                    startActivity(intent);
                    break;

                case R.id.exitButton:
                    alertExitDialog.show();
                    return;

                default:
                    throw new RuntimeException("error: ");

            }
        }


}

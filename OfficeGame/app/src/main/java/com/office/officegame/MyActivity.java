package com.office.officegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MyActivity extends Activity implements View.OnClickListener {

    Button chooseButton;
    Button exitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        chooseButton = (Button) findViewById(R.id.chooseButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        chooseButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }


    public void onClick(View first) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyActivity.this);
            alertDialog.setTitle("Exit");
            alertDialog.setMessage("Are you sure ?");
            alertDialog.setPositiveButton("Yea!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            alertDialog.setNegativeButton("No!", new DialogInterface.OnClickListener() {
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
                    alertDialog.show();
                    return;

                default:
                    throw new RuntimeException("error: ");

            }
        }
    }

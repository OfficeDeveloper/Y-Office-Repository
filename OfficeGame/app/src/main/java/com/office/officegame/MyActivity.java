package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

        switch (first.getId()) {
            case R.id.chooseButton:
                Intent intent = new Intent(this, ChooseGameMenu.class);
                startActivity(intent);
                break;
            //case R.id.exitButton:
                //Intent exit = new Intent(this, ChooseGameMenu.class);
                //startActivity(exit);
               // break;



            default:
                throw new RuntimeException("error: ");


        }
    }
}

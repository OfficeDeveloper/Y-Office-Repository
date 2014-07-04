package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Андрей on 04.07.2014.
 */
public class FourthGame extends Activity implements View.OnClickListener, View.OnTouchListener {

    Button backToMainMenuFourthGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_game);

        backToMainMenuFourthGame = (Button) findViewById(R.id.backToMainMenuFourthGame);

        backToMainMenuFourthGame.setOnClickListener(this);

    }


    @Override
    public void onClick(View fourthGame) {

        switch (fourthGame.getId()) {

            case R.id.backToMainMenuFourthGame:
                Intent goToChooseMenu = new Intent(FourthGame.this, ChooseGameMenu.class);
                startActivity(goToChooseMenu);
                break;

            default:
                throw new RuntimeException("error: ");


        }
    }

    @Override
    public boolean onTouch(View fourthGame, MotionEvent newEvent) {

        if (newEvent.getAction()==MotionEvent.ACTION_DOWN) {

        }

        return false;
        }



    }


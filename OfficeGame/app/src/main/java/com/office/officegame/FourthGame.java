package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Андрей on 04.07.2014.
 */
public class FourthGame extends Activity implements View.OnClickListener, View.OnTouchListener {


    Button startFourthGame;
    Button backToMainMenuFourthGame;
    TextView viewArray[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_game);

        viewArray = new TextView[16];

        viewArray[0] = (TextView)findViewById(R.id.viewFourthGame1);
        viewArray[0].setOnTouchListener(this);
        viewArray[1] = (TextView)findViewById(R.id.viewFourthGame2);
        viewArray[1].setOnTouchListener(this);
        viewArray[2] = (TextView)findViewById(R.id.viewFourthGame3);
        viewArray[2].setOnTouchListener(this);
        viewArray[3] = (TextView)findViewById(R.id.viewFourthGame4);
        viewArray[3].setOnTouchListener(this);
        viewArray[4] = (TextView)findViewById(R.id.viewFourthGame5);
        viewArray[4].setOnTouchListener(this);
        viewArray[5] = (TextView)findViewById(R.id.viewFourthGame6);
        viewArray[5].setOnTouchListener(this);
        viewArray[6] = (TextView)findViewById(R.id.viewFourthGame7);
        viewArray[6].setOnTouchListener(this);
        viewArray[7] = (TextView)findViewById(R.id.viewFourthGame8);
        viewArray[7].setOnTouchListener(this);
        viewArray[8] = (TextView)findViewById(R.id.viewFourthGame9);
        viewArray[8].setOnTouchListener(this);
        viewArray[9] = (TextView)findViewById(R.id.viewFourthGame10);
        viewArray[9].setOnTouchListener(this);
        viewArray[10] = (TextView)findViewById(R.id.viewFourthGame11);
        viewArray[10].setOnTouchListener(this);
        viewArray[11] = (TextView)findViewById(R.id.viewFourthGame12);
        viewArray[11].setOnTouchListener(this);
        viewArray[12] = (TextView)findViewById(R.id.viewFourthGame13);
        viewArray[12].setOnTouchListener(this);
        viewArray[13] = (TextView)findViewById(R.id.viewFourthGame14);
        viewArray[13].setOnTouchListener(this);
        viewArray[14] = (TextView)findViewById(R.id.viewFourthGame15);
        viewArray[14].setOnTouchListener(this);
        viewArray[15] = (TextView)findViewById(R.id.viewFourthGame16);
        viewArray[15].setOnTouchListener(this);




        startFourthGame = (Button) findViewById(R.id.startFourthGame);
        backToMainMenuFourthGame = (Button) findViewById(R.id.backToMainMenuFourthGame);

        backToMainMenuFourthGame.setOnClickListener(this);
        startFourthGame.setOnClickListener(this);



    }


    @Override
    public void onClick(View fourthGame) {

        switch (fourthGame.getId()) {

            case R.id.backToMainMenuFourthGame:
                Intent goToChooseMenu = new Intent(FourthGame.this, ChooseGameMenu.class);
                startActivity(goToChooseMenu);
                break;

            case R.id.startFourthGame:

                for (int i = 0; i < 16; i++) {
                    viewArray[i].setText(String.valueOf((int)(11 + Math.random() *88)));

                }
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


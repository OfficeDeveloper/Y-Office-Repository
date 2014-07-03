package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Андрей on 03.07.2014 at 4:10.
 * Паскудатварь
 */
public class FirstGame extends Activity implements View.OnClickListener {

    Button backToMainMenu;
    Button startButton;
    TextView tv;
    TextView point;
    long startTime = 0;
    int random;
    int score = 0;
    TextView stile;
    boolean bool=false;
    int delay=0;
    int fouls;
    TextView tileArray[];


    Handler handler1 = new Handler();
    Runnable task1 = new Runnable() {

        @Override
        public void run() {

            point.setText(String.valueOf(score));
            tv.setText(String.valueOf(fouls));

            changeColor(tileArray);

            if ((score%30==0)&&(score>0)){
                delay=(int)(delay*0.9);
            }

            handler1.postDelayed(this, delay);

        }
    };

    public void changeColor(TextView[] tiles) {
        for (int i=0;i<16;i++){
            tiles[i].setBackgroundColor(Color.WHITE);
        }

        for (int i=0;i<5;i++) {
            int k = (int) (Math.random() * 16);
            random = (int) (Math.random() * 1);
            tiles[k].setBackgroundColor(Color.BLACK);
        }
    }

    public void upScore(TextView tiv) {
        ColorDrawable drawable = (ColorDrawable) tiv.getBackground();
        if ((drawable.getColor() == Color.BLACK)&&(bool)) {
            score = score + 1;
            point.setText(String.valueOf(score));
        }
        if ((drawable.getColor() == Color.WHITE)&&(bool)) {
            fouls = fouls - 1;
            tv.setText(String.valueOf(fouls));
            if (fouls==0) {
                handler1.removeCallbacks(task1);
                startButton.setText("start");
                bool=false;
            }
        }
    }

    public void onPause() {
        super.onPause();
        handler1.removeCallbacks(task1);
        Button b = (Button) findViewById(R.id.startButton);
        b.setText("start");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstgame);
        tv = (TextView) findViewById(R.id.timer);
        tv.setText("20");
        point = (TextView) findViewById(R.id.point);
        point.setText(String.valueOf(score));

        backToMainMenu = (Button) findViewById(R.id.backToMainMenu);
        backToMainMenu.setOnClickListener(this);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        tileArray = new TextView[16];

        tileArray[0] = (TextView) findViewById(R.id.tile1);
        tileArray[0].setOnClickListener(this);

        tileArray[1] = (TextView) findViewById(R.id.tile2);
        tileArray[1].setOnClickListener(this);

        tileArray[2] = (TextView) findViewById(R.id.tile3);
        tileArray[2].setOnClickListener(this);

        tileArray[3] = (TextView) findViewById(R.id.tile4);
        tileArray[3].setOnClickListener(this);

        tileArray[4] = (TextView) findViewById(R.id.tile5);
        tileArray[4].setOnClickListener(this);

        tileArray[5] = (TextView) findViewById(R.id.tile6);
        tileArray[5].setOnClickListener(this);

        tileArray[6] = (TextView) findViewById(R.id.tile7);
        tileArray[6].setOnClickListener(this);

        tileArray[7] = (TextView) findViewById(R.id.tile8);
        tileArray[7].setOnClickListener(this);

        tileArray[8] = (TextView) findViewById(R.id.tile9);
        tileArray[8].setOnClickListener(this);

        tileArray[9] = (TextView) findViewById(R.id.tile10);
        tileArray[9].setOnClickListener(this);

        tileArray[10] = (TextView) findViewById(R.id.tile11);
        tileArray[10].setOnClickListener(this);

        tileArray[11] = (TextView) findViewById(R.id.tile12);
        tileArray[11].setOnClickListener(this);

        tileArray[12] = (TextView) findViewById(R.id.tile13);
        tileArray[12].setOnClickListener(this);

        tileArray[13] = (TextView) findViewById(R.id.tile14);
        tileArray[13].setOnClickListener(this);

        tileArray[14] = (TextView) findViewById(R.id.tile15);
        tileArray[14].setOnClickListener(this);

        tileArray[15] = (TextView) findViewById(R.id.tile16);
        tileArray[15].setOnClickListener(this);
    }

    public void onClick(View button) {
        switch (button.getId()) {
            case R.id.backToMainMenu:
                Intent goHome = new Intent(FirstGame.this, ChooseGameMenu.class);
                startActivity(goHome);
                break;

            case R.id.startButton:
                //startButton tasks
                if (startButton.getText().equals("stop")) {
                    handler1.removeCallbacks(task1);
                    startButton.setText("start");
                    bool=false;
                } else {
                    startTime = System.currentTimeMillis();
                    handler1.postDelayed(task1, 0);
                    startButton.setText("stop");
                    score = 0;
                    bool=true;
                    delay=700;
                    fouls=20;
                }
                break;

            case R.id.tile1:
                if (bool) {
                    stile = (TextView) findViewById(R.id.tile1);
                    upScore(stile);
                }
                break;

            case R.id.tile2:
                stile = (TextView) findViewById(R.id.tile2);
                upScore(stile);
                break;

            case R.id.tile3:
                stile = (TextView) findViewById(R.id.tile3);
                upScore(stile);
                break;

            case R.id.tile4:
                stile = (TextView) findViewById(R.id.tile4);
                upScore(stile);
                break;

            case R.id.tile5:
                stile = (TextView) findViewById(R.id.tile5);
                upScore(stile);
                break;

            case R.id.tile6:
                stile = (TextView) findViewById(R.id.tile6);
                upScore(stile);
                break;

            case R.id.tile7:
                stile = (TextView) findViewById(R.id.tile7);
                upScore(stile);
                break;

            case R.id.tile8:
                stile = (TextView) findViewById(R.id.tile8);
                upScore(stile);
                break;

            case R.id.tile9:
                stile = (TextView) findViewById(R.id.tile9);
                upScore(stile);
                break;

            case R.id.tile10:
                stile = (TextView) findViewById(R.id.tile10);
                upScore(stile);
                break;

            case R.id.tile11:
                stile = (TextView) findViewById(R.id.tile11);
                upScore(stile);
                break;

            case R.id.tile12:
                stile = (TextView) findViewById(R.id.tile12);
                upScore(stile);
                break;

            case R.id.tile13:
                stile = (TextView) findViewById(R.id.tile13);
                upScore(stile);
                break;

            case R.id.tile14:
                stile = (TextView) findViewById(R.id.tile14);
                upScore(stile);
                break;

            case R.id.tile15:
                stile = (TextView) findViewById(R.id.tile15);
                upScore(stile);
                break;

            case R.id.tile16:
                stile = (TextView) findViewById(R.id.tile16);
                upScore(stile);
                break;


            default:
                throw new RuntimeException("error: ");

        }
    }
}
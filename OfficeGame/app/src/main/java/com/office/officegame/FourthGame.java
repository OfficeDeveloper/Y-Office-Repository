package com.office.officegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by Андрей on 03.07.2014 at 4:10.
 */
public class FourthGame extends Activity implements View.OnClickListener, OnTouchListener {

    Button backToMainMenuFourthGame;
    Button startFourthGame;
    TextView textViewForTimer;
    TextView pointFourthGame;
    long startTime = 0;
    int random;
    int score = 0;
    int maxArrayNumber;
    int currentArrayNumber;
    int touchArrayNumber;
    TextView stile;
    boolean bool = false;
    int delay = 0;
    int fouls;
    TextView viewFourthGameArray[];

    Handler handler1 = new Handler();
    Runnable task1 = new Runnable() {

        @Override
        public void run() {

            pointFourthGame.setText(String.valueOf(score));
            textViewForTimer.setText(String.valueOf(fouls));

            setViewNumber(viewFourthGameArray);

            //if ((score % 30 == 0) && (score > 0)) {
            delay = (int) (delay * 0.997);
            //}

            handler1.postDelayed(this, delay);

        }
    };

    public void setViewNumber(TextView[] tiles) {
        maxArrayNumber = 0;
        for (int i = 0; i < 16; i++) {
            tiles[i].setText(String.valueOf((int) (11 + Math.random() * 88)));
            String numString = tiles[i].getText().toString();
            currentArrayNumber = Integer.parseInt(numString);
            if (currentArrayNumber > maxArrayNumber) {
                maxArrayNumber = currentArrayNumber;
            }
            else;
        }
    }

    public void upScore(TextView tiv) {

        if (tiles[i].getText.toString() != "") {
            String newStringForTiles = tiles[i].getText().toString();
            touchArrayNumber = Integer.parseInt(newStringForTiles);
        }
        if (touchArrayNumber == maxArrayNumber) {
            score = score + 1;
            pointFourthGame.setText(String.valueOf(score));
            tiv.setBackgroundColor(Color.DKGRAY);
        }


        if (touchArrayNumber != maxArrayNumber) {
            fouls = fouls - 1;
            textViewForTimer.setText(String.valueOf(fouls));
            tiv.setBackgroundColor(Color.RED);
            if (fouls == 0) {
                handler1.removeCallbacks(task1);
                startFourthGame.setText("Start");
                bool = false;
                AlertDialog.Builder looseAlert = new AlertDialog.Builder(FourthGame.this);
                looseAlert.setTitle("GAME OVER")
                        .setMessage("You finished with score: " + score)
                        .setIcon(R.drawable.ic_launcher)
                        .setCancelable(false)
                        .setNegativeButton("Okay!",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }
                        );
                AlertDialog alert = looseAlert.create();
                for (int i = 0; i < 16; i++) {
                    viewFourthGameArray[i].setBackgroundColor(Color.WHITE);
                }
                alert.show();
            }
        }
    }

    public void onPause() {
        super.onPause();
        handler1.removeCallbacks(task1);
        Button b = (Button) findViewById(R.id.startFourthGame);
        b.setText("Start");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_game);
        textViewForTimer=(TextView)findViewById(R.id.timerTextView);
        textViewForTimer.setText("20");
        pointFourthGame = (TextView) findViewById(R.id.pointFourthGame);
        pointFourthGame.setText(String.valueOf(score));

        backToMainMenuFourthGame = (Button) findViewById(R.id.backToMainMenuFourthGame);
        backToMainMenuFourthGame.setOnClickListener(this);
        startFourthGame = (Button) findViewById(R.id.startFourthGame);
        startFourthGame.setOnClickListener(this);

        viewFourthGameArray = new TextView[16];

        viewFourthGameArray[0] = (TextView) findViewById(R.id.viewFourthGame1);
        viewFourthGameArray[0].setOnTouchListener(this);

        viewFourthGameArray[1] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[1].setOnTouchListener(this);

        viewFourthGameArray[2] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[2].setOnTouchListener(this);

        viewFourthGameArray[3] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[3].setOnTouchListener(this);

        viewFourthGameArray[4] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[4].setOnTouchListener(this);

        viewFourthGameArray[5] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[5].setOnTouchListener(this);

        viewFourthGameArray[6] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[6].setOnTouchListener(this);

        viewFourthGameArray[7] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[7].setOnTouchListener(this);

        viewFourthGameArray[8] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[8].setOnTouchListener(this);

        viewFourthGameArray[9] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[9].setOnTouchListener(this);

        viewFourthGameArray[10] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[10].setOnTouchListener(this);

        viewFourthGameArray[11] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[11].setOnTouchListener(this);

        viewFourthGameArray[12] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[12].setOnTouchListener(this);

        viewFourthGameArray[13] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[13].setOnTouchListener(this);

        viewFourthGameArray[14] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[14].setOnTouchListener(this);

        viewFourthGameArray[15] = (TextView) findViewById(R.id.viewFourthGame2);
        viewFourthGameArray[15].setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) switch (v.getId()) {

            case R.id.viewFourthGame1:
                stile = (TextView) findViewById(R.id.viewFourthGame1);
                upScore(stile);
                break;

            case R.id.viewFourthGame2:
                stile = (TextView) findViewById(R.id.viewFourthGame2);
                upScore(stile);
                break;

            case R.id.viewFourthGame3:
                stile = (TextView) findViewById(R.id.viewFourthGame3);
                upScore(stile);
                break;

            case R.id.viewFourthGame4:
                stile = (TextView) findViewById(R.id.viewFourthGame4);
                upScore(stile);
                break;

            case R.id.viewFourthGame5:
                stile = (TextView) findViewById(R.id.viewFourthGame5);
                upScore(stile);
                break;

            case R.id.viewFourthGame6:
                stile = (TextView) findViewById(R.id.viewFourthGame6);
                upScore(stile);
                break;

            case R.id.viewFourthGame7:
                stile = (TextView) findViewById(R.id.viewFourthGame7);
                upScore(stile);
                break;

            case R.id.viewFourthGame8:
                stile = (TextView) findViewById(R.id.viewFourthGame8);
                upScore(stile);
                break;

            case R.id.viewFourthGame9:
                stile = (TextView) findViewById(R.id.viewFourthGame9);
                upScore(stile);
                break;

            case R.id.viewFourthGame10:
                stile = (TextView) findViewById(R.id.viewFourthGame10);
                upScore(stile);
                break;

            case R.id.viewFourthGame11:
                stile = (TextView) findViewById(R.id.viewFourthGame11);
                upScore(stile);
                break;

            case R.id.viewFourthGame12:
                stile = (TextView) findViewById(R.id.viewFourthGame12);
                upScore(stile);
                break;

            case R.id.viewFourthGame13:
                stile = (TextView) findViewById(R.id.viewFourthGame13);
                upScore(stile);
                break;

            case R.id.viewFourthGame14:
                stile = (TextView) findViewById(R.id.viewFourthGame14);
                upScore(stile);
                break;

            case R.id.viewFourthGame15:
                stile = (TextView) findViewById(R.id.viewFourthGame15);
                upScore(stile);
                break;

            case R.id.viewFourthGame16:
                stile = (TextView) findViewById(R.id.viewFourthGame16);
                upScore(stile);
                break;


            default:
                throw new RuntimeException("error: ");

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToMainMenuFourthGame:
                Intent goToChooseMenu = new Intent (FourthGame.this, ChooseGameMenu.class);
                startActivity(goToChooseMenu);
                break;

            case R.id.startFourthGame:
                //startButton tasks


                if (startFourthGame.getText().equals("Stop")) {
                    handler1.removeCallbacks(task1);
                    startFourthGame.setText("Start");
                    bool = false;
                    AlertDialog.Builder looseAlert = new AlertDialog.Builder(FourthGame.this);
                    looseAlert.setTitle("GAME OVER")
                            .setMessage("You finished with score: " + score)
                            .setIcon(R.drawable.ic_launcher)
                            .setCancelable(false)
                            .setNegativeButton("Okay!",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    }
                            );
                    AlertDialog alert = looseAlert.create();
                    for (int i = 0; i < 16; i++) {
                        viewFourthGameArray[i].setBackgroundColor(Color.WHITE);
                    }
                    alert.show();
                } else {
                    startTime = System.currentTimeMillis();
                    handler1.postDelayed(task1, 0);
                    startFourthGame.setText("Stop");
                    score = 0;
                    bool = true;
                    delay = 700;
                    fouls = 20;
                }
                break;

            default:
                throw new RuntimeException("error: ");

        }

    }
}
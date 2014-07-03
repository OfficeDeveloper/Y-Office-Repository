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
   TextView tile;
   int Min = 0;
   int Max = 1;
   int random;
   int score = 0;
   TextView stile;

    Handler handler1 = new Handler();
    Runnable task1 = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            point.setText(String.valueOf(score));
            tv.setText(String.format("%d:%02d", minutes, seconds));

            tile = (TextView)findViewById(R.id.tile1); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile2); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile3); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile4); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile5); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile6); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile7); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile8); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile9); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile10); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile11); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile12); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile13); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile14); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile15); changeColor(tile);
            tile = (TextView)findViewById(R.id.tile16); changeColor(tile);

            handler1.postDelayed(this, 333);
        }
    };

    public void changeColor(TextView tv){
        random = Min + (int)(Math.random() * ((Max - Min) + 1));
        if(random==Min){
            tv.setBackgroundColor(Color.BLACK);
        }
        else {
            tv.setBackgroundColor(Color.WHITE);
        }
    }

    public void upScore(TextView tv){
        ColorDrawable drawable = (ColorDrawable) tv.getBackground();
        if (drawable.getColor()==Color.BLACK) {
            score = score + 1;
            point.setText(String.valueOf(score));
        }
    }

    public void onPause() {
        super.onPause();
        handler1.removeCallbacks(task1);
        Button b = (Button)findViewById(R.id.startButton);
        b.setText("start");
    }

      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstgame);
        tv = (TextView) findViewById(R.id.timer);
        tv.setText("0:00");
        point = (TextView) findViewById(R.id.point);
        point.setText(String.valueOf(score));


        backToMainMenu = (Button) findViewById(R.id.backToMainMenu);
        backToMainMenu.setOnClickListener(this);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

          tile = (TextView)findViewById(R.id.tile1); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile2); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile3); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile4); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile5); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile6); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile7); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile8); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile9); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile10); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile11); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile12); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile13); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile14); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile15); tile.setOnClickListener(this);
          tile = (TextView)findViewById(R.id.tile16); tile.setOnClickListener(this);
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
                } else {
                    startTime = System.currentTimeMillis();
                    handler1.postDelayed(task1, 0);
                    startButton.setText("stop");
                    score = 0;
                }
                break;

            case R.id.tile1:
                stile = (TextView)findViewById(R.id.tile1);
                upScore(stile);
                break;

            case R.id.tile2:
                stile = (TextView)findViewById(R.id.tile2);
                upScore(stile);
                break;

            case R.id.tile3:
                stile = (TextView)findViewById(R.id.tile3);
                upScore(stile);
                break;

            case R.id.tile4:
                stile = (TextView)findViewById(R.id.tile4);
                upScore(stile);
                break;

            case R.id.tile5:
                stile = (TextView)findViewById(R.id.tile5);
                upScore(stile);
                break;

            case R.id.tile6:
                stile = (TextView)findViewById(R.id.tile6);
                upScore(stile);
                break;

            case R.id.tile7:
                stile = (TextView)findViewById(R.id.tile7);
                upScore(stile);
                break;

            case R.id.tile8:
                stile = (TextView)findViewById(R.id.tile8);
                upScore(stile);
                break;

            case R.id.tile9:
                stile = (TextView)findViewById(R.id.tile9);
                upScore(stile);
                break;

            case R.id.tile10:
                stile = (TextView)findViewById(R.id.tile10);
                upScore(stile);
                break;

            case R.id.tile11:
                stile = (TextView)findViewById(R.id.tile11);
                upScore(stile);
                break;

            case R.id.tile12:
                stile = (TextView)findViewById(R.id.tile12);
                upScore(stile);
                break;

            case R.id.tile13:
                stile = (TextView)findViewById(R.id.tile13);
                upScore(stile);
                break;

            case R.id.tile14:
                stile = (TextView)findViewById(R.id.tile14);
                upScore(stile);
                break;

            case R.id.tile15:
                stile = (TextView)findViewById(R.id.tile15);
                upScore(stile);
                break;

            case R.id.tile16:
                stile = (TextView)findViewById(R.id.tile16);
                upScore(stile);
                break;


            default:
                throw new RuntimeException("error: ");

        }
    }
}

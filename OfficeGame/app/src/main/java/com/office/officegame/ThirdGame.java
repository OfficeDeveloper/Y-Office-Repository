package com.office.officegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Gavlovich Maksim (reverff@gmail.com)
 * @author Yakubenko Andrii (ayakubenko92@gmail.com)
 *         2014(c)
 */
public class ThirdGame extends Activity implements View.OnClickListener, OnTouchListener {

    private Button startButton;

    private final int gameId = 3;
    private final String gameMode = "TIME ATTACK";
    private Game thirdGame = new Game(gameId, gameMode, this);

    private SoundPool sPool;
    private int popTileTouchSound;          //sound pop on touch tile
    private int wrongTileTouchSound;        //wrong voice on wrong touch tile

    private TextView timer;
    private TextView point;
    private TextView tileArray[];
    private TextView highScore;

    private int tileArrayNumber;
    private int score;
    private int delay;
    private int time;
    private int highScoreInGame;
    private int t;
    private boolean bool = false;

    private Cursor DatabaseCursor;

    private String rules;

    private Handler handler1 = new Handler();
    private Runnable task1 = new Runnable() {
        @Override
        public void run() {
            point.setText(String.valueOf(score));
            timer.setText(String.valueOf(time));
            thirdGame.changeColor(tileArray);
            t++;
            if (t == 2) {
                time--;
                t = 0;
            }
            handler1.postDelayed(this, delay);
            if (time == 0) {
                timer.setText(String.valueOf(time));
                handler1.removeCallbacks(task1);
                startButton.setBackgroundResource(R.drawable.start_button);
                bool = false;
                thirdGame.showScore(score, highScoreInGame);
            }
        }
    };

    public void upScore(TextView tile) {

        ColorDrawable drawable = (ColorDrawable) tile.getBackground();
        if ((drawable.getColor() == Color.BLACK) && (bool)) {
            if (MyActivity.boolSoundTileCheck) sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
            score++;
            time++;
            timer.setText(String.valueOf(time));
            point.setText(String.valueOf(score));
            tile.setBackgroundColor(Color.DKGRAY);
            DatabaseCursor = thirdGame.getDB().rawQuery("Select score from highScore where game_id=3", null);
            DatabaseCursor.moveToFirst();
            highScoreInGame = DatabaseCursor.getInt(DatabaseCursor.getColumnIndex("score"));
            if (score > highScoreInGame) {
                thirdGame.getDB().execSQL("Update highScore set score=" + score + " where game_id=3");
                DatabaseCursor = thirdGame.getDB().rawQuery("Select score from highScore where game_id=3", null);
                DatabaseCursor.moveToFirst();
                highScoreInGame = DatabaseCursor.getInt(DatabaseCursor.getColumnIndex("score"));
                highScore.setText(String.valueOf(highScoreInGame));
            }
        }
        if ((drawable.getColor() == Color.WHITE) && (bool)) {
            if (MyActivity.boolSoundTileCheck) sPool.play(wrongTileTouchSound, 1, 1, 1, 0, 1f);
            time--;
            timer.setText(String.valueOf(time));
            tile.setBackgroundColor(Color.RED);
            if (time == 0) {
                handler1.removeCallbacks(task1);
                startButton.setBackgroundResource(R.drawable.start_button);
                bool = false;
                thirdGame.showScore(score, highScoreInGame);
            }
        }
    }

    public void onPause() {
        bool = false;
        thirdGame.whiteArray(tileArray);
        super.onPause();
        handler1.removeCallbacks(task1);
        Button b = (Button) findViewById(R.id.startButton);
        b.setBackgroundResource(R.drawable.start_button);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstgame);

        sPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        popTileTouchSound = sPool.load(this, R.raw.poptile, 1);
        wrongTileTouchSound = sPool.load(this, R.raw.wrong, 1);

        TextView missText = (TextView) findViewById(R.id.missTimeText);
        missText.setText("TIME");
        timer = (TextView) findViewById(R.id.misses);
        timer.setText("5");
        point = (TextView) findViewById(R.id.point);
        point.setText("0");
        highScore = (TextView) findViewById(R.id.highscore);

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        startButton.setVisibility(View.INVISIBLE);

        tileArray = new TextView[16];
        tileArray[0] = (TextView) findViewById(R.id.tile1);
        tileArray[1] = (TextView) findViewById(R.id.tile2);
        tileArray[2] = (TextView) findViewById(R.id.tile3);
        tileArray[3] = (TextView) findViewById(R.id.tile4);
        tileArray[4] = (TextView) findViewById(R.id.tile5);
        tileArray[5] = (TextView) findViewById(R.id.tile6);
        tileArray[6] = (TextView) findViewById(R.id.tile7);
        tileArray[7] = (TextView) findViewById(R.id.tile8);
        tileArray[8] = (TextView) findViewById(R.id.tile9);
        tileArray[9] = (TextView) findViewById(R.id.tile10);
        tileArray[10] = (TextView) findViewById(R.id.tile11);
        tileArray[11] = (TextView) findViewById(R.id.tile12);
        tileArray[12] = (TextView) findViewById(R.id.tile13);
        tileArray[13] = (TextView) findViewById(R.id.tile14);
        tileArray[14] = (TextView) findViewById(R.id.tile15);
        tileArray[15] = (TextView) findViewById(R.id.tile16);

        for (tileArrayNumber = 0; tileArrayNumber < 16; tileArrayNumber++) {
            tileArray[tileArrayNumber].setOnTouchListener(this);
        }

        thirdGame.connectDb();
        rules = thirdGame.getGameRules();
        highScoreInGame = thirdGame.getHighScore();
        highScore.setText(String.valueOf(highScoreInGame));
        onShow();
    }

    public void onShow() {
        AlertDialog.Builder looseAlert = new AlertDialog.Builder(ThirdGame.this);
        looseAlert.setTitle(gameMode)
                .setMessage(rules)
                .setCancelable(false)
                .setNegativeButton("I am ready",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                handler1.removeCallbacks(task1);
                                startButton.setBackgroundResource(R.drawable.start_button);
                                startButton.setVisibility(View.VISIBLE);
                                bool = false;
                            }
                        }
                );
        thirdGame.whiteArray(tileArray);
        AlertDialog alert = looseAlert.create();
        alert.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            upScore((TextView) findViewById(v.getId()));
        return false;
    }

    @Override
    public void onClick(View v) {
        if (MyActivity.boolSoundTileCheck) sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
        if (bool) {
            handler1.removeCallbacks(task1);
            startButton.setBackgroundResource(R.drawable.start_button);
            bool = false;
            thirdGame.showScore(score, highScoreInGame);
        } else {
            handler1.postDelayed(task1, 0);
            startButton.setBackgroundResource(R.drawable.stop_button);
            score = 0;
            bool = true;
            delay = 500;
            time = 5;
        }
    }

    public void onBackPressed() {
        score = 0;
        delay = 500;
        time = 5;
        Intent goToChooseMenu = new Intent(this, ChooseGameMenu.class);
        startActivity(goToChooseMenu);

    }
}
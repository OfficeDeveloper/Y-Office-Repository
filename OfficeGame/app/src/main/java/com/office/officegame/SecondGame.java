package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Gavlovych Maksym (reverff@gmail.com)
 * @author Yakubenko Andrii (ayakubenko92@gmail.com)
 *         2014(c)
 */

public class SecondGame extends Activity implements View.OnClickListener, View.OnTouchListener {

    private Button startButton;

    private final int gameId = 2;
    private final String gameMode = "TIME SPRINT";
    private Game secondGame = new Game(gameId, gameMode, this);

    private SoundPool sPool;
    private int popTileTouchSound;          //sound pop on touch tile
    private int wrongTileTouchSound;        //wrong voice on wrong touch tile
    private int congratulationEndGameSound;
    private int booEndGameSound;

    private TextView misses;
    private TextView point;
    private TextView tileArray[];
    private TextView highScore;

    private int tileArrayNumber;
    private int score;
    private int delay;
    private int currentFoulsInGame;
    private int highScoreInGame;
    private int time;
    private int t = 0;

    private boolean bool = false;

    private Handler handler1 = new Handler();
    private Runnable task1 = new Runnable() {
        @Override
        public void run() {
            secondGame.changeColor(tileArray);
            t++;
            if (t == 2) {
                time--;
                t = 0;
            }

            handler1.postDelayed(this, delay);
            checkScoreAndTime();
        }
    };

    public void upScore(TextView tile) {
        ColorDrawable drawable = (ColorDrawable) tile.getBackground();
        if ((drawable.getColor() == Color.BLACK) && (bool)) {
            if (MyActivity.soundOn) sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
            score++;
            point.setText(String.valueOf(score));
            tile.setBackgroundColor(Color.DKGRAY);
            if (score > highScoreInGame) {
                highScoreInGame = score;
                highScore.setText(String.valueOf(highScoreInGame));
            }
        }
        if ((drawable.getColor() == Color.WHITE) && (bool)) {
            if (MyActivity.soundOn) sPool.play(wrongTileTouchSound, 1, 1, 1, 0, 1f);
            currentFoulsInGame--;
            misses.setText(String.valueOf(currentFoulsInGame + "/" + time));
            tile.setBackgroundColor(Color.RED);
        }

    }

    public void checkScoreAndTime() {
        point.setText(String.valueOf(score));
        misses.setText(String.valueOf(currentFoulsInGame + "/" + time));
        if ((currentFoulsInGame < 1) || (time == 0)) {
            handler1.removeCallbacks(task1);
            startButton.setBackgroundResource(R.drawable.start_button);
            bool = false;
            secondGame.whiteArray(tileArray);
            secondGame.showScore(score, highScoreInGame);
            onEnd();
        }
    }

    public void onPause() {
        bool = false;
        secondGame.whiteArray(tileArray);
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
        congratulationEndGameSound = sPool.load(this, R.raw.smallcrowd, 1);
        booEndGameSound = sPool.load(this, R.raw.crowdboo, 1);
        TextView timeText = (TextView) findViewById(R.id.missTimeText);
        timeText.setText("MISS/TIME");
        misses = (TextView) findViewById(R.id.misses);
        misses.setText("20/30");
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

        secondGame.connectDb();
        highScoreInGame = secondGame.getHighScore();
        highScore.setText(String.valueOf(highScoreInGame));
        secondGame.onShow(startButton);
    }

    public void onEnd() {
            if (score == highScoreInGame) {
                if (MyActivity.soundOn) sPool.play(congratulationEndGameSound, 1, 1, 1, 0, 1f);
                secondGame.updateHighScore(highScoreInGame);
            }
            else if (MyActivity.soundOn) sPool.play(booEndGameSound, 1, 1, 1, 0, 1f);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            upScore((TextView) findViewById(v.getId()));
        return false;
    }

    @Override
    public void onClick(View v) {
        if (MyActivity.soundOn)
            sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
        if (bool) {
            handler1.removeCallbacks(task1);
            startButton.setBackgroundResource(R.drawable.start_button);
            bool = false;
            secondGame.whiteArray(tileArray);
            secondGame.showScore(score, highScoreInGame);
            onEnd();
        } else {
            handler1.postDelayed(task1, 0);
            startButton.setBackgroundResource(R.drawable.stop_button);
            score = 0;
            bool = true;
            delay = 500;
            currentFoulsInGame = 20;
            time = 30;
        }

    }

    public void onBackPressed() {
        super.onBackPressed();
        score = 0;
        delay = 500;
        currentFoulsInGame = 20;
        time = 30;
        Intent goToChooseMenu = new Intent(this, MyActivity.class);
        startActivity(goToChooseMenu);
    }
}
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
 * @author Gavlovych Maksym (reverff@gmail.com)
 * @author Yakubenko Andrii (ayakubenko92@gmail.com)
 *         2014(c)
 */
public class Firstgame extends Activity implements View.OnClickListener, OnTouchListener {

    private Button startButton;

    private final int gameId = 1;
    private final String gameMode = "ARCADE";
    private Game firstGame = new Game(gameId, gameMode, this);

    private SoundPool sPool;
    private int popTileTouchSound;          //sound pop on touch tile
    private int wrongTileTouchSound;        //wrong voice on wrong touch tile

    private TextView gameMisses;            //game-miss counter
    private TextView gamePoint;             //game-point counter
    private TextView tileArray[];           //game-tile array [15]
    private TextView highScore;             //game-high score

    private int tileArrayNumber;            //setOnTouch number of current tile
    private int score;                      //field in game with game-score and database field
    private int delay;                      //the delay between setColor black\white on tile
    private int currentFoulsInGame;         //<--
    private int highScoreInGame;            //<--

    private boolean boolKey = false;        //checking boolean key.

    private Cursor DatabaseCursor;

    private String rules;

    private Handler handler1 = new Handler();
    private Runnable task1 = new Runnable() {
        @Override
        public void run() {
            gamePoint.setText(String.valueOf(score));
            gameMisses.setText(String.valueOf(currentFoulsInGame));
            firstGame.changeColor(tileArray);
            handler1.postDelayed(this, delay);

        }
    };

    public void upScore(TextView tile) {                    //up-gameScore when user clicked black tile

        ColorDrawable drawable = (ColorDrawable) tile.getBackground();
        if ((drawable.getColor() == Color.BLACK) && (boolKey)) {
            if (MyActivity.boolSoundTileCheck) sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
            score++;
            gamePoint.setText(String.valueOf(score));
            tile.setBackgroundColor(Color.DKGRAY);
            DatabaseCursor = firstGame.getDB().rawQuery("Select score from highScore where game_id=1", null);
            DatabaseCursor.moveToFirst();
            highScoreInGame = DatabaseCursor.getInt(DatabaseCursor.getColumnIndex("score"));
            if (score > highScoreInGame) {
                firstGame.getDB().execSQL("Update highScore set score=" + score + " where game_id=1");
                DatabaseCursor = firstGame.getDB().rawQuery("Select score from highScore where game_id=1", null);
                DatabaseCursor.moveToFirst();
                highScoreInGame = DatabaseCursor.getInt(DatabaseCursor.getColumnIndex("score"));
                highScore.setText(String.valueOf(highScoreInGame));
            }
        }

        if ((drawable.getColor() == Color.WHITE) && (boolKey)) {
            if (MyActivity.boolSoundTileCheck) sPool.play(wrongTileTouchSound, 1, 1, 1, 0, 1f);
            currentFoulsInGame--;
            gameMisses.setText(String.valueOf(currentFoulsInGame));
            tile.setBackgroundColor(Color.RED);
            if (currentFoulsInGame == 0) {
                handler1.removeCallbacks(task1);
                startButton.setBackgroundResource(R.drawable.start_button);
                boolKey = false;
                firstGame.showScore(score, highScoreInGame);
            }
        }

        delay = (int) (delay * 0.998);
    }

    public void onPause() {
        boolKey = false;
        firstGame.whiteArray(tileArray);
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
        missText.setText("MISSES");
        gameMisses = (TextView) findViewById(R.id.misses);
        gameMisses.setText("20");
        gamePoint = (TextView) findViewById(R.id.point);
        gamePoint.setText("0");
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

        firstGame.connectDb();
        rules = firstGame.getGameRules();
        highScoreInGame = firstGame.getHighScore();
        highScore.setText(String.valueOf(highScoreInGame));
        onShow();
    }

    public void onShow() {                          //show rules before start game
        AlertDialog.Builder looseAlert = new AlertDialog.Builder(Firstgame.this);
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
                                boolKey = false;
                            }
                        }
                );
        firstGame.whiteArray(tileArray);
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
        if (MyActivity.boolSoundTileCheck)
            sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
        if (boolKey) {
            handler1.removeCallbacks(task1);
            startButton.setBackgroundResource(R.drawable.start_button);
            boolKey = false;
            firstGame.showScore(score, highScoreInGame);
        } else {
            handler1.postDelayed(task1, 0);
            startButton.setBackgroundResource(R.drawable.stop_button);
            score = 0;
            boolKey = true;
            delay = 700;
            currentFoulsInGame = 20;
        }
    }

    public void onBackPressed() {
        score = 0;
        delay = 700;
        currentFoulsInGame = 20;
        Intent goToChooseMenu = new Intent(this, ChooseGameMenu.class);
        startActivity(goToChooseMenu);
    }
}
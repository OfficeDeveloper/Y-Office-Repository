package com.office.officegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Gavlovich Maksim (reverff@gmail.com)
 * @author Yakubenko Andrii (ayakubenko92@gmail.com)
 * 2014(c)
 */
public class FirstGame extends Activity implements View.OnClickListener, OnTouchListener {

    private Button startButton;


    private SoundPool sPool;
    private int popTileTouchSound;          //sound pop on touch tile
    private int wrongTileTouchSound;        //wrong voice on wrong touch tile
    private int congratulationEndGameSound; //congratulation sound when result >=35
    private int booEndGameSound;            //boo voice when result <35

    private TextView misses;
    private TextView point;
    private TextView tileArray[];
    private TextView highScore;

    private int score;
    private int delay;
    private int fouls;
    private int hiScore;

    private boolean bool = false;

    private final String LOG_TAG = "myLogs";

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    private String mode = "Arcade";
    private String rules = "Arcade mode. In this game you should hit as much black tiles as you can. But you can miss only 20 times. Every time when you will shout tiles will change faster. Good luck!";

    private Handler handler1 = new Handler();
    private Runnable task1 = new Runnable() {
        @Override
        public void run() {
            point.setText(String.valueOf(score));
            misses.setText(String.valueOf(fouls));
            changeColor(tileArray);
            handler1.postDelayed(this, delay);

        }
    };

    public void changeColor(TextView[] tiles) {
        for (int i = 0; i < 16; i++) {
            tiles[i].setBackgroundColor(Color.WHITE);
        }

        for (int i = 0; i < 5; i++) {
            int k = (int) (Math.random() * 16);
            tiles[k].setBackgroundColor(Color.BLACK);
        }
    }

    public void whiteArray(){
        for (int i = 0; i < 16; i++) {
            tileArray[i].setBackgroundColor(Color.WHITE);
        }
    }

    public void showScore(){
        AlertDialog.Builder looseAlert = new AlertDialog.Builder(FirstGame.this);

        if (score >= 35) {   //if game over with result >=35 - play sound congratulation
            sPool.play(congratulationEndGameSound, 1, 1, 1, 0, 1f);
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
            if (score == hiScore){
            looseAlert.setMessage("You finished with score: " + score+"\nThis is your new high score!");
            }
        }
            else {           //if game over with result <35 - play sound Boo
            sPool.play(booEndGameSound, 1, 1, 1, 0, 1f);
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
                if (score == hiScore){
                looseAlert.setMessage("You finished with score: " + score+"\nThis is your new high score!");
                }
            }
        whiteArray();
        AlertDialog alert = looseAlert.create();
        alert.show();
    }

    public void upScore(TextView tile) {
        ColorDrawable drawable = (ColorDrawable) tile.getBackground();
        if ((drawable.getColor() == Color.BLACK) && (bool)) {
            sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
            score = score + 1;
            point.setText(String.valueOf(score));
            tile.setBackgroundColor(Color.DKGRAY);
            cursor = db.rawQuery("Select score from highScore where game_id=1", null);
            cursor.moveToFirst();
            hiScore = cursor.getInt(cursor.getColumnIndex("score"));
            if (score>hiScore){
                db.execSQL("Update highScore set score="+score+" where game_id=1");
                cursor = db.rawQuery("Select score from highScore where game_id=1", null);
                cursor.moveToFirst();
                hiScore = cursor.getInt(cursor.getColumnIndex("score"));
                highScore.setText(String.valueOf(hiScore));
            }
        }

        if ((drawable.getColor() == Color.WHITE) && (bool)) {
            sPool.play(wrongTileTouchSound, 1, 1, 1, 0, 1f);
            fouls = fouls - 1;
            misses.setText(String.valueOf(fouls));
            tile.setBackgroundColor(Color.RED);
            if (fouls == 0) {
                handler1.removeCallbacks(task1);
                startButton.setBackgroundResource(R.drawable.start_button);
                bool = false;
                showScore();
            }
        }

        delay = (int) (delay * 0.997);
    }

    public void onPause() {
        bool = false;
        whiteArray();
        if(score != 0) showScore();
        super.onPause();
        handler1.removeCallbacks(task1);
        Button b = (Button) findViewById(R.id.startButton);
        b.setBackgroundResource(R.drawable.start_button);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstgame);

        sPool = new SoundPool(4,AudioManager.STREAM_MUSIC, 0);
        popTileTouchSound = sPool.load(this, R.raw.poptile, 1);
        wrongTileTouchSound = sPool.load(this, R.raw.wrong, 1);
        congratulationEndGameSound = sPool.load(this, R.raw.smallcrowd, 1);
        booEndGameSound = sPool.load(this, R.raw.crowdboo, 1);

        TextView missText = (TextView) findViewById(R.id.missTimeText);
        missText.setText("MISSES");
        misses = (TextView) findViewById(R.id.misses);
        misses.setText("20");

        point = (TextView) findViewById(R.id.point);
        point.setText("0");

        highScore = (TextView) findViewById(R.id.highscore);

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        tileArray = new TextView[16];

        tileArray[0] = (TextView) findViewById(R.id.tile1);
        tileArray[0].setOnTouchListener(this);

        tileArray[1] = (TextView) findViewById(R.id.tile2);
        tileArray[1].setOnTouchListener(this);

        tileArray[2] = (TextView) findViewById(R.id.tile3);
        tileArray[2].setOnTouchListener(this);

        tileArray[3] = (TextView) findViewById(R.id.tile4);
        tileArray[3].setOnTouchListener(this);

        tileArray[4] = (TextView) findViewById(R.id.tile5);
        tileArray[4].setOnTouchListener(this);

        tileArray[5] = (TextView) findViewById(R.id.tile6);
        tileArray[5].setOnTouchListener(this);

        tileArray[6] = (TextView) findViewById(R.id.tile7);
        tileArray[6].setOnTouchListener(this);

        tileArray[7] = (TextView) findViewById(R.id.tile8);
        tileArray[7].setOnTouchListener(this);

        tileArray[8] = (TextView) findViewById(R.id.tile9);
        tileArray[8].setOnTouchListener(this);

        tileArray[9] = (TextView) findViewById(R.id.tile10);
        tileArray[9].setOnTouchListener(this);

        tileArray[10] = (TextView) findViewById(R.id.tile11);
        tileArray[10].setOnTouchListener(this);

        tileArray[11] = (TextView) findViewById(R.id.tile12);
        tileArray[11].setOnTouchListener(this);

        tileArray[12] = (TextView) findViewById(R.id.tile13);
        tileArray[12].setOnTouchListener(this);

        tileArray[13] = (TextView) findViewById(R.id.tile14);
        tileArray[13].setOnTouchListener(this);

        tileArray[14] = (TextView) findViewById(R.id.tile15);
        tileArray[14].setOnTouchListener(this);

        tileArray[15] = (TextView) findViewById(R.id.tile16);
        tileArray[15].setOnTouchListener(this);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery("Select score from highScore where game_id=1", null);
        cursor.moveToFirst();
        hiScore = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
        highScore.setText(String.valueOf(hiScore));
        onShow();

    }




    public void onShow(){
        AlertDialog.Builder looseAlert = new AlertDialog.Builder(FirstGame.this);
        looseAlert.setTitle(mode)
                .setMessage(rules)
                .setIcon(R.drawable.ic_launcher)
                .setCancelable(false)
                .setNegativeButton("Start",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                handler1.postDelayed(task1, delay);
                                startButton.setBackgroundResource(R.drawable.stop_button);
                                score = 0;
                                bool = true;
                                delay = 500;
                                fouls = 20;
                            }
                        }
                );
        whiteArray();
        AlertDialog alert = looseAlert.create();
        alert.show();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) switch (v.getId()) {

            case R.id.tile1:
                upScore(tileArray[0]);
                break;

            case R.id.tile2:
                upScore(tileArray[1]);
                break;

            case R.id.tile3:
                upScore(tileArray[2]);
                break;

            case R.id.tile4:
                upScore(tileArray[3]);
                break;

            case R.id.tile5:
                upScore(tileArray[4]);
                break;

            case R.id.tile6:
                upScore(tileArray[5]);
                break;

            case R.id.tile7:
                upScore(tileArray[6]);
                break;

            case R.id.tile8:
                upScore(tileArray[7]);
                break;

            case R.id.tile9:
                upScore(tileArray[8]);
                break;

            case R.id.tile10:
                upScore(tileArray[9]);
                break;

            case R.id.tile11:
                upScore(tileArray[10]);
                break;

            case R.id.tile12:
                upScore(tileArray[11]);
                break;

            case R.id.tile13:
                upScore(tileArray[12]);
                break;

            case R.id.tile14:
                upScore(tileArray[13]);
                break;

            case R.id.tile15:
                upScore(tileArray[14]);
                break;

            case R.id.tile16:
                upScore(tileArray[15]);
                break;


            default:
                throw new RuntimeException("error: ");
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                if (bool) {
                    handler1.removeCallbacks(task1);
                    startButton.setBackgroundResource(R.drawable.start_button);
                    bool = false;
                    showScore();
                } else {
                    handler1.postDelayed(task1, 0);
                    startButton.setBackgroundResource(R.drawable.stop_button);
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

    public void onBackPressed() {
        Intent goToChooseMenu = new Intent(this, ChooseGameMenu.class);
        startActivity(goToChooseMenu);
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "OfficeGameDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table highScore ("
                    + "id integer primary key autoincrement,"
                    + "game_id integer,"
                    + "score integer);");
            db.execSQL("insert into highScore ('game_id','score') values (1,0);");
            db.execSQL("insert into highScore ('game_id','score') values (2,0);");
            db.execSQL("insert into highScore ('game_id','score') values (3,0);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
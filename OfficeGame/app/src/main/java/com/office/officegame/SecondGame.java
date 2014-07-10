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
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Gavlovich Maksim (reverff@gmail.com)
 * 2014(c)
 */

public class SecondGame extends Activity implements View.OnClickListener, View.OnTouchListener {

    private Button backToMainMenu;
    private Button startButton;

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

    private Handler handler1 = new Handler();
    private Runnable task1 = new Runnable() {
        @Override
        public void run() {
            point.setText(String.valueOf(score));
            misses.setText(String.valueOf(fouls+"/30"));
            changeColor(tileArray);
            handler1.postDelayed(this, delay);

        }
    };

    public void changeColor(TextView[] tiles) {
        for (int i = 0; i < 16; i++) {
            tiles[i].setBackgroundColor(Color.WHITE);
        }

        for (int i = 0; i < 8; i++) {
            int k = (int) (Math.random() * 16);
            tiles[k].setBackgroundColor(Color.BLACK);
        }
    }

    public void upScore(TextView tile) {
        ColorDrawable drawable = (ColorDrawable) tile.getBackground();
        if ((drawable.getColor() == Color.BLACK) && (bool)) {
            score = score + 1;
            point.setText(String.valueOf(score));
            tile.setBackgroundColor(Color.DKGRAY);
            cursor = db.rawQuery("Select score from highScore where game_id=2", null);
            cursor.moveToFirst();
            hiScore = cursor.getInt(cursor.getColumnIndex("score"));
            if (score > hiScore) {
                db.execSQL("Update highScore set score=" + score + " where game_id=2");
                cursor = db.rawQuery("Select score from highScore where game_id=2", null);
                cursor.moveToFirst();
                hiScore = cursor.getInt(cursor.getColumnIndex("score"));
                highScore.setText(String.valueOf(hiScore));
            }
        }

        if ((drawable.getColor() == Color.WHITE) && (bool)) {
            fouls = fouls - 1;
            misses.setText(String.valueOf(fouls)+"/30");
            tile.setBackgroundColor(Color.RED);
            if (fouls == 0) {
                handler1.removeCallbacks(task1);
                startButton.setText("Start");
                bool = false;
                AlertDialog.Builder looseAlert = new AlertDialog.Builder(SecondGame.this);
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
                for (int i = 0; i < 16; i++) {
                    tileArray[i].setBackgroundColor(Color.WHITE);
                }
                if (score == hiScore) {
                    looseAlert.setMessage("You finished with score: " + score + "\nThis is your new high score!");
                }
                AlertDialog alert = looseAlert.create();
                alert.show();
            }
        }
    }

    public void onPause() {
        super.onPause();
        handler1.removeCallbacks(task1);
        Button b = (Button) findViewById(R.id.startButton);
        b.setText("Start");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstgame);

        TextView timeText = (TextView) findViewById(R.id.missTimeText);
        timeText.setText("MISS/TIME");
        misses = (TextView) findViewById(R.id.misses);
        misses.setText("20/30");

        point = (TextView) findViewById(R.id.point);
        point.setText("0");

        highScore = (TextView) findViewById(R.id.highscore);

        backToMainMenu = (Button) findViewById(R.id.backToMainMenu);
        backToMainMenu.setOnClickListener(this);

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
        cursor = db.rawQuery("Select score from highScore where game_id=2", null);
        cursor.moveToFirst();
        hiScore = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
        highScore.setText(String.valueOf(hiScore));
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
            case R.id.backToMainMenu:
                Intent goToChooseMenu = new Intent(SecondGame.this, ChooseGameMenu.class);
                startActivity(goToChooseMenu);
                break;

            case R.id.startButton:
                if (startButton.getText().equals("Stop")) {
                    handler1.removeCallbacks(task1);
                    startButton.setText("Start");
                    bool = false;
                    AlertDialog.Builder looseAlert = new AlertDialog.Builder(SecondGame.this);
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
                        tileArray[i].setBackgroundColor(Color.WHITE);
                    }
                    alert.show();
                } else {
                    handler1.postDelayed(task1, 0);
                    startButton.setText("Stop");
                    score = 0;
                    bool = true;
                    delay = 500;
                    fouls = 20;
                }
                break;

            default:
                throw new RuntimeException("error: ");
        }

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

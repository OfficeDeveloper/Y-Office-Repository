package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Андрей on 06.07.2014.
 */



public class SecondGame extends Activity implements View.OnClickListener, View.OnTouchListener {

    Button backToMainMenuSecondGame;
    Button startButtonSGame;
    Button rulesSGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondgame);

        backToMainMenuSecondGame = (Button) findViewById(R.id.backToMainMenuSecondGame);
        startButtonSGame = (Button) findViewById(R.id.startButtonSGame);
        rulesSGame = (Button) findViewById(R.id.rulesSGame);

        backToMainMenuSecondGame.setOnClickListener(this);
        startButtonSGame.setOnClickListener(this);
        rulesSGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View viewSGame) {

        switch (viewSGame.getId()) {
            case R.id.backToMainMenuSecondGame:
                Intent intent = new Intent(SecondGame.this, ChooseGameMenu.class);
                startActivity(intent);
                break;


            default:
                throw new RuntimeException("error: ");

        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}

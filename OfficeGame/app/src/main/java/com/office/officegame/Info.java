package com.office.officegame;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

public class Info extends Activity implements View.OnClickListener, View.OnTouchListener {
    ViewAnimator views;
    TextView arcadeGames, arcadeSh, arcadeHs;

    void swipeLeft(){
        views.setInAnimation(this, R.anim.in_right);
        views.setOutAnimation(this, R.anim.out_left);
        views.showNext();
    }

    void swipeRight(){
        views.setInAnimation(this, R.anim.in_left);
        views.setOutAnimation(this, R.anim.out_right);
        views.showPrevious();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        views = (ViewAnimator) findViewById(R.id.viewAnimator);


        arcadeHs = (TextView) findViewById(R.id.arcadeHs);
        arcadeSh = (TextView) findViewById(R.id.arcadeSh);
        arcadeGames = (TextView) findViewById(R.id.arcadeGames);

        Game info = new Game(this);
        info.connectDb();
        arcadeGames.setText(String.valueOf(info.getGamesCount(1)));
        arcadeHs.setText(String.valueOf(info.getHighScore(1)));
        arcadeSh.setText(String.valueOf(info.getSumScore(1)));

        views.setOnTouchListener(this);

    }

    @Override
    public void onClick(View v) {

        Toast.makeText(getBaseContext(), "CLICK",Toast.LENGTH_SHORT).show();
    }
    float startX;
    @Override
    public boolean onTouch(View v, MotionEvent e) {
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = e.getX();
                break;
            case MotionEvent.ACTION_UP:
                float endX = e.getX();
                if(startX > endX) {swipeLeft();}
                if(startX < endX) {swipeRight();}
                return false;
            default: break;
        }
        return true;
    }
}

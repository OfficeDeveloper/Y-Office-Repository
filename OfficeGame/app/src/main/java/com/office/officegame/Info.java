package com.office.officegame;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

public class Info extends BaseGameActivity implements View.OnClickListener, View.OnTouchListener {
    ViewAnimator views;
    Game info;
    float startX;
    TextView arcadeGames, arcadeSh, arcadeHs;
    TextView tsGames, tsSh, tsHs;
    TextView taGames, taSh, taHs;
    TextView sumGames, sumSh, sumHs;
    TextView arcadeNext, arcadePrev, tsNext, tsPrev, taNext, taPrev, sumNext, sumPrev;

    int shs=0;
    String ssh;

    void swipeNext(){
        views.setInAnimation(this, R.anim.in_right);
        views.setOutAnimation(this, R.anim.out_left);
        views.showNext();
    }

    void swipePrev(){
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

        tsHs = (TextView) findViewById(R.id.tsHs);
        tsSh = (TextView) findViewById(R.id.tsSh);
        tsGames = (TextView) findViewById(R.id.tsGames);

        taHs = (TextView) findViewById(R.id.taHs);
        taSh = (TextView) findViewById(R.id.taSh);
        taGames = (TextView) findViewById(R.id.taGames);

        sumHs = (TextView) findViewById(R.id.sumHs);
        sumSh = (TextView) findViewById(R.id.sumSh);
        sumGames = (TextView) findViewById(R.id.sumGames);

        arcadeNext = (TextView) findViewById(R.id.arcadeNext);
        arcadeNext.setOnClickListener(this);

        arcadePrev = (TextView) findViewById(R.id.arcadePrev);
        arcadePrev.setOnClickListener(this);

        tsNext = (TextView) findViewById(R.id.tsNext);
        tsNext.setOnClickListener(this);

        tsPrev = (TextView) findViewById(R.id.tsPrev);
        tsPrev.setOnClickListener(this);

        taNext = (TextView) findViewById(R.id.taNext);
        taNext.setOnClickListener(this);

        taPrev = (TextView) findViewById(R.id.taPrev);
        taPrev.setOnClickListener(this);

        sumNext = (TextView) findViewById(R.id.sumNext);
        sumNext.setOnClickListener(this);

        sumPrev = (TextView) findViewById(R.id.sumPrev);
        sumPrev.setOnClickListener(this);


        info = new Game(this);
        info.connectDb();
        info.createPlayer();

        int hs1 = info.getHighScore(1);
        int hs2 = info.getHighScore(2);
        int hs3 = info.getHighScore(3);

        arcadeGames.setText(String.valueOf(info.getGamesCount(1)));
        arcadeHs.setText(String.valueOf(hs1));
        arcadeSh.setText(String.valueOf(info.getSumScore(1)));

        tsGames.setText(String.valueOf(info.getGamesCount(2)));
        tsHs.setText(String.valueOf(hs2));
        tsSh.setText(String.valueOf(info.getSumScore(2)));

        taGames.setText(String.valueOf(info.getGamesCount(3)));
        taHs.setText(String.valueOf(hs3));
        taSh.setText(String.valueOf(info.getSumScore(3)));

        String sg = String.valueOf(info.getGamesCount(1)+info.getGamesCount(2)+info.getGamesCount(3));
        sumGames.setText(sg);


        if (hs1>=hs2 && hs1>=hs3) shs = hs1;
        if (hs2>=hs1 && hs2>=hs3) shs = hs2;
        if (hs3>=hs1 && hs3>=hs2) shs = hs3;

        sumHs.setText(String.valueOf(shs));

        ssh = String.valueOf(info.getSumScore(1)+info.getSumScore(2)+info.getSumScore(3));
        sumSh.setText(ssh);
        views.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arcadeNext:
                swipeNext();
                break;
            case R.id.arcadePrev:
                swipePrev();
                break;
            case R.id.tsNext:
                swipeNext();
                break;
            case R.id.tsPrev:
                swipePrev();
                break;
            case R.id.taNext:
                swipeNext();
                break;
            case R.id.taPrev:
                swipePrev();
                break;
            case R.id.sumNext:
                swipeNext();
                break;
            case R.id.sumPrev:
                swipePrev();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = e.getX();
                break;
            case MotionEvent.ACTION_UP:
                float endX = e.getX();
                if(startX > endX) {
                    swipeNext();}
                if(startX < endX) {
                    swipePrev();}
                return false;
            default: break;
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        info.pauseMusic();
    }

    public void onResume() {
        super.onResume();
        info.startMusic();
    }

    public void onStop() {
        super.onStop();
        info.stopMusic();
    }

    @Override
    public void onSignInFailed() {
        Toast.makeText(this, "Failed signing to Google Play Games", Toast.LENGTH_SHORT).show();
        info.setSignStatus(0);
    }

    @Override
    public void onSignInSucceeded() {
        info.setSignStatus(1);
    }
}

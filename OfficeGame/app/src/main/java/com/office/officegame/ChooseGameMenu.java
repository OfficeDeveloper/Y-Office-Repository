package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * @author Gavlovich Maksim (reverff@gmail.com)
 * @author Yakubenko Andrii (ayakubenko92@gmail.com)
 * 2014(c)
 */
public class ChooseGameMenu extends Activity implements View.OnClickListener {


    private SoundPool sPool;
    private int popTileTouchSound;          //sound pop on touch tile
    private Button goToFirstGame, goToSecondGame, goToThirdGame, comeBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosegamemenu);

        sPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        popTileTouchSound = sPool.load(this, R.raw.poptile, 1);

        goToFirstGame = (Button) findViewById(R.id.goToFirstGame);
        goToSecondGame = (Button) findViewById(R.id.goToSecondGame);
        goToThirdGame = (Button) findViewById(R.id.goToThirdGame);
        comeBack = (Button) findViewById(R.id.comeBack);

        goToFirstGame.setOnClickListener(this);
        goToSecondGame.setOnClickListener(this);
        goToThirdGame.setOnClickListener(this);
        comeBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View button) {

        switch (button.getId()) {

            case R.id.goToFirstGame:
                Intent beginFirstGame = new Intent(ChooseGameMenu.this, Firstgame.class);
                sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                startActivity(beginFirstGame);
                break;

            case R.id.goToSecondGame:
                Intent beginSecondGame = new Intent(ChooseGameMenu.this, SecondGame.class);
                sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                startActivity(beginSecondGame);
                break;

            case R.id.goToThirdGame:
                Intent beginThirdGame = new Intent(ChooseGameMenu.this, ThirdGame.class);
                sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                startActivity(beginThirdGame);
                break;

            case R.id.comeBack:
                Intent comeBackToMainMenu = new Intent (ChooseGameMenu.this, MyActivity.class);
                sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                startActivity(comeBackToMainMenu);
                break;

            default:
                throw new RuntimeException("error: ");

        }

    }

    public void onBackPressed() {
        Intent comeBackToMainMenu = new Intent (ChooseGameMenu.this, MyActivity.class);
        startActivity(comeBackToMainMenu);
    }
}

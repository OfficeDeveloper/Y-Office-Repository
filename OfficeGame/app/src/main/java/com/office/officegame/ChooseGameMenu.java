package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * @author Gavlovich Maksim (reverff@gmail.com)
 * @author Yakubenko Andrii (ayakubenko92@gmail.com)
 * 2014(c)
 */
public class ChooseGameMenu extends Activity implements View.OnClickListener {



    Button goToFirstGame;
    Button goToSecondGame;
    Button goToThirdGame;
    Button comeBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosegamemenu);

        goToFirstGame = (Button) findViewById(R.id.goToFirstGame);
        goToSecondGame = (Button) findViewById(R.id.goToSecondGame);
        goToThirdGame = (Button) findViewById(R.id.goToThirdGame);
        comeBack = (Button) findViewById(R.id.comeBack);

        goToFirstGame.setOnClickListener(this);
        goToSecondGame.setOnClickListener(this);
        goToThirdGame.setOnClickListener(this);
        comeBack.setOnClickListener(this);
    }
    protected void onResume() {
        super.onResume();
        Music.play(this, R.raw.guitarmainfon);
    }
    protected void onPause() {
        super.onPause();
        Music.stop(this);
    }

    @Override
    public void onClick(View button) {

        switch (button.getId()) {

            case R.id.goToFirstGame:
                Intent beginFirstGame = new Intent(ChooseGameMenu.this, FirstGame.class);
                startActivity(beginFirstGame);
                break;

            case R.id.goToSecondGame:
                Intent beginSecondGame = new Intent(ChooseGameMenu.this, SecondGame.class);
                startActivity(beginSecondGame);
                break;

            case R.id.goToThirdGame:
                Intent beginThirdGame = new Intent(ChooseGameMenu.this, ThirdGame.class);
                startActivity(beginThirdGame);
                break;

            case R.id.comeBack:
                Intent comeBackToMainMenu = new Intent (ChooseGameMenu.this, MyActivity.class);
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

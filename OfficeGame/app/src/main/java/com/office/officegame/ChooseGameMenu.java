package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Андрей on 02.07.2014 at 4:10.
 * Паскудатварь
 */
public class ChooseGameMenu extends Activity implements View.OnClickListener {

    Button goToFirstGame;
    Button goToSecondGame;
    //Button goToThirdGame;
    Button goToFourthGame;
    Button comeBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosegamemenu);

        goToFirstGame = (Button) findViewById(R.id.goToFirstGame);
        goToSecondGame = (Button) findViewById(R.id.goToSecondGame);
        //goToThirdGame = (Button) findViewById(R.id.goToThirdGame);
        goToFourthGame = (Button) findViewById(R.id.goToFourthGame);
        comeBack = (Button) findViewById(R.id.comeBack);

       goToFirstGame.setOnClickListener(this);
       goToSecondGame.setOnClickListener(this);
 //       goToThirdGame.setOnClickListener(this);
        goToFourthGame.setOnClickListener(this);
        comeBack.setOnClickListener(this);
        //паскудатварь
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

            case R.id.goToFourthGame:
                Intent beginFourthGame = new Intent(ChooseGameMenu.this, FourthGame.class);
                startActivity(beginFourthGame);
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

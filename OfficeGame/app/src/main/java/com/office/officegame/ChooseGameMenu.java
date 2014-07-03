package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Андрей on 02.07.2014.
 */
public class ChooseGameMenu extends Activity implements View.OnClickListener {

    Button goToFirstGame;
   // Button goToSecondGame;
    //Button goToThirdGame;
    //Button goToFirthGame;
    Button comeBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosegamemenu);

        goToFirstGame = (Button) findViewById(R.id.goToFirstGame);
        //goToSecondGame = (Button) findViewById(R.id.goToSecondGame);
        //goToThirdGame = (Button) findViewById(R.id.goToThirdGame);
        //goToFirthGame = (Button) findViewById(R.id.goToFirthGame);
        comeBack = (Button) findViewById(R.id.comeBack);

       goToFirstGame.setOnClickListener(this);
//        goToSecondGame.setOnClickListener(this);
 //       goToThirdGame.setOnClickListener(this);
 //       goToFirthGame.setOnClickListener(this);
        comeBack.setOnClickListener(this);
        //паскудатварь
    }

    @Override
    public void onClick(View SecondView) {

        switch (SecondView.getId()) {

            case R.id.goToFirstGame:
                Intent begin = new Intent(this, FirstGame.class);
                startActivity(begin);
                break;
            case R.id.comeBack:
                Intent goHome = new Intent(this, MyActivity.class);
                startActivity(goHome);
                break;


            default:
                throw new RuntimeException("error: ");


        }

    }
}

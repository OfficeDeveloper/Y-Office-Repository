package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Андрей on 03.07.2014.
 */
public class FirstGame extends Activity implements View.OnClickListener {

   Button backToMainMenu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstgame);

        backToMainMenu = (Button) findViewById(R.id.backToMainMenu);
    }

    public void onClick(View third) {
        switch (third.getId()) {
            case R.id.backToMainMenu:
                Intent goHome = new Intent(this, ChooseGameMenu.class);
                startActivity(goHome);
                break;


            default:
                throw new RuntimeException("error: ");

        }
    }
}

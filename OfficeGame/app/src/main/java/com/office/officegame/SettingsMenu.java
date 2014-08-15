package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



/**
 * Created by Andrii on 8/15/14.
 */
public class SettingsMenu extends Activity implements View.OnClickListener {

    private Button backToMainMenuButton;
    private Button muteVoiceButtonInTheEntrieGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);

        backToMainMenuButton = (Button) findViewById(R.id.backToMainMenuButton);
        muteVoiceButtonInTheEntrieGame = (Button) findViewById(R.id.muteVoiceButtonInTheEntrieGame);

        backToMainMenuButton.setOnClickListener(this);
        muteVoiceButtonInTheEntrieGame.setOnClickListener(this);

    }
    protected void onResume() {
        super.onResume();
        Music.play(this, R.raw.guitarmainfon);
    }

    @Override
    public void onClick(View SettingsMenu) {

        switch (SettingsMenu.getId()) {
            case R.id.backToMainMenuButton:
                Intent goToMainActivity = new Intent(SettingsMenu.this, MyActivity.class);
                startActivity(goToMainActivity);
                break;

            case R.id.muteVoiceButtonInTheEntrieGame:
                //
                break;


        }
    }
}

package com.office.officegame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



/**
 * Created by Andrii on 8/15/14.
 */
public class SettingsMenu extends Activity implements View.OnClickListener {

    private SoundPool sPool;
    private int popTileTouchSound;          //sound pop on touch tile
    private Button  backToMainMenuButton;
    private Button  muteVoiceButtonInTheEntrieGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);

        sPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        popTileTouchSound = sPool.load(this, R.raw.poptile, 1);

        backToMainMenuButton = (Button) findViewById(R.id.backToMainMenuButton);
        muteVoiceButtonInTheEntrieGame = (Button) findViewById(R.id.muteVoiceButtonInTheEntrieGame);

        backToMainMenuButton.setOnClickListener(this);
        muteVoiceButtonInTheEntrieGame.setOnClickListener(this);

    }


    @Override
    public void onClick(View SettingsMenu) {

        switch (SettingsMenu.getId()) {
            case R.id.backToMainMenuButton:
                Intent goToMainActivity = new Intent(SettingsMenu.this, MyActivity.class);
                sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);
                startActivity(goToMainActivity);
                break;

            case R.id.muteVoiceButtonInTheEntrieGame:
                sPool.play(popTileTouchSound, 1, 1, 1, 0, 1f);



                break;


        }
    }
}

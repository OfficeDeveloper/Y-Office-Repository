package com.office.officegame;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Andrii on 8/14/14.
 */
public class Prefs extends MyActivity {
    // Option names and default values
    private static final String OPT_MUSIC = "music";
    private static final boolean OPT_MUSIC_DEF = true;

    /** Get the current value of the music option */
    public static boolean getMusic(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(OPT_MUSIC, OPT_MUSIC_DEF);
    }



}
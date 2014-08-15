package com.office.officegame;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Andrii on 8/14/14.
 */
public class Music extends MyActivity{

    private static MediaPlayer mPlayer = null;


    /** Stop old song and start new one */
    public static void play(Context context, int resource) {
        stop(context);

        // Start music only if not disabled in preferences
        if (Prefs.getMusic(context)) {
            mPlayer = MediaPlayer.create(context, R.raw.guitarmainfon);
            mPlayer.setLooping(true);
            mPlayer.start();
        }
    }

    /** Stop the music */
    public static void stop(Context context) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}



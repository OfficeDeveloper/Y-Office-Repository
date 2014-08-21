package com.office.officegame;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


/**
 * Created by Andrii on 8/18/14.
 */
public class MyService extends Service {
    public MediaPlayer     mPlayer;
    private boolean boolMusicVolumeCheckKey = true;

    public static int backgroundMusicKey = 1;
    public static boolean boolMusicCheck = true;
    public static boolean boolSoundTileCheck = true;

    public void onCreate() {
        mPlayer = MediaPlayer.create(this, R.raw.guitarmainfon);
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        startMyMediaPlayer();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        stopMyMediaPlayer();
        super.onDestroy();

    }
    public IBinder onBind(Intent intent) {    //default
        return null;
    }

    private void startMyMediaPlayer() {
            mPlayer.start();
            mPlayer.setLooping(true);
    }

    private void stopMyMediaPlayer() {
        if (boolMusicVolumeCheckKey == true) {
            mPlayer.setVolume(0,0);
            boolMusicVolumeCheckKey = false;
        }
        else {
            mPlayer.setVolume(1,1);
            boolMusicVolumeCheckKey = true;
        }



    }


}

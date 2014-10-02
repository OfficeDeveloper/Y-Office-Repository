package com.office.officegame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * @author Gavlovych Maksym (reverff@gmail.com)
 * @author Yakubenko Andrii (ayakubenko92@gmail.com)
 *         2014(c)
 */
public class SplashScreen extends Activity {
    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Splash screen view
        setContentView(R.layout.splash);

        ImageView splashImageView = (ImageView) findViewById(R.id.SplashImageView);
        splashImageView.setBackgroundResource(R.drawable.load_animation);
        final AnimationDrawable frameAnimation = (AnimationDrawable) splashImageView.getBackground();
        splashImageView.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        // The thread to wait for splash screen events
        mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        // Wait given period of time or exit on touch
                        wait(2500);
                    }
                } catch (InterruptedException ex) {
                    System.out.println("interrupted");
                }

                finish();

                // Run next activity
                Intent intent = new Intent();
                intent.setClass(SplashScreen.this, Main.class);
                startActivity(intent);
                onStop();
            }
        };

        mSplashThread.start();
    }

    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN)
            //noinspection SynchronizeOnNonFinalField
            synchronized (mSplashThread) {
                mSplashThread.notifyAll();
            }
        return true;
    }
}
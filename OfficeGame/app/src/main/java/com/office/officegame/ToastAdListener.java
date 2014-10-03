package com.office.officegame;

import android.content.Context;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

/**
 * @author Gavlovich Maksim (reverff@gmail.com)
 *         2014(c)
 */
public class ToastAdListener extends AdListener {
    private Context mContext;

    public ToastAdListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onAdLoaded() {
        Toast.makeText(mContext, "onAdLoaded()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdFailedToLoad(int errorCode) {
        String errorReason = "";
        switch(errorCode) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                errorReason = "Internal error";
                break;
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                errorReason = "Invalid request";
                break;
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                errorReason = "Network Error";
                break;
            case AdRequest.ERROR_CODE_NO_FILL:
                errorReason = "No fill";
                break;
        }
        Toast.makeText(mContext, String.format("onAdFailedToLoad(%s)", errorReason),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdOpened() {
        Toast.makeText(mContext, "onAdOpened()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClosed() {
        Toast.makeText(mContext, "onAdClosed()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdLeftApplication() {
        Toast.makeText(mContext, "onAdLeftApplication()", Toast.LENGTH_SHORT).show();
    }
}


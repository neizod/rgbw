package com.neizod.rgbw;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;


public class FullscreenActivity extends Activity {
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int[] COLOR_LIST = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.WHITE};
    private static int iColor = 0;
    private FrameLayout mScreen;
    private Handler mHideHandler = new Handler();
    private Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hideSystemUi();
        }
    };

    private void hideSystemUi() {
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    private void nextBackgroundColor() {
        iColor = (iColor + 1) % COLOR_LIST.length;
        mScreen.setBackgroundColor(COLOR_LIST[iColor]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        mScreen = (FrameLayout) findViewById(R.id.fullscreen_root);
        mScreen.setBackgroundColor(COLOR_LIST[iColor]);

        hideSystemUi();
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(
            new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if (visibility == 0) {
                        mHideHandler.postDelayed(mHideRunnable, AUTO_HIDE_DELAY_MILLIS);
                    }
                }
            }
        );

        mScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBackgroundColor();
            }
        });
    }
}

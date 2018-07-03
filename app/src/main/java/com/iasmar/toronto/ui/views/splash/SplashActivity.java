package com.iasmar.toronto.ui.views.splash;

import android.content.Intent;
import android.os.Bundle;

import com.iasmar.toronto.R;
import com.iasmar.toronto.ui.views.base.BaseActivity;
import com.iasmar.toronto.ui.views.main.MainActivity;


/**
 * Created by Asmar on 8/06/2018.
 * <p>
 * The splash screen  will appear while app is launching.
 * @author Asmar
 * @version 1
 * @see BaseActivity
 * @since 1.0
 */
public class SplashActivity extends BaseActivity {

    /**
     * This method called after the setContentView(int) in the base class.
     *
     * @param savedInstanceState A mapping from String keys to various parcelable values.
     * @param intent             intent that started this activity.
     */
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        // Start MainActivity
        startActivity(MainActivity.newInstance(SplashActivity.this));
        // close splash activity
        finish();
    }

    /**
     * Get the attempt content view.
     * To be used by child activities
     *
     * @return layoutResID Resource ID to be inflated.
     */
    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

}

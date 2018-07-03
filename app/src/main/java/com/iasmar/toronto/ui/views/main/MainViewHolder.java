package com.iasmar.toronto.ui.views.main;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.iasmar.toronto.R;
import com.iasmar.toronto.ui.views.base.BaseActivity;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Initialize all the UI components to the {@link MainActivity} and control the UI components.
 *
 * @author Asmar
 * @version 1
 * @see MainActivity
 * @since 1.0
 */
class MainViewHolder {

    // The tool bar.
    Toolbar toolbar;

    // The app bar.
    AppBarLayout appBar;

    // The base activity.
    private BaseActivity activity;


    /**
     * The constructor purpose is to initialize all the UI components.
     *
     * @param activity the view that will be used to get the UI components.
     */
    MainViewHolder(BaseActivity activity) {
        activity = requireNonNull(activity, "activity cannot be null");
        this.activity = activity;
        appBar = activity.findViewById(R.id.appBar);
        toolbar = activity.findViewById(R.id.toolbar);

    }

}

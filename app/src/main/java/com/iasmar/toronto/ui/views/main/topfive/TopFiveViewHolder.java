package com.iasmar.toronto.ui.views.main.topfive;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.iasmar.toronto.R;
import com.iasmar.toronto.ui.views.custom.recyclerview.CustomSwipeRefreshLayout;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Initialize all the UI components to the {@link TopFiveFragment} and control the UI components.
 *
 * @author Asmar
 * @version 1
 * @see TopFiveFragment
 * @see CustomSwipeRefreshLayout
 * @since 1.0
 */
class TopFiveViewHolder {


    /**
     * The constructor purpose is to initialize all the UI components.
     *
     * @param view the view that will be used to get the UI components.
     */
    TopFiveViewHolder(View view) {
        view = requireNonNull(view, "view cannot be null");

    }


}

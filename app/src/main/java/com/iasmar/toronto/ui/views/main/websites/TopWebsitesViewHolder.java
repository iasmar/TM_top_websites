package com.iasmar.toronto.ui.views.main.websites;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iasmar.toronto.R;
import com.iasmar.toronto.data.modules.WebSite;
import com.iasmar.toronto.data.modules.ModulesHolder;
import com.iasmar.toronto.ui.views.custom.recyclerview.CustomSwipeRefreshLayout;
import com.iasmar.toronto.ui.views.custom.recyclerview.RecyclerViewHelper;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;
import static com.iasmar.toronto.util.ViewUtils.setText;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Initialize all the UI components to the {@link TopWebsitesFragment} and control the UI components.
 *
 * @author Asmar
 * @version 1
 * @see TopWebsitesFragment
 * @see CustomSwipeRefreshLayout * @since 1.0
 */
class TopWebsitesViewHolder {
    final LinearLayout headerLl;

    final CustomSwipeRefreshLayout swipeRefresh;
    private final RecyclerView categRecv;

    TextView startDateTxv;
    TextView endDateTxv;
    Button filterBut;
    Button allBut;
    Button topFiveBut;
    Button sortBut;

    /**
     * The constructor purpose is to initialize all the UI components.
     *
     * @param view the view that will be used to get the UI components.
     */
    TopWebsitesViewHolder(View view) {
        view = requireNonNull(view, "view cannot be null");
        headerLl = view.findViewById(R.id.frg_top_websites_header_ll);
        swipeRefresh = view.findViewById(R.id.frg_top_websites_swipe_refresh);
        categRecv = view.findViewById(R.id.frg_top_websites_recv);
        startDateTxv = view.findViewById(R.id.frg_top_websites_start_date_txv);
        endDateTxv = view.findViewById(R.id.frg_top_websites_end_date_txv);
        filterBut = view.findViewById(R.id.frg_top_websites_filter_but);
        allBut = view.findViewById(R.id.frg_top_websites_all_but);
        topFiveBut = view.findViewById(R.id.frg_top_websites_top_five_but);
        sortBut = view.findViewById(R.id.frg_top_websites_sort_but);

    }

    public void setStartDate(String startDate) {
        setText(startDateTxv, startDate);

    }

    public void setEndDate(String endDate) {
        setText(endDateTxv, endDate);

    }

    /**
     * Causes the Runnable to be added to the message queue, then set refreshing.
     *
     * @param refreshing Whether or not the view should show refresh progress.
     */
    void swipeRefreshPost(boolean refreshing) {
        swipeRefresh.post(() -> swipeRefresh.setRefreshing(refreshing));

    }

    /**
     * Set up progress indicator.
     * Set the scrolling view in the {@link SwipeRefreshLayout}.
     **/
    void setupLoadingView() {
        // Set up progress indicator
        swipeRefresh.setColorSchemeColors();
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefresh.setScrollUpChild(categRecv);
    }


    /**
     * Set up RecyclerView.
     *
     * @param websitesAdapter The websites adapter.
     * @return New recycler view helper.
     */
    RecyclerViewHelper<TopWebsitesAdapterViewHolder, ModulesHolder<WebSite>> setupRecyclerView(TopWebsitesAdapter websitesAdapter) {
        return new RecyclerViewHelper<>(categRecv, websitesAdapter, 1);
    }

}

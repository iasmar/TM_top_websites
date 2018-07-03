package com.iasmar.toronto.ui.views.main.topfive;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.iasmar.toronto.Injection;
import com.iasmar.toronto.R;
import com.iasmar.toronto.data.modules.ModulesHolder;
import com.iasmar.toronto.data.modules.WebSite;
import com.iasmar.toronto.ui.views.base.BaseFragment;
import com.iasmar.toronto.ui.views.base.IBasePresenter;
import com.iasmar.toronto.util.ViewUtils;

import java.util.ArrayList;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 *
 * @author Asmar
 * @version 1
 * @see BaseFragment
 * @see ModulesHolder
 * @see WebSite
 * @see Injection
 * @see TopFivePresenter
 * @see TopFiveContract.View
 * @see TopFiveViewHolder
 * @since 1.0
 */
public class TopFiveFragment extends BaseFragment implements TopFiveContract.View, SwipeRefreshLayout.OnRefreshListener {


    //*********************************** Public static fields ***********************************//

    //Unique tag name for the fragment, to later retrieve the.
    public static final String FRAGMENT_TAG = "TopFiveFragment";

    /**
     * Get new instance of topFives fragment.
     *
     * @return new instance of topFives fragment.
     */
    public static TopFiveFragment newInstance() {
        return new TopFiveFragment();
    }

    //*********************************** private static fields ***********************************//
    //TODO refactoring
    public static final String forceUpdateKey = "forceUpdate";


    //*********************************** private fields ***********************************//
    // The context.
    private Context context;
    // The presenter.
    private TopFiveContract.Presenter presenter;
    //    forceUpdate force update means get the remote server.
    private boolean forceUpdate = true;

    // The view holder.
    private TopFiveViewHolder viewHolder;

    // topFives holder.
    private ModulesHolder<WebSite> topFivesHolder = new ModulesHolder<>();


    //*********************************** Constructors ***********************************//


    //*********************************** Override super methods ***********************************//

    /**
     * Save current View's state.
     *
     * @param outState A mapping from String keys to various {@link Parcelable} values.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //TODO add  Restore View's state
        super.onSaveInstanceState(outState);
        outState.putBoolean(forceUpdateKey, false);
    }


    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally.
     */
    @Override
    public void onResume() {
        super.onResume();
        // Network reload will be forced on first load.
        presenter.loadTopFives(forceUpdate);

    }

    /**
     * Called when the Fragment is no longer resumed.
     */
    @Override
    public void onPause() {
        super.onPause();
    }


    //*********************************** Override base methods ***********************************//


    /**
     * Called immediately after {@link #onViewCreated(View, Bundle)}.
     *
     * @param view               The current View.
     * @param savedInstanceState A mapping from String keys to various {@link Parcelable} values.
     */
    @Override
    protected void onViewReady(View view, @Nullable Bundle savedInstanceState) {
        super.onViewReady(view, savedInstanceState);
        if (savedInstanceState != null) {
            forceUpdate = savedInstanceState.getBoolean(forceUpdateKey);
        }

    }

    /**
     * The presenter won't update the view unless it's active.
     *
     * @return true if the fragment is currently added to its activity.
     */
    @VisibleForTesting
    @Override
    public boolean isActive() {
        return isAdded();
    }

    /**
     * Initialize the view after {@link #onViewCreated(View, Bundle)} being called.
     *
     * @param view The current view.
     */
    @Override
    protected void intiView(View view) {
        // Set up topFives view.
        setupTopFivesView(view);



    }



    /**
     * Get the current presenter.
     *
     * @return The current presenter.
     */
    @Override
    protected IBasePresenter getPresenter() {
        return new TopFivePresenter(
                Injection.provideWebsitesRepository(getTheContext()),
                this,
                Injection.provideSchedulerProvider());
    }

    /**
     * Get the current content view res layout id.
     *
     * @return The current content view res layout id.
     */
    @Override
    protected int getContentView() {
        return R.layout.fragment_top_five;
    }


    //*********************************** presenter implementations ***********************************//


    /**
     * Called when the presenter is successfully created this method will be called.
     * Set the presenter.
     *
     * @param presenter the ProfilePresenter
     */
    @Override
    public void setPresenter(TopFiveContract.Presenter presenter) {
        this.presenter = requireNonNull(presenter, "presenter cannot be null");
    }

    /**
     * Show loading or hide loading to the user.
     *
     * @param active enable or disable the loading view_indicator.
     */
    @Override
    public void setLoading(boolean active) {
        if(active) {
            resetFragmentView();
        }

    }


    /**
     * Called when there are some topFives found.
     *
     * @param topFives the list of topFives.
     * @see ModulesHolder
     * @see WebSite
     */
    @Override
    public void showTopFives(ModulesHolder<WebSite> topFives) {
        this.topFivesHolder = topFives;

    }

    /**
     * Called if there is no topFives.
     */
    @Override
    public void showNoTopFives() {
        resetFragmentView();
        Log.d("WTF", "showNoData");

    }
    /**
     * reset the fragment view means reset fragment as new.
     */
   protected void resetFragmentView(){
       topFivesHolder.clear();
    }

    /**
     * If any kind of error appears while loading.
     *
     * @param error String res id.
     */
    @Override
    public void showError(@StringRes int error) {
        Log.d("WTF", "showLoadingTopFivesError " + ViewUtils.getString(getTheContext(), error));
        resetFragmentView();
    }


    //*********************************** other implementations ***********************************//


    /**
     * Called when the user Swipe down.
     * Refresh all topFives with force update.
     *
     * @see SwipeRefreshLayout.OnRefreshListener
     **/
    @Override
    public void onRefresh() {
        presenter.loadTopFives(true);
    }


    //*********************************** Private methods ***********************************//
    /**
     * Get the context.
     *
     * @return context.
     */
    private Context getTheContext() {
        if (context == null) {
            context = getContext();
        }

        return context;
    }


    /**
     * Setup topFives view holder.
     *
     * @param view the current view.
     * @see TopFiveViewHolder
     */
    private void setupTopFivesView(View view) {
        // Set up topFives view.
        viewHolder = new TopFiveViewHolder(view);
    }



}

package com.iasmar.toronto.ui.views.main.websites;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iasmar.toronto.Injection;
import com.iasmar.toronto.R;
import com.iasmar.toronto.data.modules.WebSite;
import com.iasmar.toronto.data.modules.ModulesHolder;
import com.iasmar.toronto.ui.views.base.BaseFragment;
import com.iasmar.toronto.ui.views.base.IBasePresenter;
import com.iasmar.toronto.ui.views.custom.recyclerview.RecyclerViewHelper;
import com.iasmar.toronto.ui.views.main.MainActivity;
import com.iasmar.toronto.ui.views.main.topfive.TopFiveFragment;
import com.iasmar.toronto.util.ObjectHelper;
import com.iasmar.toronto.util.ViewUtils;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Display a list  of {@link WebSite}s. User can choose to view all or active websites.
 *
 * @author Asmar
 * @version 1
 * @see BaseFragment
 * @see ModulesHolder
 * @see WebSite
 * @see Injection
 * @see TopWebsitesPresenter
 * @see TopWebsitesContract.View
 * @see TopWebsitesViewHolder
 * @since 1.0
 */
public class TopWebsitesFragment extends BaseFragment implements TopWebsitesContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    //*********************************** Public static fields ***********************************//

    //Unique tag name for the fragment, to later retrieve the.
    public static final String FRAGMENT_TAG = "TopWebsitesFragment";

    /**
     * Get new instance of websites fragment.
     *
     * @return new instance of websites fragment.
     */
    public static TopWebsitesFragment newInstance() {
        return new TopWebsitesFragment();
    }

    //*********************************** private static fields ***********************************//
    //TODO refactoring
    public static final String forceUpdateKey = "forceUpdate";


    //*********************************** private fields ***********************************//
    // The context.
    private Context context;


    private String currentSorting;

    // The presenter.
    private TopWebsitesContract.Presenter presenter;
    //    forceUpdate force update means get the remote server.
    private boolean forceUpdate = true;

    // The view holder.
    private TopWebsitesViewHolder viewHolder;

    // websites holder.
    private ModulesHolder<WebSite> websitesHolder = new ModulesHolder<>();

    //The recycler view helper.
    private RecyclerViewHelper<TopWebsitesAdapterViewHolder, ModulesHolder<WebSite>> recyclerViewHelper;

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
        presenter.loadWebsites(forceUpdate, null);

    }

    /**
     * Called when the Fragment is no longer resumed.
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frg_top_websites_top_five_but:
                ((MainActivity) ObjectHelper.requireNonNull(getTheContext(), "context cannot be null")).proceedItem(TopFiveFragment.FRAGMENT_TAG);

                break;
            case R.id.frg_top_websites_sort_but:
//                 ascending  descending
                if (currentSorting == null || currentSorting.equals("descending")) {
                    currentSorting = "ascending";
                } else {
                    currentSorting = "descending";

                }


                presenter.loadWebsites(false, "ascending");


                break;
            case R.id.frg_top_websites_filter_but:

                break;
            case R.id.all:

                break;

        }
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
        // Set up websites view.
        setupWebsitesView(view);
        // Set up loading view.
        setupLoadingView();
        // Set up recycler view.
        setupRecyclerView();

        setUpButtons();


    }


    /**
     * Get the current presenter.
     *
     * @return The current presenter.
     */
    @Override
    protected IBasePresenter getPresenter() {
        return new TopWebsitesPresenter(
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
        return R.layout.fragment_top_websites;
    }


    //*********************************** presenter implementations ***********************************//


    /**
     * Called when the presenter is successfully created this method will be called.
     * Set the presenter.
     *
     * @param presenter the ProfilePresenter
     */
    @Override
    public void setPresenter(TopWebsitesContract.Presenter presenter) {
        this.presenter = requireNonNull(presenter, "presenter cannot be null");
    }

    /**
     * Show loading or hide loading to the user.
     *
     * @param active enable or disable the loading view_indicator.
     */
    @Override
    public void setLoading(boolean active) {
        if (active) {
            resetFragmentView();
            viewHolder.headerLl.setVisibility(View.GONE);
        }

        viewHolder.swipeRefreshPost(active);

    }

    /**
     * Called when there are some websites found.
     *
     * @param websites the list of websites.
     * @see ModulesHolder
     * @see WebSite
     */
    @Override
    public void showWebsites(ModulesHolder<WebSite> websites) {
        boolean animation = true;
        if (websitesHolder.getSize() > 0) {
            animation = false;
        }
        this.websitesHolder = websites;
        recyclerViewHelper.replaceData(websitesHolder, animation);
        viewHolder.headerLl.setVisibility(View.VISIBLE);

    }

    /**
     * Called if there is no websites.
     */
    @Override
    public void showNoWebsites() {
        resetFragmentView();
        Log.d("WTF", "showNoData");
        Toast.makeText(getTheContext(),"NO data",Toast.LENGTH_LONG).show();

    }

    /**
     * reset the fragment view means reset fragment as new.
     */
    protected void resetFragmentView() {
        websitesHolder.clear();
        recyclerViewHelper.replaceData(websitesHolder, false);
        viewHolder.headerLl.setVisibility(View.GONE);
    }

    /**
     * If any kind of error appears while loading.
     *
     * @param error String res id.
     */
    @Override
    public void showError(@StringRes int error) {
        resetFragmentView();
        Log.d("WTF", "showLoadingWebsitesError " + ViewUtils.getString(getTheContext(), error));
        viewHolder.headerLl.setVisibility(View.GONE);


        Toast.makeText(getTheContext(),ViewUtils.getString(getTheContext(), error),Toast.LENGTH_LONG).show();

    }


    //*********************************** other implementations ***********************************//


    /**
     * Called when the user Swipe down.
     * Refresh all websites with force update.
     *
     * @see SwipeRefreshLayout.OnRefreshListener
     **/
    @Override
    public void onRefresh() {
        presenter.loadWebsites(true, currentSorting);
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
     * Setup websites view holder.
     *
     * @param view the current view.
     * @see TopWebsitesViewHolder
     */
    private void setupWebsitesView(View view) {
        // Set up websites view.
        viewHolder = new TopWebsitesViewHolder(view);
    }


    /**
     * Set up progress indicator.
     * Set the scrolling view in the {@link SwipeRefreshLayout}.
     * Set the refresh listener in the  SwipeRefreshLayout.
     **/
    private void setupLoadingView() {
        viewHolder.setupLoadingView();
        // Set the refresh listener in the custom SwipeRefreshLayout.
        viewHolder.swipeRefresh.setOnRefreshListener(this);
    }


    /**
     * Set up RecyclerView.
     **/
    private void setupRecyclerView() {
        TopWebsitesAdapter websitesAdapter = new TopWebsitesAdapter(websitesHolder);
        recyclerViewHelper = viewHolder.setupRecyclerView(websitesAdapter);
    }

    private void setUpButtons() {
        viewHolder.topFiveBut.setOnClickListener(this);
        viewHolder.filterBut.setOnClickListener(this);
        viewHolder.allBut.setOnClickListener(this);
        viewHolder.sortBut.setOnClickListener(this);
    }


}

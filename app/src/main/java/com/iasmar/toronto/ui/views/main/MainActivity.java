package com.iasmar.toronto.ui.views.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.iasmar.toronto.Injection;
import com.iasmar.toronto.R;
import com.iasmar.toronto.configuration.Constant;
import com.iasmar.toronto.ui.views.base.BaseActivity;
import com.iasmar.toronto.ui.views.base.BaseFragment;
import com.iasmar.toronto.ui.views.base.IBasePresenter;
import com.iasmar.toronto.ui.views.main.topfive.TopFiveFragment;
import com.iasmar.toronto.ui.views.main.websites.TopWebsitesFragment;
import com.iasmar.toronto.util.ObjectHelper;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Base class for all the main activities.
 *
 * @author Asmar
 * @version 1
 * @see BaseActivity
 * @since 1.0
 */
public class MainActivity extends BaseActivity implements MainContract.View {

    // The presenter.
    private MainContract.Presenter presenter;
    private MainViewHolder mainViewHolder;

    /**
     * Get new instance of websites fragment.
     *
     * @param baseActivity A Context of the application package implementing
     *                     this class.
     * @return new instance of websites fragment.
     */
    public static Intent newInstance(BaseActivity baseActivity) {
        return new Intent(baseActivity, MainActivity.class);
    }

    /**
     * This method called after the setContentView(int) in the base class.
     *
     * @param savedInstanceState A mapping from String keys to various parcelable values.
     * @param intent             intent that started this activity.
     */
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        if (savedInstanceState == null) {
            mainViewHolder = new MainViewHolder(this);

            setSupportActionBar(mainViewHolder.toolbar);


            // proceed to websites fragment.
            proceedItem(TopWebsitesFragment.FRAGMENT_TAG);

            getPresenter();

        }
    }

    /**
     * Get the attempt content view.
     * To be used by child activities
     *
     * @return layoutResID Resource ID to be inflated.
     */
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally.
     */
    @Override
    public void onResume() {
        super.onResume();
        // Network reload will be forced on first load.

    }

    /**
     * This hook is called whenever an item in  options menu is selected.
     *
     * @param item The item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * This method is to initialize nre Instance of all fragment and add a tag to each one.
     *
     * @param tag the item tag.
     * @return whether it successfully replaced or not.
     */
    public boolean proceedItem(String tag) {
        return proceedItem(tag, null);
    }

    /**
     * This method is to initialize nre Instance of all fragment and add a tag to each one.
     *
     * @param tag the item tag.
     * @return whether it successfully replaced or not.
     */
    public boolean proceedItem(String tag, Bundle bundle) {
        BaseFragment fragment = null;

        switch (tag) {
            case TopWebsitesFragment.FRAGMENT_TAG:
                fragment = TopWebsitesFragment.newInstance();

                break;
            case TopFiveFragment.FRAGMENT_TAG:
                fragment = TopFiveFragment.newInstance();

                break;

        }


        return fragment != null && replaceFragment(fragment, tag, bundle);
    }

    /**
     * Tis method will update the UI base on the selected fragment.
     *
     * @param tag A tag name for the fragment.
     */
    private void onFragmentChange(String tag) {
        int title = Constant.CUSTOM_INVALID_INT;
        ActionBar supportActionBar = getSupportActionBar();
        ObjectHelper.requireNonNull(supportActionBar,"supportActionBar cannot be null");

        switch (tag) {
            case TopWebsitesFragment.FRAGMENT_TAG:
                title = R.string.websites_ranking;
                supportActionBar.setDisplayHomeAsUpEnabled(false);
                supportActionBar.setDisplayShowHomeEnabled(false);
                break;
                case TopFiveFragment.FRAGMENT_TAG:
                title = R.string.topFives;
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setDisplayShowHomeEnabled(true);
                break;

        }

        if (title != Constant.CUSTOM_INVALID_INT) {
            setTitle(title);
        }

    }



    /**
     * Replace the current view to the new one, and update the UI.
     *
     * @param fragment The fragment.
     * @param tag      Optional tag name for the fragment, to later retrieve the
     * @param bundle   the arguments supplied when the fragment was instantiated.
     * @return whether it successfully replaced or not.
     */
    private boolean replaceFragment(BaseFragment fragment, String tag, @Nullable Bundle bundle) {
        onFragmentChange(tag);
        return replaceFragment(fragment, R.id.frg_main_container, tag, bundle);
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key and tool bar back button.
     * <p>
     * back to the previous fragment if possible
     * else finish the activity.
     */
    @Override
    public void onBackPressed() {

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStackImmediate();

                BaseFragment currentFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.frg_main_container);
                if (currentFragment != null) {
                    String tag = currentFragment.getTag();
                    onFragmentChange(ObjectHelper.requireNonNull(tag,"tag cannot be null"));

                } else {
                    finish();
                }
            } else {
                finish();
            }
    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = requireNonNull(presenter, "presenter cannot be null");

    }

    @Override
    public void setLoading(boolean active) {

    }

    @Override
    public void showError(int error) {

    }


    @Override
    public boolean isActive() {
        return true;
    }



    protected IBasePresenter getPresenter() {
        return new MainPresenter(
                Injection.provideMainRepository(this),
                this,
                Injection.provideSchedulerProvider());


    }



}



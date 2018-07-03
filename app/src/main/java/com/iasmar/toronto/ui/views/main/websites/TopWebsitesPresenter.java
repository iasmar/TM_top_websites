package com.iasmar.toronto.ui.views.main.websites;

import android.support.annotation.NonNull;

import com.iasmar.toronto.data.modules.WebSite;
import com.iasmar.toronto.data.modules.ModulesHolder;
import com.iasmar.toronto.data.repositories.websites.WebsitesDataSource;
import com.iasmar.toronto.data.repositories.websites.WebsitesRepository;
import com.iasmar.toronto.ui.views.base.BasePresenter;
import com.iasmar.toronto.util.schedulers.BaseSchedulerProvider;
import com.iasmar.toronto.util.testing.EspressoIdlingResource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Listens to user actions from the UI ({@link TopWebsitesFragment}), retrieves the data and updates the
 * UI as required. *
 *
 * @param <V> The current view.
 * @author Asmar
 * @version 1
 * @see TopWebsitesFragment
 * @see TopWebsitesContract.View
 * @see TopWebsitesContract.Presenter
 * @see BaseSchedulerProvider
 * @see WebsitesDataSource
 * @see WebsitesRepository
 * @see WebSite
 * @see CompositeDisposable
 * @since 1.0
 */

public class TopWebsitesPresenter<V extends TopWebsitesContract.View> extends BasePresenter<V>
        implements TopWebsitesContract.Presenter<V> {
    // The websites repository.
    @NonNull
    private WebsitesRepository websitesRepository;

    // The websites view.
    @NonNull
    private final TopWebsitesContract.View websitesView;

    // The scheduler provider.
    @NonNull
    private BaseSchedulerProvider schedulerProvider;


    // Is it still loading.
    private boolean isStillLoading;

    /**1
     * The constructor purpose is to
     * <p>
     * Initialize the inject websites repository,
     * websites view and the inject scheduler provider.
     * Initialize the composite disposable for espresso testing library.
     * Set the presenter back to the view.
     *
     * @param websitesRepository The inject of websites repository.
     * @param websiteView         The website view.
     * @param schedulerProvider    The  inject of scheduler provider.
     * @see WebsitesRepository
     * @see TopWebsitesContract.View
     * @see BaseSchedulerProvider
     */
    TopWebsitesPresenter(@NonNull WebsitesRepository websitesRepository,
                         @NonNull TopWebsitesContract.View websiteView,
                         @NonNull BaseSchedulerProvider schedulerProvider) {
        super(websitesRepository, schedulerProvider);
        onAttach(websiteView);
        this.websitesView = websiteView;
        this.schedulerProvider = getBaseSchedulerProvider();
        this.websitesRepository = (WebsitesRepository) getRepository();

        websitesView.setPresenter(this);

    }

    /**
     * This method should be called on onResume().
     */
    @Override
    public void subscribe() {
        super.subscribe();
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after onDestroy().
     */
    @Override
    public void onDetach() {
        super.onDetach();
        WebsitesRepository.destroyInstance();
    }

    /**
     * Load all websites and filter them and update the UI.
     *
     * @param forceUpdate Pass in true to refresh the data in the {@link WebsitesDataSource}.
     * @see WebsitesRepository
     * @see WebSite
     * @see BaseSchedulerProvider
     */
    @Override
    public void loadWebsites(final boolean forceUpdate, String sort) {

        if(isStillLoading){
            return;
        }
        isStillLoading = true;

        // Show loading to the user.
        setLoading(true);
        // Use force update to clear the cache
        setCacheDirty(forceUpdate);

        // App is busy until further notice.
        espressoIncrement();

        // Clear all disposables.
        clearDisposables();

        //Create the Disposable.
        // show loading error to the user.
        Disposable disposable = websitesRepository
                // Get the data from the repository.
                .getWebsites(sort)
                .flatMap(Flowable::fromIterable)
                .toList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doFinally(() -> {
                    espressoDecrement();
                    isStillLoading = false;
                    // hide loading to the user.
                    setLoading(false);

                })
                .subscribe(
                        // onNext
                        //valid websites.
                        this::processWebsites,
                        // onError
                        // hide loading to the user.
                        this::handleError);
        // Add disposable
        addDisposable(disposable);
    }

    /**
     * Show loading or hide loading from the UI.
     *
     * @param active true to show or false to hide.
     * @see TopWebsitesContract.View
     */
    private void setLoading(boolean active) {
        websitesView.setLoading(active);
    }


    /**
     * Force update data in the next call by making the cache dirty true.
     * @param cacheIsDirty true to force update.
     * @see WebsitesRepository
     */
    private void setCacheDirty(boolean cacheIsDirty) {
        websitesRepository.setCacheDirty(cacheIsDirty);
    }

    /**
     * The network request might be handled in a different thread so make sure Espresso knows
     * that the app is busy until the response is handled.
     * App is busy until further notice.
     */
    private void espressoIncrement() {
        EspressoIdlingResource.increment();
    }

    /**
     * Free the App.
     * Set app as idle.
     */
    private void espressoDecrement() {
        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
            EspressoIdlingResource.decrement();
        }
    }


    /**
     * Handel valid websites.
     *
     * @param websites list of the websites.
     * @see WebSite
     * @see TopWebsitesContract.View
     */
    private void processWebsites(@NonNull List<WebSite> websites) {
        if (!websites.isEmpty()) {
            ModulesHolder<WebSite> websitesHolder = new ModulesHolder<>();
            for (WebSite website : websites) {
                if (!website.isEmpty()) websitesHolder.addItem(website);
            }
            // Show websites
            if (!websitesHolder.isEmpty()) {
                websitesView.showWebsites(websitesHolder);
            } else {
                // Show a message indicating there are no websites.
                processEmptyWebsites();
            }
        } else {
            // Show a message indicating there are no websites.
            processEmptyWebsites();
        }

    }


    /**
     * Handel empty websites.
     *
     * @see TopWebsitesContract.View
     */
    private void processEmptyWebsites() {
        websitesView.showNoWebsites();
    }


}

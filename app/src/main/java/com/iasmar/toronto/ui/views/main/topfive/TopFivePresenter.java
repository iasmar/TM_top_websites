package com.iasmar.toronto.ui.views.main.topfive;

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
 * Listens to user actions from the UI ({@link TopFiveFragment}), retrieves the data and updates the
 * UI as required. *
 *
 * @param <V> The current view.
 * @author Asmar
 * @version 1
 * @see TopFiveFragment
 * @see TopFiveContract.View
 * @see TopFiveContract.Presenter
 * @see BaseSchedulerProvider
 * @see WebsitesDataSource
 * @see WebsitesRepository
 * @see WebSite
 * @see CompositeDisposable
 * @since 1.0
 */

public class TopFivePresenter<V extends TopFiveContract.View> extends BasePresenter<V>
        implements TopFiveContract.Presenter<V> {
    // The webSites repository.
    @NonNull
    private WebsitesRepository webSitesRepository;

    // The topFives view.
    @NonNull
    private final TopFiveContract.View topFivesView;

    // The topFivesr provider.
    @NonNull
    private BaseSchedulerProvider baseSchedulerProvider;


    // Is it still loading.
    private boolean isStillLoading;

    /**1
     * The constructor purpose is to
     * <p>
     * Initialize the inject webSites repository,
     * topFives view and the inject topFivesr provider.
     * Initialize the composite disposable for espresso testing library.
     * Set the presenter back to the view.
     *
     * @param webSitesRepository The inject of webSites repository.
     * @param topFivesView         The webSite view.
     * @param baseSchedulerProvider    The  inject of baseSchedulerProvider.
     * @see WebsitesRepository
     * @see TopFiveContract.View
     * @see BaseSchedulerProvider
     */
    TopFivePresenter(@NonNull WebsitesRepository webSitesRepository,
                     @NonNull TopFiveContract.View topFivesView,
                     @NonNull BaseSchedulerProvider baseSchedulerProvider) {
        super(webSitesRepository, baseSchedulerProvider);
        onAttach(topFivesView);
        this.topFivesView = topFivesView;
        this.baseSchedulerProvider = getBaseSchedulerProvider();
        this.webSitesRepository = (WebsitesRepository) getRepository();

        topFivesView.setPresenter(this);

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
     * Load all webSites and filter them and update the UI.
     *
     * @param forceUpdate Pass in true to refresh the data in the {@link WebsitesDataSource}.
     * @see WebsitesRepository
     * @see WebSite
     * @see BaseSchedulerProvider
     */
    @Override
    public void loadTopFives(final boolean forceUpdate) {


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
        Disposable disposable = webSitesRepository
                // Get the data from the repository.
                .getTopFives()
                .flatMap(Flowable::fromIterable)
                .toList()
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .doFinally(() -> {
                    espressoDecrement();
                    isStillLoading = false;
                    // hide loading to the user.
                    setLoading(false);

                })
                .subscribe(
                        // onNext
                        //valid webSites.
                        this::processWebSites,
                        // onError
                        // hide loading to the user.
                        this::handleError);
        // Add disposable
        addDisposable(disposable);
    }

    /**
     * Show loading or hide loading to the user.
     *
     * @param active true to show or false to hide.
     * @see TopFiveContract.View
     */
    private void setLoading(boolean active) {
        topFivesView.setLoading(active);
    }


    /**
     * Force update data in the next call by making the cache dirty true.
     * @param cacheIsDirty true to force update.
     * @see WebsitesRepository
     */
    private void setCacheDirty(boolean cacheIsDirty) {
        webSitesRepository.setCacheDirty(cacheIsDirty);
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
     * Handel valid topFives.
     *
     * @param topFives list of the topFives.
     * @see WebSite
     * @see TopFiveContract.View
     */
    private void processWebSites(@NonNull List<WebSite> topFives) {
        if (!topFives.isEmpty()) {
            ModulesHolder<WebSite> topFivesHolder = new ModulesHolder<>();
            for (WebSite webSite : topFives) {
                if (!webSite.isEmpty()) topFivesHolder.addItem(webSite);
            }
            // Show topFives
            if (!topFivesHolder.isEmpty()) {
                topFivesView.showTopFives(topFivesHolder);
            } else {
                // Show a message indicating there are no topFives.
                processEmptyWebSites();
            }
        } else {
            // Show a message indicating there are no topFives.
            processEmptyWebSites();
        }

    }


    /**
     * Handel empty topFives.
     *
     * @see TopFiveContract.View
     */
    private void processEmptyWebSites() {
     topFivesView.showNoTopFives();
    }

}

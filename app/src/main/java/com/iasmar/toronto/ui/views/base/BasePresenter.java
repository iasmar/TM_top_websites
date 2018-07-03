package com.iasmar.toronto.ui.views.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;

import com.iasmar.toronto.R;
import com.iasmar.toronto.data.modules.WebSite;
import com.iasmar.toronto.data.repositories.BaseRepository;
import com.iasmar.toronto.data.sources.remote.ApiError;
import com.iasmar.toronto.util.schedulers.BaseSchedulerProvider;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;

/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView(), It also handles errors.
 *
 * @param <V> The view.
 * @author Asmar
 * @version 1
 * @since 1.0
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    // The base repository.
    @NonNull
    private final BaseRepository repository;

    // The  view.
    private IBaseView view;

    @NonNull
    private final BaseSchedulerProvider baseSchedulerProvider;


    // The composite disposable.
    @NonNull
    private CompositeDisposable compositeDisposable;

    /**
     * The constructor purpose is to
     * <p>
     * Initialize the inject  repository,
     * Initialize the composite disposable for espresso testing library.
     * Set the presenter back to the view.
     *
     * @param repository        The inject of  repository.
     * @param baseSchedulerProvider The  inject of base scheduler provider.
     * @see BaseRepository
     * @see BaseSchedulerProvider
     */
    public BasePresenter(@NonNull BaseRepository repository,
                         @NonNull BaseSchedulerProvider baseSchedulerProvider) {
        this.repository = requireNonNull(repository, "repository cannot be null");
        this.baseSchedulerProvider = requireNonNull(baseSchedulerProvider, "topFivesrProvider cannot be null");
        compositeDisposable = new CompositeDisposable();
    }


    /**
     * This method should be called on onResume().
     */
    @Override
    public void subscribe() {

    }

    /**
     * This method should be called on onPause().
     * Clear all disposables.
     */
    @Override
    public void unsubscribe() {
        // Clear all disposables.
        clearDisposables();

    }

    /**
     * Called from the constructor.
     *
     * @param view The view.
     * @see IBaseView
     */

    @Override
    public void onAttach(IBaseView view) {
        this.view = view;

    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after onDestroy().
     */
    @Override
    public void onDetach() {
        view = null;

    }

    /**
     * Get the current view.
     *
     * @return The current view.
     * @see IBaseView
     */
    @Nullable
    public IBaseView getView() {
        return view;
    }

    /**
     * Get baseSchedulerProvider providers of {@link WebSite}s.
     *
     * @return baseSchedulerProvider .
     * @see BaseSchedulerProvider
     */
    @NonNull
    protected BaseSchedulerProvider getBaseSchedulerProvider() {
        return baseSchedulerProvider;
    }

    /**
     * Get the current repository.
     *
     * @return The current repository.
     * @see BaseRepository
     */
    @NonNull
    protected BaseRepository getRepository() {
        return repository;
    }

    /**
     * Clear all disposables.
     */
    protected void clearDisposables() {
        compositeDisposable.clear();
    }

    /**
     * Add disposable to CompositeDisposable.
     *
     * @param disposable the disposable that you attempt to add.
     * @see Disposable
     */
    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }


    /**
     * Show loading error to the user.
     *
     * @param error the throwable error.
     * @see ApiError
     */
    @Override
    public void handleError(Throwable error) {
        @StringRes
        int errorMessage;
        if (error instanceof ApiError) {
            ApiError apiError = (ApiError) error;

            switch (apiError.getErrorCode()) {
                case HttpsURLConnection.HTTP_UNAVAILABLE:
                    errorMessage = (R.string.error_api_service_unavailable);
                    break;
                //TODO handel other Errors.
                default:
                    errorMessage = (R.string.error_api_default);
                    break;
            }

        } else if (error instanceof IOException) {
            errorMessage = (R.string.connection_error);
        } else {
            errorMessage = (R.string.error_default);
            Log.d("WTF", "throwable " + error.getLocalizedMessage());
        }

        if (view != null) {
            view.showError(errorMessage);
        }

    }
}

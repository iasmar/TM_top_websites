package com.iasmar.toronto.data.sources.remote.websites;


import android.support.annotation.NonNull;

import com.iasmar.toronto.data.modules.WebSite;
import com.iasmar.toronto.data.repositories.websites.WebsitesDataSource;
import com.iasmar.toronto.data.repositories.websites.RemoteWebsitesDataSource;
import com.iasmar.toronto.data.sources.remote.BaseRemoteDataSource;
import com.iasmar.toronto.util.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.Flowable;

import static com.iasmar.toronto.configuration.ApiEndPoint.END_POINT_API_TOP_FIVE;
import static com.iasmar.toronto.configuration.ApiEndPoint.END_POINT_API_WEBSITES;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Implementation of the data source that will communicate with the server.
 *
 * @author Asmar
 * @author Asmar
 * @version 1
 * @see BaseRemoteDataSource
 * @see WebSite
 * @see WebsitesDataSource
 * @see RemoteWebsitesDataSource
 * @see BaseSchedulerProvider
 * @since 0.1.0
 */
public class WebsitesRemoteDataSource extends BaseRemoteDataSource<WebSite> implements RemoteWebsitesDataSource {

    // The instance of WebsitesRemoteDataSource.
    private static WebsitesRemoteDataSource INSTANCE;


    /**
     * Prevent direct instantiation.
     *
     * @param baseSchedulerProvider The  inject of topFivesr provider.
     */
    private WebsitesRemoteDataSource(@NonNull BaseSchedulerProvider baseSchedulerProvider) {
        super(baseSchedulerProvider);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param baseSchedulerProvider The inject of topFivesr provider.
     * @return the {@link WebsitesRemoteDataSource} instance
     */
    public static synchronized WebsitesRemoteDataSource getInstance(@NonNull BaseSchedulerProvider baseSchedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new WebsitesRemoteDataSource(baseSchedulerProvider);
        }
        return INSTANCE;
    }

    /**
     * TODO use this
     * Used to force {@link #getInstance(BaseSchedulerProvider)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * TODO delete this
     * Not required because the {@link } handles the logic of refreshing the
     * // help from all the available data sources.
     */
    @Override
    public void setCacheDirty(boolean cacheIsDirty) {
    }

    /**
     * Gets websites from the server.
     *
     * @return the List of {@link WebSite} module from server.
     */
    @Override
    public Flowable<List<WebSite>> getWebsites(String sort) {
        return getListData(WebSite[].class, END_POINT_API_WEBSITES);
    }

    @Override
    public Flowable<List<WebSite>> getTopFives() {
        return getListData(WebSite[].class, END_POINT_API_TOP_FIVE);
    }


}

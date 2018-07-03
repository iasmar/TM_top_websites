package com.iasmar.toronto.data.repositories.websites;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.iasmar.toronto.data.modules.WebSite;
import com.iasmar.toronto.data.repositories.BaseRepository;
import com.iasmar.toronto.util.GeneralUtils;
import com.iasmar.toronto.util.ObjectHelper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Concrete implementation to load websites from the data sources.
 * <p>
 * This implements a synchronisation for data
 * obtained from the server and cache data.
 *
 * @author Asmar
 * @version 1
 * @see WebSite
 * @see WebsitesDataSource
 * @since 0.1.0
 */
public class WebsitesRepository extends BaseRepository<WebSite, WebsitesDataSource> implements RemoteWebsitesDataSource {

    // The remote data source that will be based from the presenter.
    @NonNull
    private final RemoteWebsitesDataSource remoteDataSource;


    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    @Nullable
    Map<String, WebSite> cachedWebsites;


    /**
     * Marks the cache as invalid, to force an update the next time data is requested.
     * This variable has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    boolean cacheIsDirty = true;

    // The instance of WebsitesRepository.
    @Nullable
    private static WebsitesRepository INSTANCE = null;
    private String sort;

    /**
     * Prevent direct instantiation.
     *
     * @param remoteDataSource The remote data source.
     */
    private WebsitesRepository(@NonNull RemoteWebsitesDataSource remoteDataSource
    ) {
        this.remoteDataSource = requireNonNull(remoteDataSource, "remoteDataSource cannot be null");

    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param websitesRemoteDataSource The remote data source.
     * @return The {@link WebsitesRepository} instance.
     */
    public static synchronized WebsitesRepository getInstance(@NonNull RemoteWebsitesDataSource websitesRemoteDataSource
    ) {
        if (INSTANCE == null) {
            INSTANCE = new WebsitesRepository(websitesRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(RemoteWebsitesDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets data from cache or remote data source.
     *
     * @return the List of {@link WebSite} module from cache or remote.
     */
    public Flowable<List<WebSite>> getWebsites(String sort) {
        this.sort = sort;
        // Respond immediately with cache if available and not dirty.
        if (cachedWebsites != null && !cacheIsDirty) {
            return Flowable.fromIterable(cachedWebsites.values()).sorted(WebsitesRepository.this::sortItems).toList().toFlowable();
        } else if (cachedWebsites == null) {
            cachedWebsites = new LinkedHashMap<>();
        }
        // Get the data from remote and then save it into the cache.
        return getRemoteData();
    }

    @Override
    public Flowable<List<WebSite>> getTopFives() {
        return remoteDataSource
                .getTopFives()
                .flatMap(data -> Flowable.fromIterable(data)
                        .distinct()
                        .sorted(WebsitesRepository.this::sortItems)
                        .toList().toFlowable());
    }


    /**
     * Query the network if available and sort them, then store in the cache.
     *
     * @return the List of {@link WebSite} module from network.
     */
    private Flowable<List<WebSite>> getRemoteData() {
        return remoteDataSource
                .getWebsites(null)
                .flatMap(data -> Flowable.fromIterable(data)
                        .doOnNext(website -> {
                            ObjectHelper.requireNonNull(cachedWebsites, "cachedWebsites cannot be null").put(website.getId(), website);
                        })
                        .distinct()
                        .sorted(WebsitesRepository.this::sortItems)
                        .toList().toFlowable().doOnComplete(() -> cacheIsDirty = false));


    }

    /**
     * Force update data in the next call by making the cache dirty true.
     *
     * @param cacheIsDirty true to force update.
     */
    public void setCacheDirty(boolean cacheIsDirty) {
        this.cacheIsDirty = cacheIsDirty;
    }


    /**
     * Compares two {@code long} values numerically.
     *
     * @param o1 the first module to compare.
     * @param o2 the second module to compare.
     * @return the value of the comparison.
     */
    private int sortItems(WebSite o1, WebSite o2) {
        if (sort!= null && sort.equals("ascending")) {
            return -GeneralUtils.compare(o1.getSortBy(), o2.getSortBy());

        }

        return GeneralUtils.compare(o1.getSortBy(), o2.getSortBy());

    }


}
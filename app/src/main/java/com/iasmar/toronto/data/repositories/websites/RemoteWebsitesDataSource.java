package com.iasmar.toronto.data.repositories.websites;

import com.iasmar.toronto.data.modules.WebSite;
import com.iasmar.toronto.data.repositories.BaseDataSource;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Remote entry point for accessing websites data.
 *
 * @author Asmar
 * @version 1
 * @see WebSite
 * @see BaseDataSource
 * @see WebsitesDataSource
 * @since 0.1.0
 */
public interface RemoteWebsitesDataSource extends WebsitesDataSource {
    /**
     * Gets websites.
     *
     * @return the List of {@link WebSite} module.
     */
    Flowable<List<WebSite>> getWebsites(String sort);
    Flowable<List<WebSite>> getTopFives();

}
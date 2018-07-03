package com.iasmar.toronto.data.repositories;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * The base data source that will be used by all modules data source.
 *
 * @author Asmar
 * @version 1
 * @since 0.1.0
 */
public interface BaseDataSource {

    /**
     * Force update data in the next call by making the cache dirty true.
     * @param cacheIsDirty true to force update.
     */
    void setCacheDirty(boolean cacheIsDirty);
}

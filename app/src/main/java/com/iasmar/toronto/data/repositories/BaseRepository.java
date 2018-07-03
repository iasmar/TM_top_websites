package com.iasmar.toronto.data.repositories;


import com.iasmar.toronto.data.modules.BaseModule;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * The base data source that will be used by all sub repositories.
 * @param <T> {@link BaseModule] child.
 * @param <S> {@link BaseDataSource] child.
 * @author Asmar
 * @version 1
 * @since 0.1.0
 */

public class BaseRepository<T extends BaseModule, S extends BaseDataSource> {

}


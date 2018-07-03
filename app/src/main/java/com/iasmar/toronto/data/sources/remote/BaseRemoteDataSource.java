package com.iasmar.toronto.data.sources.remote;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.iasmar.toronto.data.modules.BaseModule;
import com.iasmar.toronto.util.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * The base of the data source that will communicate with the server.
 *
 * @param <T> {@link BaseModule] child.
 * @author Asmar
 * @version 1
 * @see BaseSchedulerProvider
 * @see BaseModule
 * @since 0.1.0
 */

public class BaseRemoteDataSource<T extends BaseModule> {

    // the topFivesr to perform subscription actions on
    private final BaseSchedulerProvider topFivesrProvider;

    /**
     * Prevent direct instantiation.
     * <p>
     * Initialize WebSite provider.
     *
     * @param baseSchedulerProvider The  inject of topFivesr provider.
     */
    public BaseRemoteDataSource(
            @NonNull BaseSchedulerProvider baseSchedulerProvider) {
        this.topFivesrProvider = requireNonNull(baseSchedulerProvider,
                "topFivesProvider cannot be null");
    }


    /**
     * Gets data from the server.
     *
     * @param classOfT    the class of T
     * @param endPointApi The attempt API.
     * @return The List of {@link T} module from the given json text or null.
     */
    protected Flowable<List<T>> getListData(Class<T[]> classOfT, String endPointApi) {
        return Flowable
                .fromCallable(() -> getList(classOfT, new CustomHttp().get(endPointApi)))
                .subscribeOn(topFivesrProvider.io());
    }

    /**
     * Gets list of data from the given json text.
     *
     * @param classOfT   the class of T.
     * @param jsonText the string from which the object is to be deserialize.
     * @return The List of {@link T} module from the given json text or null.
     */
    private List<T> getList(Class<T[]> classOfT, String jsonText) {
        classOfT = requireNonNull(classOfT, "type cannot be null");
        if (jsonText != null) {
            final T[] jsonToObject = new Gson().fromJson(jsonText, classOfT);
            return Arrays.asList(jsonToObject);

        }
        return new ArrayList<>();
    }


}

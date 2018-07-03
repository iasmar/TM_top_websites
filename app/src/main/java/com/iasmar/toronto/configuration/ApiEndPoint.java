package com.iasmar.toronto.configuration;

import com.iasmar.toronto.data.sources.remote.BaseRemoteDataSource;

import static com.iasmar.toronto.configuration.NetworkConfig.BASE_URL;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * This is the APIs end point that remote data source will use in order to get the attempt API.
 * <p>
 * The base URL is controlled by gradle and could be changed from gradle.properties.
 *
 * @author Asmar
 * @version 1
 * @see BaseRemoteDataSource
 * @since 0.1.0
 */
public final class ApiEndPoint {

        public static final String END_POINT_API_WEBSITES = BASE_URL
            + "all_data.php";

        //TODO change to parameters
        public static final String END_POINT_API_TOP_FIVE = BASE_URL
            + "filter.php?start=2017-03-02&end=2017-03-04";
}
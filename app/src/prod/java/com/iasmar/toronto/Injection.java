package com.iasmar.toronto;


import android.content.Context;
import android.support.annotation.NonNull;


import com.iasmar.toronto.data.repositories.websites.WebsitesRepository;
import com.iasmar.toronto.data.repositories.main.MainRepository;
import com.iasmar.toronto.data.sources.remote.websites.WebsitesRemoteDataSource;
import com.iasmar.toronto.util.schedulers.BaseSchedulerProvider;
import com.iasmar.toronto.util.schedulers.SchedulerProvider;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 8/06/2018.
 * <p>
 * Enables injection of production implementations for
 *  repositories at compile time.
 *
 * @author Asmar
 * @since 0.1.0
 */


public class Injection {

    public static WebsitesRepository provideWebsitesRepository(@NonNull Context context) {
        context = requireNonNull(context, "context cannot be null");

        return    WebsitesRepository.getInstance(WebsitesRemoteDataSource.getInstance(provideSchedulerProvider()));

    }

    public static MainRepository provideMainRepository(@NonNull Context context) {
        context = requireNonNull(context, "context cannot be null");
        return MainRepository.getInstance();

    }
    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}


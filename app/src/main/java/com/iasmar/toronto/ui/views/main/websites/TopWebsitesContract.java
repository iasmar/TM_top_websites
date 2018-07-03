package com.iasmar.toronto.ui.views.main.websites;

import com.iasmar.toronto.data.modules.WebSite;
import com.iasmar.toronto.data.modules.ModulesHolder;
import com.iasmar.toronto.ui.views.base.IBasePresenter;
import com.iasmar.toronto.ui.views.base.IBaseView;

/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * This specifies the contract between the view and the presenter.
 *
 * @author Asmar
 * @version 1
 * @see IBaseView
 * @see IBasePresenter
 * @see ModulesHolder
 * @see WebSite
 * @since 1.0
 */
interface TopWebsitesContract {

    //*********************************** The websites view ***********************************//


    /**
     * The websites view  must  implement this interface.
     */
    interface View extends IBaseView<Presenter> {
        /**
         * Display websites on the UI.
         */
        void showWebsites(ModulesHolder<WebSite> websites);
        /**
         * Display no websites on the UI.
         */
        void showNoWebsites();


    }


    //*********************************** The websites presenter ***********************************//

    /**
     * The websites presenter  must  implement this interface.
     *
     * @param <V> the websites view.
     */
    interface Presenter<V extends View> extends IBasePresenter<V> {

        /**
         * load websites.
         *
         * @param forceUpdate true to force update from the server false load from cash or DB,
         *                    if there is a problem with th connection will return from cash or DB.
         */
        void loadWebsites(boolean forceUpdate, String sort);


    }
}

package com.iasmar.toronto.ui.views.main.topfive;


import com.iasmar.toronto.data.modules.ModulesHolder;
import com.iasmar.toronto.data.modules.WebSite;
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
interface TopFiveContract {

    //*********************************** The topFives view ***********************************//


    /**
     * The topFives view  must  implement this interface.
     */
    interface View extends IBaseView<Presenter> {
        /**
         * Display topFives on the UI.
         */
        void showTopFives(ModulesHolder<WebSite> topFives);
        /**
         * Display no topFives on the UI.
         */
        void showNoTopFives();



    }


    //*********************************** The topFives presenter ***********************************//

    /**
     * The topFives presenter  must  implement this interface.
     *
     * @param <V> the topFives view.
     */
    interface Presenter<V extends View> extends IBasePresenter<V> {

        /**
         * load topFives.
         *
         * @param forceUpdate true to force update from the server false load from cash or DB,
         *                    if there is a problem with th connection will return from cash or DB.
         */
        void loadTopFives(boolean forceUpdate);


    }
}

package com.iasmar.toronto.ui.views.main.websites;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iasmar.toronto.R;

import static com.iasmar.toronto.util.ViewUtils.setText;

/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Initialize all the UI components to the {@link TopWebsitesAdapter} and control the UI components.
 *
 * @author Asmar
 * @version 1
 * @see TopWebsitesAdapter
 * @since 1.0
 */
class TopWebsitesAdapterViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameTxv;

    private final TextView dateTxv;

    private final TextView totalVisitsTxv;

    /**
     * The constructor purpose is to
     * <p>
     * Initialize UI components.
     *
     * @param view The item view in the recycle view.
     */
    TopWebsitesAdapterViewHolder(View view) {
        super(view);

        nameTxv = view.findViewById(R.id.row_website_ranking_name_txv);
        dateTxv = view.findViewById(R.id.row_website_date_txv);
        totalVisitsTxv = view.findViewById(R.id.row_website_total_visits_txv);
    }


    /**
     * Set the name of the websites.
     *
     * @param name The name of the websites.
     */
    public void setName(String name) {
        setText(nameTxv,name);

    }
    /**
     * Set the visit date
     *
     * @param visitDate The visitDate of the websites.
     */
    public void setDate(String visitDate) {
        setText(dateTxv,visitDate);

    }
    /**
     * Total visits of the websites.
     *
     * @param totalVisits Total visits of the websites.
     */
    public void setTotalVisits(String totalVisits) {
        setText(totalVisitsTxv,totalVisits);

    }
}
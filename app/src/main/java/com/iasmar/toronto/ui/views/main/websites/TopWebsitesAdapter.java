package com.iasmar.toronto.ui.views.main.websites;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iasmar.toronto.R;
import com.iasmar.toronto.data.modules.WebSite;
import com.iasmar.toronto.data.modules.ModulesHolder;
import com.iasmar.toronto.ui.views.custom.recyclerview.BaseAdapter;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Display a list  of {@link WebSite}s.
 *
 * @author Asmar
 * @version 1
 * @see BaseAdapter
 * @see TopWebsitesAdapterViewHolder
 * @see ModulesHolder
 * @see WebSite
 * @since 1.0
 */
class TopWebsitesAdapter extends BaseAdapter<TopWebsitesAdapterViewHolder, ModulesHolder<WebSite>> {

    /**
     * The constructor purpose is to
     * <p>
     * replace the current list of websites to the new one.
     *
     * @param websites the list of websites.
     * @see ModulesHolder
     * @see WebSite
     * @see TopWebsitesContract.View
     */
    TopWebsitesAdapter(ModulesHolder<WebSite> websites) {
        replaceData(websites);

    }

    /**
     * Called when RecyclerView needs a new {@link TopWebsitesAdapterViewHolder}.
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new {@link TopWebsitesAdapterViewHolder} that holds a View.
     */
    @Override
    public TopWebsitesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parent = requireNonNull(parent, "parent cannot be null");
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TopWebsitesAdapterViewHolder(inflater.inflate(R.layout.row_website_ranking, parent, false));
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The {@link TopWebsitesAdapterViewHolder} that holds a View, which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TopWebsitesAdapterViewHolder holder, int position) {
        holder = requireNonNull(holder, "holder cannot be null");
        super.onBindViewHolder(holder, position);
        final WebSite website = getData().getItemByIndex(holder.getAdapterPosition());

        holder.setName(website.getName());
        holder.setDate(website.getVisitDate());
        holder.setTotalVisits(website.getTotalVisits());
    }


}

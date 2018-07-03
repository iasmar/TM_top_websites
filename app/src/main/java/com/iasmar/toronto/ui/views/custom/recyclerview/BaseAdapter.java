package com.iasmar.toronto.ui.views.custom.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.iasmar.toronto.data.modules.ModulesHolder;

import static com.iasmar.toronto.util.ObjectHelper.requireNonNull;

/**
 * Created by asmar on 9/24/2017.
 */
//TODO  add comments

public class BaseAdapter<T extends RecyclerView.ViewHolder, S extends ModulesHolder> extends RecyclerView.Adapter<T> {


    private S data;

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(T holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.getSize();

    }

    protected void replaceData(S data) {
        this.data = requireNonNull(data,"context cannot be null");

    }

    protected S getData() {
        return data;
    }
}

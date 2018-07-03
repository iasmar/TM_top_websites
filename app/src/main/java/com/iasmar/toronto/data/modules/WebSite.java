package com.iasmar.toronto.data.modules;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.iasmar.toronto.util.GeneralUtils;

import static com.iasmar.toronto.util.GeneralUtils.isNullOrEmpty;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Immutable model class for a website.
 * <p>
 * The main purpose of this Module is to hold the website data.
 *
 * @author Asmar
 * @version 1
 * @see BaseModule
 * @since 0.1.0
 */
public final class WebSite implements BaseModule<String> {

    //The unique id of the website.
    @SerializedName("id_website")
    private final String websiteId;

    //The name of the website.
    @SerializedName("website_name")
    private final String websiteName;

    //The website visit date.
    @SerializedName("visit_date")
    private final String visitDate;

    //The website visit date.
    @SerializedName("total_visits")
    private final String totalVisits;


    /**
     * Use this constructor to create a new website.
     *
     * @param websiteId   The unique id of the website.
     * @param websiteName The name of the website.
     * @param visitDate   The website visit date.
     */
    public WebSite(@NonNull String websiteId, @NonNull String websiteName, @NonNull String visitDate, @NonNull String totalVisits) {
        this.websiteId = websiteId;
        this.websiteName = websiteName;
        this.visitDate = visitDate;
        this.totalVisits = totalVisits;
    }

    /**
     * Get the unique id of the website.
     *
     * @return The unique id of the website.
     */
    @Override
    public String getId() {
        return websiteId;
    }

    /**
     * Get the name of the website.
     *
     * @return The name of the website.
     */
    public String getName() {
        return websiteName;
    }

    /**
     * The website visit date.
     *
     * @return The website visit date.
     */
    public String getVisitDate() {
        return visitDate;
    }
    /**
     * The website visit date.
     *
     * @return The website visit date.
     */
    public String getTotalVisits() {
        return totalVisits;
    }


    /**
     * Get hash of the website.
     *
     * @return The hash of the website.
     */
    @Override
    public String getHash() {
        return GeneralUtils.getHash(toString());
    }

    /**
     * Is the passed object equals to this object.
     *
     * @param obj The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        String obj1Hash = ((BaseModule) obj).getHash();
        String obj2Hash = this.getHash();

        return obj1Hash != null && obj1Hash.equals(obj2Hash);

    }

    /**
     * Is this object empty (has no data).
     *
     * @return {@code true} if this object is empty
     * {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return isNullOrEmpty(getId())
                && isNullOrEmpty(getName());
    }

    /**
     * Get The default sort by.
     *
     * @return The default sort by.
     */
    @Override
    public String getSortBy() {
        return getName();
    }

    /**
     * Get the string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "websiteId: " + getId() +
                "name: " + getName() +
                "visitDate: " + getVisitDate()+
                "totalVisits: " + getTotalVisits();
    }


}

package com.havryliuk.itarticles.utils.events;

/**
 * Created by Igor Havrylyuk on 24.10.2017.
 */

public class SearchParamEvent {

    private boolean isTag;
    private String category;
    private String dateTo;
    private String dateFrom;

    public SearchParamEvent(boolean isTag, String category) {
        this.isTag = isTag;
        this.category = category;
    }

    public boolean isTag() {
        return isTag;
    }

    public void setTag(boolean tag) {
        this.isTag = tag;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SearchParamEvent{" +
                "isTag=" + isTag +
                ", category='" + category + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                '}';
    }
}

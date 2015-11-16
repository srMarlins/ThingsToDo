package com.srmarlins.eventful_android.data.request;

public class EventSearchRequest extends SearchRequest {
    private String category;
    private String dateRange = "future";
    private EventSearchRequest.SortOrder sortOrder;

    public EventSearchRequest() {
        this.sortOrder = EventSearchRequest.SortOrder.RELEVANCE;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDateRange() {
        return this.dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getSortOrder() {
        return this.sortOrder.toString().toLowerCase();
    }

    public void setSortOrder(EventSearchRequest.SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public enum SortOrder {
        RELEVANCE,
        DATE,
        TITLE,
        VENUE_NAME,
        DISTANCE;

        SortOrder() {
        }
    }
}

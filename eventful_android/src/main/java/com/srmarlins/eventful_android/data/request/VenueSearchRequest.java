package com.srmarlins.eventful_android.data.request;

public class VenueSearchRequest extends SearchRequest {
    private VenueSearchRequest.SortOrder sortOrder;

    public VenueSearchRequest() {
        this.sortOrder = VenueSearchRequest.SortOrder.POPULARITY;
    }

    public String getSortOrder() {
        return this.sortOrder.toString().toLowerCase();
    }

    public void setSortOrder(VenueSearchRequest.SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public static enum SortOrder {
        RELEVANCE,
        DATE,
        EVENT_COUNT,
        POPULARITY;

        private SortOrder() {
        }
    }
}

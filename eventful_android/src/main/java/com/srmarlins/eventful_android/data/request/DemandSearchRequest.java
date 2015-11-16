package com.srmarlins.eventful_android.data.request;

public class DemandSearchRequest extends SearchRequest {
    private DemandSearchRequest.SortOrder sortOrder;

    public DemandSearchRequest() {
        this.sortOrder = DemandSearchRequest.SortOrder.RELEVANCE;
    }

    public String getSortOrder() {
        return this.sortOrder.toString().toLowerCase();
    }

    public void setSortOrder(DemandSearchRequest.SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public enum SortOrder {
        RELEVANCE,
        NAME,
        CATEGORY,
        MEMBER_COUNT,
        PERFORMER,
        CREATED;

        SortOrder() {
        }
    }
}
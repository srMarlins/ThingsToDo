package com.srmarlins.eventful_android.data.request;

public class PerformerSearchRequest extends SearchRequest {
    private PerformerSearchRequest.SortOrder sortOrder;

    public PerformerSearchRequest() {
        this.sortOrder = PerformerSearchRequest.SortOrder.RELEVANCE;
    }

    public String getSortOrder() {
        return this.sortOrder.toString().toLowerCase();
    }

    public void setSortOrder(PerformerSearchRequest.SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public static enum SortOrder {
        RELEVANCE,
        NAME,
        CATEGORY,
        MEMBER_COUNT,
        PERFORMER,
        CREATED;

        private SortOrder() {
        }
    }
}

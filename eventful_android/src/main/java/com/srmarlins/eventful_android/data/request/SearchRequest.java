package com.srmarlins.eventful_android.data.request;

public abstract class SearchRequest {
    private String keywords;
    private String includes;
    private String location;
    private String imageSizes;
    private int locationRadius;
    private int changemultidaystart = 0;
    private String locationUnits = "mi";
    private SearchRequest.SortDirection sortDirection;
    private int pageSize;
    private int connectTimeOut;
    private int readTimeOut;
    private int pageNumber;

    public SearchRequest() {
        this.sortDirection = SearchRequest.SortDirection.ASCENDING;
        this.pageSize = 10;
        this.connectTimeOut = 0;
        this.readTimeOut = 0;
        this.pageNumber = 1;
    }

    public abstract String getSortOrder();

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getIncludes() {
        return this.includes;
    }

    public void setIncludes(String includes) {
        this.includes = includes;
    }

    public int getChangeMultiDayStart() {
        return this.changemultidaystart;
    }

    public void setChangeMultiDayStart(int val) {
        this.changemultidaystart = val;
    }

    public String getLocation() {
        return this.location;
    }

    public int getConnectionTimeOut() {
        return this.connectTimeOut;
    }

    public int getReadTimeOut() {
        return this.readTimeOut;
    }

    public void setConnectionTimeout(int timeout) {
        this.connectTimeOut = timeout;
    }

    public void setReadTimeout(int timeout) {
        this.readTimeOut = timeout;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLocationRadius() {
        return this.locationRadius;
    }

    public void setLocationRadius(int locationRadius) {
        this.locationRadius = locationRadius;
    }

    public String getLocationUnits() {
        return this.locationUnits;
    }

    public void setLocationUnits(String locationUnits) {
        this.locationUnits = locationUnits;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public SearchRequest.SortDirection getSortDirection() {
        return this.sortDirection;
    }

    public void setSortDirection(SearchRequest.SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getImageSizes() {
        return imageSizes;
    }

    public void setImageSizes(String imageSizes) {
        this.imageSizes = imageSizes;
    }

    public static enum SortDirection {
        ASCENDING,
        DESCENDING;

        private SortDirection() {
        }
    }
}


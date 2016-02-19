package com.srmarlins.eventful_android.data;


import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


@Root(
        name = "search",
        strict = false
)
@Default(DefaultType.FIELD)
public class SearchResult {
    @Element(
            name = "total_items", required = false
    )
    private int totalItems;
    @Element(
            name = "page_size", required = false
    )
    private int pageSize;
    @Element(
            name = "page_count", required = false
    )
    private int pageCount;
    @Element(
            name = "page_number", required = false
    )
    private int pageNumber;
    @Element(
            name = "page_items", required = false
    )
    private int pageItems;
    @Element(
            name = "first_item", required = false
    )
    private int firstItem;
    @Element(
            name = "last_item", required = false
    )
    private int lastItem;
    @Element(
            name = "search_time", required = false
    )
    private double searchTime;
    @ElementList(
            name = "events", required = false
    )
    private List<Event> events;
    @ElementList(
            name = "venues", required = false
    )
    private List<Venue> venues;
    @ElementList(
            name = "performers", required = false
    )
    private List<Performer> performers;
    @ElementList(
            name = "demands", required = false
    )
    private List<Demand> demands;

    public SearchResult() {
    }

    public Hashtable<String, Event> getEvents() {
        Hashtable<String, Event> eventMap = new Hashtable<>();
        for(Event event : events){
            eventMap.put(event.getSeid(), event);
        }
        return eventMap;
    }

    public void setEvents(Hashtable<String, Event> events) {
        this.events = new ArrayList<>(events.values());
    }

    public int getFirstItem() {
        return this.firstItem;
    }

    public void setFirstItem(int firstItem) {
        this.firstItem = firstItem;
    }

    public int getLastItem() {
        return this.lastItem;
    }

    public void setLastItem(int lastItem) {
        this.lastItem = lastItem;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageItems() {
        return this.pageItems;
    }

    public void setPageItems(int pageItems) {
        this.pageItems = pageItems;
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

    public double getSearchTime() {
        return this.searchTime;
    }

    public void setSearchTime(double searchTime) {
        this.searchTime = searchTime;
    }

    public int getTotalItems() {
        return this.totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Venue> getVenues() {
        return this.venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    public List<Performer> getPerformers() {
        return this.performers;
    }

    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
    }

    public List<Demand> getDemands() {
        return this.demands;
    }

    public void setDemands(List<Demand> demands) {
        this.demands = demands;
    }
}


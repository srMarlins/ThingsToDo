package com.srmarlins.eventful_android.data;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Transient;

import java.util.Date;
import java.util.List;

@Root(
        name = "event",
        strict = false
)
@Default(DefaultType.FIELD)
public class Event {
    @Attribute(
            name = "id"
    )
    private String seid;
    private String title;
    private String url;
    private String description;
    @Element(
            name = "start_time", required = false
    )
    @Attribute
    protected Date startTime;
    @Element(
            name = "stop_time", required = false
    )
    @Attribute
    private Date stopTime;
    @Element(
            name = "tz_olson_path", required = false
    )
    private String olsonPath;
    @Element(
            name = "all_day", required = false
    )
    private boolean allDay = false;
    @Element(name = "free", required = false)
    private boolean free = false;
    @Element(name = "price", required = false)
    private String price;
    @Element(name="withdrawn", required = false)
    private boolean withdrawn;
    @Element(
            name = "withdrawn_note", required = false
    )
    private String withdrawnNote;
    private int privacy = 1;
    @ElementList(
            name = "parents", required = false
    )
    private List<Event> parents;
    @ElementList(
            name = "children", required = false
    )
    private List<Event> children;
    @ElementList(
            name = "links", required = false
    )
    private List<Link> links;
    @ElementList(
            name = "comments", required = false
    )
    private List<Comment> comments;
    @ElementList(
            name = "trackbacks", required = false
    )
    private List<Trackback> trackbacks;
    @ElementList(
            name = "performers", required = false
    )
    private List<Performer> performers;
    @ElementList(
            name = "image", required = false
    )
    private List<Image> images;
    @Element(
            name = "images", required = false
    )
    private Image singleImageList;
    @ElementList(
            name = "tags", required = false
    )
    private List<Tag> tags;
    @ElementList(
            name = "calendars", required = false
    )
    private List<Calendar> calendars;
    @ElementList(
            name = "properties", required = false
    )
    private List<Property> properties;
    @ElementList(
            name = "groups", required = false
    )
    private List<Group> groups;
    @ElementList(
            name = "going", required = false
    )
    private List<User> going;
    @ElementList(
            name = "categories", required = false
    )
    private List<Category> categories;
    @Transient
    private Venue venue;
    @Element(
            name = "venue_name", required = false
    )
    private String venueName;
    @Element(
            name = "venue_id", required = false
    )
    private String svid;
    @Element(
            name = "venue_type", required = false
    )
    private String venueType;
    @Element(
            name = "venue_display", required = false
    )
    private int venueDisplay;
    @Element(
            name = "venue_address", required = false
    )
    private String venueAddress;
    @Element(
            name = "city_name", required = false
    )
    private String venueCity;
    @Element(
            name = "region_name", required = false
    )
    private String venueRegion;
    @Element(
            name = "region_abbr", required = false
    )
    private String venueRegionAbbreviation;
    @Element(
            name = "postal_code", required = false
    )
    private String venuePostalCode;
    @Element(
            name = "country_name", required = false
    )
    private String venueCountry;
    @Element(
            name = "county_abbr2", required = false
    )
    private String venueCountryTwoLetterAbbreviation;
    @Element(
            name = "country_abbr", required = false
    )
    private String venueCountryThreeLetterAbbreviation;
    @Element(
            name = "latitude", required = false
    )
    private double venueLatitude;
    @Element(
            name = "longitude", required = false
    )
    private double venueLongitude;
    @Element(
            name = "geocode_type", required = false
    )
    private String venueGeocodeType;
    @Element(
            name = "recur_string", required = false
    )
    private String recurString;

    public Event() {
    }

    public String getRecurString() {
        return this.recurString;
    }

    public void setRecurString(String recurString) {
        this.recurString = recurString;
    }

    public Venue getVenue() {
        if (this.venue != null) {
            return this.venue;
        } else if (this.venueName != null && this.venueName.length() != 0 || this.svid != null && this.svid.length() != 0) {
            Venue v = new Venue();
            v.setName(this.venueName);
            v.setSvid(this.svid);
            v.setType(this.venueType);
            v.setDisplay(this.venueDisplay);
            v.setAddress(this.venueAddress);
            v.setCity(this.venueCity);
            v.setRegion(this.venueRegion);
            v.setRegionAbbreviation(this.venueRegionAbbreviation);
            v.setPostalCode(this.venuePostalCode);
            v.setCountry(this.venueCountry);
            v.setCountryTwoLetterAbbreviation(this.venueCountryTwoLetterAbbreviation);
            v.setCountryThreeLetterAbbreviation(this.venueCountryThreeLetterAbbreviation);
            v.setLatitude(this.venueLatitude);
            v.setLongitude(this.venueLongitude);
            v.setGeocodeType(this.venueGeocodeType);
            return v;
        } else {
            return null;
        }
    }

    public boolean isAllDay() {
        return this.allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeid() {
        return this.seid;
    }

    public void setSeid(String seid) {
        this.seid = seid;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return this.stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getTitle() {
        return this.title;
    }

    public String getURL() {
        return this.url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public List<Event> getChildren() {
        return this.children;
    }

    public void setChildren(List<Event> children) {
        this.children = children;
    }

    public boolean isFree() {
        return this.free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public List<Event> getParents() {
        return this.parents;
    }

    public void setParents(List<Event> parents) {
        this.parents = parents;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isWithdrawn() {
        return this.withdrawn;
    }

    public void setWithdrawn(boolean withdrawn) {
        this.withdrawn = withdrawn;
    }

    public String getWithdrawnNote() {
        return this.withdrawnNote;
    }

    public void setWithdrawnNote(String withdrawnNote) {
        this.withdrawnNote = withdrawnNote;
    }

    public List<Link> getLinks() {
        return this.links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Performer> getPerformers() {
        return this.performers;
    }

    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
    }

    public List<Trackback> getTrackbacks() {
        return this.trackbacks;
    }

    public void setTrackbacks(List<Trackback> trackbacks) {
        this.trackbacks = trackbacks;
    }

    public List<Image> getImages(){
        return this.images;
    }

    public Image getImage(){
        return this.singleImageList;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Calendar> getCalendars() {
        return this.calendars;
    }

    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<User> getGoing() {
        return this.going;
    }

    public void setGoing(List<User> going) {
        this.going = going;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public int getPrivacy() {
        return this.privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    public String getOlsonPath() {
        return this.olsonPath;
    }

    public void setOlsonPath(String olsonPath) {
        this.olsonPath = olsonPath;
    }

    public String getSvid() {
        return this.svid;
    }

    public void setSvid(String svid) {
        this.svid = svid;
    }

    public String getVenueAddress() {
        return this.venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getVenueCity() {
        return this.venueCity;
    }

    public void setVenueCity(String venueCity) {
        this.venueCity = venueCity;
    }

    public String getVenueCountry() {
        return this.venueCountry;
    }

    public void setVenueCountry(String venueCountry) {
        this.venueCountry = venueCountry;
    }

    public String getVenueCountryThreeLetterAbbreviation() {
        return this.venueCountryThreeLetterAbbreviation;
    }

    public void setVenueCountryThreeLetterAbbreviation(String venueCountryThreeLetterAbbreviation) {
        this.venueCountryThreeLetterAbbreviation = venueCountryThreeLetterAbbreviation;
    }

    public String getVenueCountryTwoLetterAbbreviation() {
        return this.venueCountryTwoLetterAbbreviation;
    }

    public void setVenueCountryTwoLetterAbbreviation(String venueCountryTwoLetterAbbreviation) {
        this.venueCountryTwoLetterAbbreviation = venueCountryTwoLetterAbbreviation;
    }

    public int getVenueDisplay() {
        return this.venueDisplay;
    }

    public void setVenueDisplay(int venueDisplay) {
        this.venueDisplay = venueDisplay;
    }

    public String getVenueGeocodeType() {
        return this.venueGeocodeType;
    }

    public void setVenueGeocodeType(String venueGeocodeType) {
        this.venueGeocodeType = venueGeocodeType;
    }

    public double getVenueLatitude() {
        return this.venueLatitude;
    }

    public void setVenueLatitude(double venueLatitude) {
        this.venueLatitude = venueLatitude;
    }

    public double getVenueLongitude() {
        return this.venueLongitude;
    }

    public void setVenueLongitude(double venueLongitude) {
        this.venueLongitude = venueLongitude;
    }

    public String getVenueName() {
        return this.venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenuePostalCode() {
        return this.venuePostalCode;
    }

    public void setVenuePostalCode(String venuePostalCode) {
        this.venuePostalCode = venuePostalCode;
    }

    public String getVenueRegion() {
        return this.venueRegion;
    }

    public void setVenueRegion(String venueRegion) {
        this.venueRegion = venueRegion;
    }

    public String getVenueRegionAbbreviation() {
        return this.venueRegionAbbreviation;
    }

    public void setVenueRegionAbbreviation(String venueRegionAbbreviation) {
        this.venueRegionAbbreviation = venueRegionAbbreviation;
    }

    public String getVenueType() {
        return this.venueType;
    }

    public void setVenueType(String venueType) {
        this.venueType = venueType;
    }
}

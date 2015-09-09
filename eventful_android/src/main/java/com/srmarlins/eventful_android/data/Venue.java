package com.srmarlins.eventful_android.data;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(
        name = "venue",
        strict = false
)
@Default(DefaultType.FIELD)
public class Venue {
    @ElementUnion({
            @Element(name = "name"),
            @Element(name = "venue_name")
    })
    private String name;

    @Element(
            name = "venue_id"
    )
    private String svid;

    @Attribute(
            name = "id"
    )
    private String attributeId;

    @Element(
            name = "venue_type"
    )
    private String type;

    @Element(
            name = "venue_display"
    )
    private int display;
    private String address;
    @ElementUnion({@Element(
            name = "city"
    ), @Element(
            name = "city_name"
    )})
    private String city;
    @ElementUnion({@Element(
            name = "region"
    ), @Element(
            name = "region_name"
    )})
    private String region;
    private String description;
    @Element(
            name = "region_abbr"
    )
    private String regionAbbreviation;
    @Element(
            name = "postal_code"
    )
    private String postalCode;
    @ElementUnion({@Element(
            name = "country"
    ), @Element(
            name = "country_name"
    )})
    private String country;
    @Element(
            name = "county_abbr2"
    )
    private String countryTwoLetterAbbreviation;
    @Element(
            name = "country_abbr"
    )
    private String countryThreeLetterAbbreviation;
    private double latitude;
    private double longitude;
    private int privacy = 1;
    private String url;
    @Element(
            name = "url_type"
    )
    private String urlType;
    @Element(
            name = "geocode_type"
    )
    private String geocodeType;
    @ElementList(
            name = "parents", required = false
    )
    private List<Venue> parents;
    @ElementList(
            name = "children", required = false
    )
    private List<Venue> children;
    @ElementList(
            name = "events", required = false
    )
    private List<Event> events;
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
            name = "properties", required = false
    )
    private List<Property> properties;
    @ElementList(
            name = "tags", required = false
    )
    private List<Tag> tags;

    public Venue() {
    }

    public String getSvid() {
        return this.svid == null ? this.attributeId : this.svid;
    }

    public void setSvid(String svid) {
        this.svid = svid;
        this.attributeId = svid;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String venueAddress) {
        this.address = venueAddress;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String venueCity) {
        this.city = venueCity;
    }

    public int getDisplay() {
        return this.display;
    }

    public void setDisplay(int venueDisplay) {
        this.display = venueDisplay;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String venueName) {
        this.name = venueName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String venueType) {
        this.type = venueType;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryThreeLetterAbbreviation() {
        return this.countryThreeLetterAbbreviation;
    }

    public void setCountryThreeLetterAbbreviation(String countryThreeLetterAbbreviation) {
        this.countryThreeLetterAbbreviation = countryThreeLetterAbbreviation;
    }

    public String getCountryTwoLetterAbbreviation() {
        return this.countryTwoLetterAbbreviation;
    }

    public void setCountryTwoLetterAbbreviation(String countryTwoLetterAbbreviation) {
        this.countryTwoLetterAbbreviation = countryTwoLetterAbbreviation;
    }

    public String getGeocodeType() {
        return this.geocodeType;
    }

    public void setGeocodeType(String geocodeType) {
        this.geocodeType = geocodeType;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionAbbreviation() {
        return this.regionAbbreviation;
    }

    public void setRegionAbbreviation(String regionAbbreviation) {
        this.regionAbbreviation = regionAbbreviation;
    }

    public List<Venue> getChildren() {
        return this.children;
    }

    public void setChildren(List<Venue> children) {
        this.children = children;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Link> getLinks() {
        return this.links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Venue> getParents() {
        return this.parents;
    }

    public void setParents(List<Venue> parents) {
        this.parents = parents;
    }

    public List<Trackback> getTrackbacks() {
        return this.trackbacks;
    }

    public void setTrackbacks(List<Trackback> trackbacks) {
        this.trackbacks = trackbacks;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrivacy() {
        return this.privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlType() {
        return this.urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}


package com.srmarlins.eventful_android.data;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Date;
import java.util.List;

@Root(
        name = "performer",
        strict = false
)
@Default(DefaultType.FIELD)
public class Performer {
    @Element(
            name = "id"
    )
    private String spid;
    @Attribute(
            name = "id"
    )
    private String attributeId;
    private String name;
    @Element(
            name = "is_human"
    )
    private boolean human;
    @Element(
            name = "short_bio"
    )
    private String shortBio;
    @Element(
            name = "long_bio"
    )
    private String longBio;
    @Element(
            name = "demand_count"
    )
    private int demandCount;
    @Element(
            name = "demand_member_count"
    )
    private int demandMemberCount;
    @Element(
            name = "event_count"
    )
    private int eventCount;
    @Attribute
    private Date created;
    private String creator;
    private boolean withdrawn;
    @Element(
            name = "withdrawn_note"
    )
    private String withdrawnNote;
    @ElementList(
            name = "links", required = false
    )
    private List<Link> links;
    @ElementList(
            name = "comments", required = false
    )
    private List<Comment> comments;
    @ElementList(
            name = "events", required = false
    )
    private List<Event> events;
    @ElementList(
            name = "images", required = false
    )
    private List<Image> images;
    @ElementList(
            name = "tags", required = false
    )
    private List<Tag> tags;
    @ElementList(
            name = "properties", required = false
    )
    private List<Property> properties;

    public Performer() {
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getDemandCount() {
        return this.demandCount;
    }

    public void setDemandCount(int demandCount) {
        this.demandCount = demandCount;
    }

    public int getDemandMemberCount() {
        return this.demandMemberCount;
    }

    public void setDemandMemberCount(int demandMemberCount) {
        this.demandMemberCount = demandMemberCount;
    }

    public int getEventCount() {
        return this.eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public boolean isHuman() {
        return this.human;
    }

    public void setHuman(boolean human) {
        this.human = human;
    }

    public List<Image> getImages() {
        return this.images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Link> getLinks() {
        return this.links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getLongBio() {
        return this.longBio;
    }

    public void setLongBio(String longBio) {
        this.longBio = longBio;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortBio() {
        return this.shortBio;
    }

    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    public String getSpid() {
        return this.spid == null ? this.attributeId : this.spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
        this.attributeId = spid;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
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

    public List<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}


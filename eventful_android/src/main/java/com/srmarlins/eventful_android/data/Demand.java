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
        name = "demand",
        strict = false
)
@Default(DefaultType.FIELD)
public class Demand {
    @Element(
            name = "id"
    )
    private String sdid;
    private String description;
    private String status;
    private String location;
    @Element(
            name = "member_count"
    )
    private int memberCount;
    @Attribute
    private Date created;
    private String creator;
    private boolean withdrawn;
    @Element(
            name = "withdrawn_note"
    )
    private String withdrawnNote;
    private Performer performer;
    @ElementList(
            name = "images", required = false
    )
    private List<Image> images;
    @ElementList(
            name = "tags", required = false
    )
    private List<Tag> tags;
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

    public Demand() {
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
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

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMemberCount() {
        return this.memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public Performer getPerformer() {
        return this.performer;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }

    public String getSdid() {
        return this.sdid;
    }

    public void setSdid(String sdid) {
        this.sdid = sdid;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}


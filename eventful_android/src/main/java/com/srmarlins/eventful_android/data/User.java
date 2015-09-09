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
        name = "user",
        strict = false
)
@Default(DefaultType.FIELD)
public class User {
    private String username;
    @Element(
            name = "status"
    )
    private String eventWatchingGoingStatus;
    private String bio;
    private String hometown;
    @Element(
            name = "first_name"
    )
    private String firstName;
    @Element(
            name = "last_name"
    )
    private String lastName;
    private String interests;
    @Element(
            name = "registration_date"
    )
    @Attribute
    private Date registrationDate;
    private String reputation;
    @ElementList(
            name = "links", required = false
    )
    private List<Link> links;
    @ElementList(
            name = "images", required = false
    )
    private List<Image> images;
    @ElementList(
            name = "going", required = false
    )
    private List<Event> going;

    public User() {
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEventWatchingGoingStatus() {
        return this.eventWatchingGoingStatus;
    }

    public void setEventWatchingGoingStatus(String eventWatchingGoingStatus) {
        this.eventWatchingGoingStatus = eventWatchingGoingStatus;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Event> getGoing() {
        return this.going;
    }

    public void setGoing(List<Event> going) {
        this.going = going;
    }

    public String getHometown() {
        return this.hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public List<Image> getImages() {
        return this.images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getInterests() {
        return this.interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Link> getLinks() {
        return this.links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Date getRegistrationDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getReputation() {
        return this.reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


package com.srmarlins.eventful_android.data;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Transient;

import java.util.Date;

@Root(
        name = "link",
        strict = false
)
@Default(DefaultType.FIELD)
public class Link {
    @Attribute
    private int id;
    private String url;
    @Transient
    private Link.LinkType type;
    @Element(
            name = "type"
    )
    private String typeString;
    private String description;
    private String username;
    @Element(
            name = "time"
    )
    @Attribute
    private Date createdTime;

    public Link() {
    }

    public Date getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        if (this.typeString != null) {
            this.type = Link.LinkType.valueOf(this.typeString.toUpperCase());
        }

        return this.type.asInteger();
    }

    public void setType(Link.LinkType type) {
        this.type = type;
        this.typeString = type.toString();
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public enum LinkType {
        INFO(1),
        BOX_OFFICE(2),
        NEWS(3),
        REVIEW(4),
        SPONSOR(5),
        TICKETS(6),
        CHAT(8),
        BLOG(15),
        OFFICIAL_SITE(17),
        PODCAST(18),
        WEBCAST(14),
        WEBSITE(19),
        OTHER(16);

        private int typeId;

        LinkType(int typeId) {
            this.typeId = typeId;
        }

        public int asInteger() {
            return this.typeId;
        }
    }
}


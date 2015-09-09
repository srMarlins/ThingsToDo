package com.srmarlins.eventful_android.data;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(
        name = "calendar",
        strict = false
)
@Default(DefaultType.FIELD)
public class Calendar {
    @Attribute(
            name = "id", required = false
    )
    private String scid;
    private String name;
    private String owner;
    private String description;
    private String keywords;
    private int privacy;
    @Element(
            name = "notify_schedule", required = false
    )
    private String notifySchedule;
    @Element(
            name = "what_query", required = false
    )
    private String whatQuery;
    @Element(
            name = "where_query", required = false
    )
    private String whereQuary;
    @ElementList(
            name = "properties", required = false
    )
    private List<Property> properties;

    public Calendar() {
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotifySchedule() {
        return this.notifySchedule;
    }

    public void setNotifySchedule(String notifySchedule) {
        this.notifySchedule = notifySchedule;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPrivacy() {
        return this.privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    public String getScid() {
        return this.scid;
    }

    public void setScid(String scid) {
        this.scid = scid;
    }

    public String getWhatQuery() {
        return this.whatQuery;
    }

    public void setWhatQuery(String whatQuery) {
        this.whatQuery = whatQuery;
    }

    public String getWhereQuary() {
        return this.whereQuary;
    }

    public void setWhereQuary(String whereQuary) {
        this.whereQuary = whereQuary;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}


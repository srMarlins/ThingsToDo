package com.srmarlins.eventful_android.data;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(
        name = "group",
        strict = false
)
@Default(DefaultType.FIELD)
public class Group {
    @Attribute(
            name = "id"
    )
    private String sgid;
    @Element(
            name = "group_name"
    )
    private String name;
    @Element(
            name = "creator_id"
    )
    private int creatorId;
    @Element(
            name = "group_description"
    )
    private String description;

    public Group() {
    }

    public int getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSgid() {
        return this.sgid;
    }

    public void setSgid(String sgid) {
        this.sgid = sgid;
    }
}

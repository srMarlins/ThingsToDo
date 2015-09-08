package com.srmarlins.eventful_android.data;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Root(
        name = "tag",
        strict = false
)
@Default(DefaultType.FIELD)
public class Tag {
    @Attribute
    private String id;
    private String title;
    private String owner;

    public Tag() {
    }

    public Tag(String title) {
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package com.srmarlins.eventful_android.data;


import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Root(
        name = "category",
        strict = false
)
@Default(DefaultType.FIELD)
public class Category {
    private String id;
    private String name;

    public Category() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


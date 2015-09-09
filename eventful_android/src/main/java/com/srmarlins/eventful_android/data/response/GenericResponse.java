package com.srmarlins.eventful_android.data.response;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(
        name = "response",
        strict = false
)
@Default(DefaultType.FIELD)
public class GenericResponse {
    @Attribute
    private String status;
    private String message;
    private String id;
    @Element(
            name = "property_id"
    )
    private int propertyId;

    public GenericResponse() {
    }

    public int getPropertyId() {
        return this.propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


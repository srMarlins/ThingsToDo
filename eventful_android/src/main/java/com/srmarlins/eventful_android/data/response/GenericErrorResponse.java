package com.srmarlins.eventful_android.data.response;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Root(
        name = "error"
)
@Default(DefaultType.FIELD)
public class GenericErrorResponse {
    @Attribute(
            name = "string"
    )
    private String status;
    private String description;

    public GenericErrorResponse() {
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

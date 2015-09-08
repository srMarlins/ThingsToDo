package com.srmarlins.eventful_android.data;


import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
@Default(DefaultType.FIELD)
public class ImageItem {
    @Element(
            name = "url", required = false
    )
    private String url;
    @Element(
            name = "width", required = false
    )
    private int width;
    @Element(
            name = "height", required = false
    )
    private int height;

    public ImageItem() {
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

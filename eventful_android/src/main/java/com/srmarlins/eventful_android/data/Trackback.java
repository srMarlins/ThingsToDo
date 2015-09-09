package com.srmarlins.eventful_android.data;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

@Root(
        name = "trackback",
        strict = false
)
@Default
public class Trackback {
    private int id;
    @Element(
            name = "blog_name"
    )
    private String blogName;
    private String excerpt;
    @Element(
            name = "trackback_url"
    )
    private String url;
    @Element(
            name = "time"
    )
    private Date timeReceived;

    public Trackback() {
    }

    public String getBlogName() {
        return this.blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getExcerpt() {
        return this.excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimeReceived() {
        return this.timeReceived;
    }

    public void setTimeReceived(Date timeReceived) {
        this.timeReceived = timeReceived;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


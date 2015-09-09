package com.srmarlins.eventful_android.data;


import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(
        name = "image"
)
@Default(DefaultType.FIELD)
public class Image {
    @Element(
            name = "id", required = false
    )
    private String id;
    @Element(
            name = "caption", required = false
    )
    private String caption;
    @Element(
            required = false
    )
    private String creator;
    @Element(
            required = false
    )
    private String source;
    @Element(
            name = "default", required = false
    )
    private boolean sticky;
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
    @Element(
            name = "small", required = false
    )
    private ImageItem small;
    @Element(
            name = "medium",required = false
    )
    private ImageItem medium;
    @Element(
            name = "large", required = false
    )
    private ImageItem large;
    @Element(
            required = false
    )
    private ImageItem block;
    @Element(
            name = "thumb",required = false
    )
    private ImageItem thumb;
    @Element(
            name = "original", required = false
    )
    private ImageItem original;

    public Image() {
    }

    public ImageItem getBlock() {
        return this.block;
    }

    public void setBlock(ImageItem block) {
        this.block = block;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImageItem getLarge() {
        return this.large;
    }

    public void setLarge(ImageItem large) {
        this.large = large;
    }

    public ImageItem getMedium() {
        return this.medium;
    }

    public void setMedium(ImageItem medium) {
        this.medium = medium;
    }

    public ImageItem getOriginal() {
        return this.original;
    }

    public void setOriginal(ImageItem original) {
        this.original = original;
    }

    public ImageItem getSmall() {
        return this.small;
    }

    public void setSmall(ImageItem small) {
        this.small = small;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isSticky() {
        return this.sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    public ImageItem getThumb() {
        return this.thumb;
    }

    public void setThumb(ImageItem thumb) {
        this.thumb = thumb;
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


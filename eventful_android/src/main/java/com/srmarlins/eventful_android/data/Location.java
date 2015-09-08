package com.srmarlins.eventful_android.data;


import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(
        name = "response",
        strict = false
)
@Default(DefaultType.FIELD)
public class Location {
    private String original;
    @Element(
            name = "postal_code"
    )
    private String postalCode;
    @Element(
            name = "city_name"
    )
    private String city;
    @Element(
            name = "metro_name"
    )
    private String metro;
    @Element(
            name = "region_name"
    )
    private String region;
    @Element(
            name = "country_name"
    )
    private String country;
    @Element(
            name = "lat"
    )
    private double latitude;
    @Element(
            name = "lon"
    )
    private double longitude;

    public Location() {
    }

    public String getOriginal() {
        return this.original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMetro() {
        return this.metro;
    }

    public void setMetro(String metro) {
        this.metro = metro;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}


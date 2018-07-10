package com.itrided.android.popularmovies.model;

public class Trailer {

    private String id;
    private String languageCode;
    private String countryCode;

    private String youTubeKey;
    private String name;
    private String site;
    private int size;
    private String type;

    public Trailer(String id, String languageCode, String countryCode, String youTubeKey, String name,
                   String site, int size, String type) {
        this.id = id;
        this.languageCode = languageCode;
        this.countryCode = countryCode;
        this.youTubeKey = youTubeKey;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getYouTubeKey() {
        return youTubeKey;
    }

    public void setYouTubeKey(String youTubeKey) {
        this.youTubeKey = youTubeKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

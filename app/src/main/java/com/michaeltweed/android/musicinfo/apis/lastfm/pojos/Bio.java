package com.michaeltweed.android.musicinfo.apis.lastfm.pojos;

public class Bio {

    private Links links;

    private String published;

    private String summary;

    private String content;

    private String placeformed;

    private String yearformed;

    public Bio(Links links, String published, String summary, String content, String placeformed, String yearformed) {
        this.links = links;
        this.published = published;
        this.summary = summary;
        this.content = content;
        this.placeformed = placeformed;
        this.yearformed = yearformed;
    }

    public Links getLinks() {
        return links;
    }

    public String getPublished() {
        return published;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public String getPlaceformed() {
        return placeformed;
    }

    public String getYearformed() {
        return yearformed;
    }
}

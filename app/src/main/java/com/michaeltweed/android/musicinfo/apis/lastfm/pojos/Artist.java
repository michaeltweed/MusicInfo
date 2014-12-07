package com.michaeltweed.android.musicinfo.apis.lastfm.pojos;

import java.util.ArrayList;
import java.util.List;

public class Artist {

    private String name;

    private String mbid;

    private String url;

    private List<Image> image = new ArrayList<Image>();

    private String streamable;

    private String ontour;

    private Stats stats;

    private Similar similar;

    private Tags tags;

    private Bio bio;

    public String getName() {
        return name;
    }

    public String getMbid() {
        return mbid;
    }

    public String getUrl() {
        return url;
    }

    public List<Image> getImage() {
        return image;
    }

    public String getStreamable() {
        return streamable;
    }

    public String getOntour() {
        return ontour;
    }

    public Stats getStats() {
        return stats;
    }

    public Similar getSimilar() {
        return similar;
    }

    public Tags getTags() {
        return tags;
    }

    public Bio getBio() {
        return bio;
    }
}

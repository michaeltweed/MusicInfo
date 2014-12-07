package com.michaeltweed.android.musicinfo.apis.lastfm.pojos;

import java.util.ArrayList;
import java.util.List;

public class SimilarArtist {


    private String name;

    private String url;

    private List<Image> image = new ArrayList<Image>();

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<Image> getImage() {
        return image;
    }
}

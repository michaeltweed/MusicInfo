package com.michaeltweed.android.musicinformation.apis.lastfm.pojos;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("#text")
    private String url;

    private String size;

    public Image(String url, String size) {
        this.url = url;
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public String getSize() {
        return size;
    }
}

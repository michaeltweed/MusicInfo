package com.michaeltweed.android.musicinfo.apis.lastfm.pojos;

import com.google.gson.annotations.SerializedName;

public class Link {

    @SerializedName("#text")
    private String text;

    private String rel;

    private String href;

    public String getText() {
        return text;
    }

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }
}

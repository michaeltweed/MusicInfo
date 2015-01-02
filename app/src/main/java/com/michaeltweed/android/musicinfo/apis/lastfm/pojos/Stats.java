package com.michaeltweed.android.musicinfo.apis.lastfm.pojos;

public class Stats {


    private String listeners;

    private String playcount;

    private String userplaycount;

    public Stats(String listeners, String playcount, String userplaycount) {
        this.listeners = listeners;
        this.playcount = playcount;
        this.userplaycount = userplaycount;
    }

    public String getListeners() {
        return listeners;
    }

    public String getPlaycount() {
        return playcount;
    }

    public String getUserplaycount() {
        return userplaycount;
    }
}

package com.michaeltweed.android.musicinfo.apis.lastfm.pojos;

public class ArtistResponse {

    private Artist artist;

    public ArtistResponse(Artist artist) {
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }

}

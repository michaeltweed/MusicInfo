package com.michaeltweed.android.musicinfo.events;

public class NoArtistInfoAvailableEvent {
    String artistName;

    public NoArtistInfoAvailableEvent(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }
}

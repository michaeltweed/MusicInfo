package com.michaeltweed.android.musicinfo.events;

public class LastFmUsernameChangedEvent {

    private String username;

    public LastFmUsernameChangedEvent(String username) {

        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LastFmUsernameChangedEvent that = (LastFmUsernameChangedEvent) o;

        if (!username.equals(that.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}

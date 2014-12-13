package com.michaeltweed.android.musicinfo.events;

public class SongChangedEvent {
    private final String artist;
    private final String album;
    private final String track;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SongChangedEvent that = (SongChangedEvent) o;

        if (album != null ? !album.equals(that.album) : that.album != null) return false;
        if (artist != null ? !artist.equals(that.artist) : that.artist != null) return false;
        if (track != null ? !track.equals(that.track) : that.track != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = artist != null ? artist.hashCode() : 0;
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (track != null ? track.hashCode() : 0);
        return result;
    }

    public SongChangedEvent(String artist, String album, String track) {

        this.artist = artist;
        this.album = album;
        this.track = track;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getTrack() {
        return track;
    }
}

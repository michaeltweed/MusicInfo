package com.michaeltweed.android.musicinfo.events;

import com.michaeltweed.android.musicinfo.artist.FilteredArtistResponse;

public class ArtistResponseEvent {
    private FilteredArtistResponse filteredArtistResponse;

    public ArtistResponseEvent(FilteredArtistResponse filteredArtistResponse) {

        this.filteredArtistResponse = filteredArtistResponse;
    }

    public FilteredArtistResponse getFilteredArtistResponse() {
        return filteredArtistResponse;
    }
}

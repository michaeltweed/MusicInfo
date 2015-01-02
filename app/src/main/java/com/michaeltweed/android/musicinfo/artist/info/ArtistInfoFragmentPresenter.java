package com.michaeltweed.android.musicinfo.artist.info;

import com.michaeltweed.android.musicinfo.events.ArtistResponseEvent;
import com.michaeltweed.android.musicinfo.events.NoArtistInfoAvailableEvent;
import com.squareup.otto.Subscribe;

public class ArtistInfoFragmentPresenter {

    private final ArtistInfoFragmentView view;

    public ArtistInfoFragmentPresenter(ArtistInfoFragmentView view) {
        this.view = view;
    }

    @Subscribe
    public void onArtistResponseReceived(ArtistResponseEvent event) {
        if (event != null) {
            view.updateArtistBioText(event.getFilteredArtistResponse().getArtistBio());
            view.updateArtistPlayCount(event.getFilteredArtistResponse().getArtistPlayCount());
            view.setArtistPlayCountVisibility(true);
        }
    }

    @Subscribe
    public void onNoArtistInformationAvailable(NoArtistInfoAvailableEvent event) {
        if (event != null) {
            view.updateArtistBioText("No information available for " + event.getArtistName());
            view.setArtistPlayCountVisibility(false);
        }
    }
}

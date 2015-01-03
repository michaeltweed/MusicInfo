package com.michaeltweed.android.musicinfo.artist;

import com.michaeltweed.android.musicinfo.events.ArtistResponseEvent;
import com.michaeltweed.android.musicinfo.events.DataChangedEvent;
import com.michaeltweed.android.musicinfo.events.NoArtistInfoAvailableEvent;
import com.squareup.otto.Subscribe;

public class ArtistFragmentPresenter{

    private ArtistFragmentView view;

    public ArtistFragmentPresenter(ArtistFragmentView view) {
        this.view = view;
    }

    @Subscribe
    public void onArtistChangedEvent(DataChangedEvent event) {
        if (event != null) {
            view.setProgressBarVisibility(true);
        }
    }

    @Subscribe
    public void onNoArtistInfoAvailableEvent(NoArtistInfoAvailableEvent event) {
        if (event != null) {
            view.setProgressBarVisibility(false);
        }
    }

    @Subscribe
    public void onArtistResponseEvent(ArtistResponseEvent event) {
        if (event != null) {
            view.setProgressBarVisibility(false);
        }
    }
}

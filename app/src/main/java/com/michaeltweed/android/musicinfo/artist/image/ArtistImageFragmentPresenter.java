package com.michaeltweed.android.musicinfo.artist.image;

import android.support.v7.graphics.Palette;

import com.michaeltweed.android.musicinfo.events.ArtistResponseEvent;
import com.michaeltweed.android.musicinfo.events.NoArtistInfoAvailableEvent;
import com.michaeltweed.android.musicinfo.events.PaletteAvailableEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ArtistImageFragmentPresenter {

    private Bus bus;
    private ArtistImageFragmentView view;

    public ArtistImageFragmentPresenter(Bus bus, ArtistImageFragmentView view) {
        this.bus = bus;
        this.view = view;
    }

    public void paletteAvailable(Palette palette) {
        bus.post(new PaletteAvailableEvent(palette));
    }

    @Subscribe
    public void onArtistResponseReceived(ArtistResponseEvent event) {
        if (event != null) {
            if (event.getFilteredArtistResponse().hasArtistImageUrl()) {
                view.setImageBackgroundToUrl(event.getFilteredArtistResponse().getArtistImageUrl());
            }
        }
    }

    @Subscribe
    public void onNoArtistInformationAvailable(NoArtistInfoAvailableEvent event) {
        if (event != null) {
            view.setImageBackgroundToEmpty();
        }
    }

}

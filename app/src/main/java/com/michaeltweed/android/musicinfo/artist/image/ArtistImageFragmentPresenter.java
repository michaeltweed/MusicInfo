package com.michaeltweed.android.musicinfo.artist.image;

import android.support.v7.graphics.Palette;

import com.michaeltweed.android.musicinfo.events.PaletteAvailableEvent;
import com.squareup.otto.Bus;

public class ArtistImageFragmentPresenter {

    private Bus bus;

    public ArtistImageFragmentPresenter(Bus bus) {
        this.bus = bus;
    }

    public void paletteAvailable(Palette palette) {
        bus.post(new PaletteAvailableEvent(palette));
    }
}

package com.michaeltweed.android.musicinfo.events;

import android.support.v7.graphics.Palette;

public class PaletteAvailableEvent {
    private Palette palette;

    public PaletteAvailableEvent(Palette palette) {
        this.palette = palette;
    }

    public Palette getPalette() {
        return palette;
    }
}

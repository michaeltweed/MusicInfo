package com.michaeltweed.android.musicinfo.nowplaying;

import android.graphics.Color;
import android.support.v7.graphics.Palette;

import com.michaeltweed.android.musicinfo.events.PaletteAvailableEvent;
import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class NowPlayingFragmentPresenter {


    private final Bus bus;
    private final NowPlayingFragmentView view;

    public NowPlayingFragmentPresenter(Bus bus, NowPlayingFragmentView view) {
        this.bus = bus;
        this.view = view;
    }

    @Subscribe
    public void onSongChangedEvent(SongChangedEvent event) {
        String artist = event.getArtist();
        String album = event.getAlbum();
        String track = event.getTrack();

        view.updateText(track + " | " + album + " - " + artist);
    }

    @Subscribe
    public void onPaletteAvailableEvent(PaletteAvailableEvent event) {
        Palette.Swatch swatch = event.getPalette().getDarkVibrantSwatch();
        if (swatch == null) {
            swatch = event.getPalette().getLightVibrantSwatch();
        }

        if (swatch != null) {
            view.updateBackgroundColor(swatch.getRgb());
            view.updateTitleColor(swatch.getTitleTextColor());
            view.updateTextColor(swatch.getBodyTextColor());
        } else {
            view.updateBackgroundColor(Color.BLACK);
            view.updateTitleColor(Color.WHITE);
            view.updateTextColor(Color.WHITE);
        }
    }
}

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

        view.updateSongText(track);
        view.updateAlbumArtistText(album + " - " + artist);
    }

    @Subscribe
    public void onPaletteAvailableEvent(PaletteAvailableEvent event) {
        Palette.Swatch swatch = getSwatch(event.getPalette());

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

    //this code isn't pretty, but I want to get a swatch if one exists
    //as it is pleasing visually. I also want to check in this order
    private Palette.Swatch getSwatch(Palette palette) {
        if (palette.getVibrantSwatch() != null) {
            return palette.getVibrantSwatch();
        }
        if (palette.getDarkVibrantSwatch() != null) {
            return palette.getDarkVibrantSwatch();
        }
        if (palette.getLightVibrantSwatch() != null) {
            return palette.getLightVibrantSwatch();
        }
        if (palette.getDarkMutedSwatch() != null) {
            return palette.getDarkMutedSwatch();
        }
        if (palette.getLightMutedSwatch() != null) {
            return palette.getLightMutedSwatch();
        }
        if (palette.getMutedSwatch() != null) {
            return palette.getMutedSwatch();
        }

        return null;
    }
}

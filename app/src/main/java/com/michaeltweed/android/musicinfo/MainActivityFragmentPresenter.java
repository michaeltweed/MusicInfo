package com.michaeltweed.android.musicinfo;

import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by Michael on 06/12/2014.
 */
public class MainActivityFragmentPresenter {


    private final Bus bus;
    private final MainActivityFragmentView view;

    public MainActivityFragmentPresenter(Bus bus, MainActivityFragmentView view) {
        this.bus = bus;
        this.view = view;
        bus.register(this);
    }

    @Subscribe
    public void onSongChangedEvent(SongChangedEvent event) {
        String artist = event.getArtist();
        String album = event.getAlbum();
        String track = event.getTrack();

        view.updateText(track + " | " + album + " - " + artist);
    }

}

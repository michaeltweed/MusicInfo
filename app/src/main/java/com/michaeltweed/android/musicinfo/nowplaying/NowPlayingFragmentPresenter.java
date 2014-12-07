package com.michaeltweed.android.musicinfo.nowplaying;

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

    public void registerBus() {
        bus.register(this);
    }

    public void unRegisterBus() {
        bus.unregister(this);
    }

    @Subscribe
    public void onSongChangedEvent(SongChangedEvent event) {
        String artist = event.getArtist();
        String album = event.getAlbum();
        String track = event.getTrack();

        view.updateText(track + " | " + album + " - " + artist);
    }

}

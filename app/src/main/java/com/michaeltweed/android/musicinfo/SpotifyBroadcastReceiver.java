package com.michaeltweed.android.musicinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

public class SpotifyBroadcastReceiver extends BroadcastReceiver {

    private Bus bus;
    private String lastArtistName;
    private String lastAlbumName;
    private String lastTrackName;
    private SongChangedEvent lastEvent;

    public SpotifyBroadcastReceiver(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        lastArtistName = intent.getStringExtra("artist");
        lastAlbumName = intent.getStringExtra("album");
        lastTrackName = intent.getStringExtra("track");

        lastEvent = new SongChangedEvent(lastArtistName, lastAlbumName, lastTrackName);
        bus.post(lastEvent);
    }

    @Produce
    public SongChangedEvent produceLastSongChangedEvent() {
        return lastEvent;
    }
}

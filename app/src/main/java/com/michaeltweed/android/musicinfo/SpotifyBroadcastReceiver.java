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
    private String lsatTrackName;

    public SpotifyBroadcastReceiver(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        lastArtistName = intent.getStringExtra("artist");
        lastAlbumName = intent.getStringExtra("album");
        lsatTrackName = intent.getStringExtra("track");

        bus.post(new SongChangedEvent(lastArtistName, lastAlbumName, lsatTrackName));
    }

    @Produce
    public SongChangedEvent produceLastSongChangedEvent() {
        return new SongChangedEvent(lastArtistName, lastAlbumName, lsatTrackName);
    }
}

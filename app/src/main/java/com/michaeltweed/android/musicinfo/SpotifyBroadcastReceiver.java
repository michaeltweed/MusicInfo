package com.michaeltweed.android.musicinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;

public class SpotifyBroadcastReceiver extends BroadcastReceiver {

    private Bus bus;

    public SpotifyBroadcastReceiver(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String artistName = intent.getStringExtra("artist");
        String albumName = intent.getStringExtra("album");
        String trackName = intent.getStringExtra("track");

        bus.post(new SongChangedEvent(artistName, albumName, trackName));
    }
}

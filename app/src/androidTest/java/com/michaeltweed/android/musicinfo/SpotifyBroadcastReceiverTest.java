package com.michaeltweed.android.musicinfo;

import android.content.Intent;

import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;

import org.mockito.Mockito;

import static org.mockito.Matchers.isA;

public class SpotifyBroadcastReceiverTest extends ParentMusicInfoTestCase {
    private SpotifyBroadcastReceiver spotifyBroadcastReceiver;
    private Bus bus;

    public void setUp() throws Exception {
        super.setUp();
        bus = Mockito.mock(Bus.class);
        spotifyBroadcastReceiver = new SpotifyBroadcastReceiver(bus);
    }

    public void testCorrectEventPostedToBusWhenBroadcastReceived() {
        String artist = "testArtist";
        String album = "testAlbum";
        String track = "testTrack";

        Intent testIntent = new Intent();
        testIntent.putExtra("artist", artist);
        testIntent.putExtra("album", album);
        testIntent.putExtra("track", track);

        spotifyBroadcastReceiver.onReceive(null, testIntent);
        Mockito.verify(bus).post(new SongChangedEvent(artist, album, track));
    }
}
package com.michaeltweed.android.musicinfo;

import android.content.Intent;

import com.michaeltweed.android.musicinfo.apis.lastfm.LastFmInterface;
import com.michaeltweed.android.musicinfo.events.ArtistChangedEvent;
import com.michaeltweed.android.musicinfo.events.NoArtistInfoAvailableEvent;
import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;

import org.mockito.Mockito;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;

public class SpotifyBroadcastReceiverTest extends ParentMusicInfoTestCase {

    private SpotifyBroadcastReceiver spotifyBroadcastReceiver;
    private Bus bus;
    private LastFmInterface lastFmInterface;

    String testArtist = "testArtist";
    String testAlbum = "testAlbum";
    String testTrack = "testTrack";

    public void setUp() throws Exception {
        super.setUp();
        bus = Mockito.mock(Bus.class);
        lastFmInterface = Mockito.mock(LastFmInterface.class);
        spotifyBroadcastReceiver = new SpotifyBroadcastReceiver(bus, lastFmInterface);
    }

    public void testSongChangedEventPostedToBusWhenBroadcastReceived() {

        spotifyBroadcastReceiver.onReceive(null, getTestIntent(testArtist, testAlbum, testTrack));
        Mockito.verify(bus).post(new SongChangedEvent(testArtist, testAlbum, testTrack));
    }

    public void testArtistChangedActionsPerformedWhenBroadcastReceivedWithNewArtist() {

        spotifyBroadcastReceiver.onReceive(null, getTestIntent(testArtist, testAlbum, testTrack));

        Mockito.verify(bus).post(isA(ArtistChangedEvent.class));
        Mockito.verify(lastFmInterface).getArtistResponse(eq("artist.getinfo"), eq("testArtist"), eq("1"), eq(Constants.LAST_FM_USERNAME), eq(Constants.LAST_FM_API_KEY), eq("json"), isA(Callback.class));

        Mockito.reset(bus, lastFmInterface);

        spotifyBroadcastReceiver.onReceive(null, getTestIntent("testArtist2", testAlbum, testTrack));

        Mockito.verify(bus).post(isA(ArtistChangedEvent.class));
        Mockito.verify(lastFmInterface).getArtistResponse(eq("artist.getinfo"), eq("testArtist2"), eq("1"), eq(Constants.LAST_FM_USERNAME), eq(Constants.LAST_FM_API_KEY), eq("json"), isA(Callback.class));

    }

    public void testNoArtistChangedActionsPerformedWhenBroadcastReceivedWithSameArtist() {

        spotifyBroadcastReceiver.onReceive(null, getTestIntent(testArtist, testAlbum, testTrack));

        Mockito.verify(bus).post(isA(ArtistChangedEvent.class));
        Mockito.verify(lastFmInterface).getArtistResponse(eq("artist.getinfo"), eq("testArtist"), eq("1"), eq(Constants.LAST_FM_USERNAME), eq(Constants.LAST_FM_API_KEY), eq("json"), isA(Callback.class));

        Mockito.reset(bus, lastFmInterface);

        spotifyBroadcastReceiver.onReceive(null, getTestIntent(testArtist, "album2", "track2"));

        Mockito.verifyZeroInteractions(lastFmInterface);

        Mockito.verify(bus, never()).post(isA(ArtistChangedEvent.class));
    }

    public void testRetrofitLatestRequestSuccessPostsToBus() {
        spotifyBroadcastReceiver.onReceive(null, getTestIntent("David Bowie", testAlbum, testTrack));

        Mockito.reset(bus);

        spotifyBroadcastReceiver.success(null, getRetrofitResponseForArtist("David Bowie"));

        Mockito.verify(bus).post(anyObject());
    }

    public void testRetrofitNonLatestRequestSuccessDoesNotPostToBus() {
        spotifyBroadcastReceiver.onReceive(null, getTestIntent("Jimmy Reed", testAlbum, testTrack));

        Mockito.reset(bus);

        spotifyBroadcastReceiver.success(null, getRetrofitResponseForArtist("Big Bill Broonzy"));

        Mockito.verifyZeroInteractions(bus);
    }

    public void testRetrofitLatestRequestFailurePostsToBus() {
        spotifyBroadcastReceiver.onReceive(null, getTestIntent("Earl Hooker", testAlbum, testTrack));

        spotifyBroadcastReceiver.failure(getRetrofitErrorForArtist("Earl Hooker"));

        Mockito.verify(bus).post(isA(NoArtistInfoAvailableEvent.class));
    }

    public void testRetrofitNonLatestRequestFailureDoesNotPostToBus() {
        spotifyBroadcastReceiver.onReceive(null, getTestIntent("Little Walter", testAlbum, testTrack));

        spotifyBroadcastReceiver.failure(getRetrofitErrorForArtist("Albert Collins"));

        Mockito.verify(bus, never()).post(isA(NoArtistInfoAvailableEvent.class));
    }




    /* HELPER METHODS BELOW */

    private Intent getTestIntent(String artist, String album, String track) {
        Intent testIntent = new Intent();
        testIntent.putExtra("artist", artist);
        testIntent.putExtra("album", album);
        testIntent.putExtra("track", track);
        return testIntent;
    }

    private Response getRetrofitResponseForArtist(String artist) {
        try {
            artist = URLEncoder.encode(artist, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        return new Response(artist, 400, "", new ArrayList<Header>(), null);
    }

    private RetrofitError getRetrofitErrorForArtist(String artist) {
        try {
            artist = URLEncoder.encode(artist, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        return RetrofitError.networkError(artist, new IOException());
    }
}
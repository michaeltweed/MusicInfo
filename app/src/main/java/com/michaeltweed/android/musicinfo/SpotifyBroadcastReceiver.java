package com.michaeltweed.android.musicinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.michaeltweed.android.musicinfo.apis.lastfm.LastFmInterface;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.ArtistResponse;
import com.michaeltweed.android.musicinfo.artist.FilteredArtistResponse;
import com.michaeltweed.android.musicinfo.events.ArtistChangedEvent;
import com.michaeltweed.android.musicinfo.events.ArtistResponseEvent;
import com.michaeltweed.android.musicinfo.events.NoArtistInfoAvailableEvent;
import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SpotifyBroadcastReceiver extends BroadcastReceiver implements Callback<ArtistResponse> {

    private final Bus bus;
    private final LastFmInterface apiInterface;
    private String lastArtistName;

    private ArtistResponseEvent lastArtistResponseEvent;
    private NoArtistInfoAvailableEvent lastNoArtistInfoAvailableEvent;
    private SongChangedEvent lastSongChangedEvent;

    public SpotifyBroadcastReceiver(Bus bus, LastFmInterface apiInterface) {
        this.bus = bus;
        this.apiInterface = apiInterface;
    }

    //called by the system when the Spotify song changes
    @Override
    public void onReceive(Context context, Intent intent) {
        String intentArtist = intent.getStringExtra("artist");
        String intentAlbum = intent.getStringExtra("album");
        String intentTrack = intent.getStringExtra("track");

        lastSongChangedEvent = new SongChangedEvent(intentArtist, intentAlbum, intentTrack);
        bus.post(lastSongChangedEvent);

        if (!(intentArtist.equals(lastArtistName))) {
            //we only do something if the artist has changed
            onArtistChanged(intentArtist);
        }
    }

    private void onArtistChanged(String artistName) {
        lastArtistName = artistName;
        bus.post(new ArtistChangedEvent());

        //request data from the API
        apiInterface.getArtistResponse("artist.getinfo", lastArtistName, "1", Constants.LAST_FM_USERNAME, Constants.LAST_FM_API_KEY, "json", this);
    }

    @Override
    //Retrofit success
    public void success(ArtistResponse artistResponse, Response response) {

        //because there is no way to cancel Retrofit requests, we want to make sure
        //that this is the latest request (in case the user has changed artists multiple
        //times before a response came back)
        if (!isLatestRequest(response.getUrl())) {
            return;
        }

        FilteredArtistResponse filteredArtistResponse = new FilteredArtistResponse(artistResponse);

        if (filteredArtistResponse.isEmptyResponse()) {
            postNoArtistAvailableEventToBus();
        } else {
            lastArtistResponseEvent = new ArtistResponseEvent(filteredArtistResponse);
            bus.post(lastArtistResponseEvent);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        if (!isLatestRequest(error.getUrl())) {
            return;
        }

        postNoArtistAvailableEventToBus();
    }

    private boolean isLatestRequest(String url) {
        boolean isLatest = false;

        try {
            String encodedArtistName = URLEncoder.encode(lastArtistName, "UTF-8");
            isLatest = url.contains(encodedArtistName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return isLatest;
    }

    private void postNoArtistAvailableEventToBus() {
        lastNoArtistInfoAvailableEvent = new NoArtistInfoAvailableEvent(lastArtistName);
        bus.post(lastNoArtistInfoAvailableEvent);
    }

    @Produce
    public ArtistResponseEvent artistResponseEventProducer() {
        return lastArtistResponseEvent;
    }

    @Produce
    public NoArtistInfoAvailableEvent noArtistInfoAvailableEventProducer() {
        return lastNoArtistInfoAvailableEvent;
    }

    @Produce
    public SongChangedEvent songChangedEventProducer() {
        return lastSongChangedEvent;
    }
}

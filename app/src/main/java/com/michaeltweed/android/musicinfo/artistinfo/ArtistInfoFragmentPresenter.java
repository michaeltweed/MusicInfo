package com.michaeltweed.android.musicinfo.artistinfo;

import android.support.v7.graphics.Palette;

import com.michaeltweed.android.musicinfo.Constants;
import com.michaeltweed.android.musicinfo.apis.lastfm.LastFmInterface;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.ArtistResponse;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.Image;
import com.michaeltweed.android.musicinfo.events.PaletteAvailableEvent;
import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ArtistInfoFragmentPresenter implements Callback<ArtistResponse> {

    private final Bus bus;
    private ArtistInfoFragmentView view;
    private final LastFmInterface apiInterface;
    private String currentArtist;

    public ArtistInfoFragmentPresenter(Bus bus, ArtistInfoFragmentView view, LastFmInterface apiInterface) {
        this.bus = bus;
        this.view = view;
        this.apiInterface = apiInterface;
    }

    @Subscribe
    public void onSongChangedEvent(SongChangedEvent event) {
        currentArtist = event.getArtist();
        if (currentArtist == null) {
            return; //may have been called by producer at first with null values
        } else {
            view.setProgressBarVisibility(true);
            apiInterface.getArtistResponse("artist.getinfo", currentArtist, "1", "", Constants.LAST_FM_API_KEY, "json", this);
        }
    }

    @Override
    public void success(ArtistResponse artistResponse, Response response) {
        boolean isLatestRequest = false;

        try {
            String encodedArtistName = URLEncoder.encode(currentArtist, "UTF-8");
            isLatestRequest = response.getUrl().contains(encodedArtistName);
        } catch (UnsupportedEncodingException e) {
        }

        if (!isLatestRequest) {
            return;
        }
        try {
            String artistBio = artistResponse.getArtist().getBio().getContent();
            view.updateArtistBioText(artistBio);
            view.setBackgroundImageToUrl(getCorrectImageUrlForArtist(artistResponse.getArtist().getImages()));
            view.setProgressBarVisibility(false);
        } catch (NullPointerException e) {
            onNoDataAvailableForArtist();
        }
    }

    @Override
    public void failure(RetrofitError error) {
        onNoDataAvailableForArtist();
    }

    protected String getCorrectImageUrlForArtist(List<Image> imageList) {
        try {
            return imageList.get(imageList.size() - 1).getUrl();
        } catch (IndexOutOfBoundsException e) {
            throw new NullPointerException();
        }
    }

    private void onNoDataAvailableForArtist() {
        view.updateArtistBioText("No data available");
        view.setProgressBarVisibility(false);
    }

    public void paletteAvailable(Palette palette) {
        bus.post(new PaletteAvailableEvent(palette));
    }
}

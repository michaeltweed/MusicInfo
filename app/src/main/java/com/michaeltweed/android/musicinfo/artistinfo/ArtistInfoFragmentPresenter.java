package com.michaeltweed.android.musicinfo.artistinfo;

import com.michaeltweed.android.musicinfo.Constants;
import com.michaeltweed.android.musicinfo.apis.lastfm.LastFmInterface;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.ArtistResponse;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.Image;
import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Subscribe;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ArtistInfoFragmentPresenter implements Callback<ArtistResponse> {

    private ArtistInfoFragmentView view;
    private final LastFmInterface apiInterface;

    public ArtistInfoFragmentPresenter(ArtistInfoFragmentView view, LastFmInterface apiInterface) {
        this.view = view;
        this.apiInterface = apiInterface;
    }

    @Subscribe
    public void onSongChangedEvent(SongChangedEvent event) {
        String artist = event.getArtist();
        if (artist == null) {
            return; //may have been called by producer at first with null values
        } else {
            view.setProgressBarVisibility(true);
            apiInterface.getArtistResponse("artist.getinfo", artist, "1", "", Constants.LAST_FM_API_KEY, "json", this);
        }
    }

    @Override
    public void success(ArtistResponse artistResponse, Response response) {
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
}

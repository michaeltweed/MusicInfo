package com.michaeltweed.android.musicinfo.artist;

import com.michaeltweed.android.musicinfo.Constants;
import com.michaeltweed.android.musicinfo.apis.lastfm.LastFmInterface;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.ArtistResponse;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.Image;
import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ArtistFragmentPresenter implements Callback<ArtistResponse>{

    private final Bus bus;
    private ArtistFragmentView view;
    private final LastFmInterface apiInterface;
    private String currentArtist;

    public ArtistFragmentPresenter(Bus bus, ArtistFragmentView view, LastFmInterface apiInterface) {
        this.bus = bus;
        this.view = view;
        this.apiInterface = apiInterface;
    }

    @Subscribe
    public void onSongChangedEvent(SongChangedEvent event) {

        if (event.getArtist() == null || event.getArtist().equals(currentArtist)) {
            //null may have been called by producer at first with empty values
            //if the artist has not changed there is no need to make a new request
            return;
        } else {
            currentArtist = event.getArtist();
            view.setProgressBarVisibility(true);
            apiInterface.getArtistResponse("artist.getinfo", currentArtist, "1", Constants.LAST_FM_USERNAME, Constants.LAST_FM_API_KEY, "json", this);
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
            view.updateArtistPlayCount(getCorrectPlayCountText(artistResponse.getArtist().getStats().getUserplaycount()));
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

    private String getCorrectPlayCountText(String artistPlayCount) {
        if (artistPlayCount == null) {
            return "You need to sign in to view personal statistics";
        }

        if (artistPlayCount.equals("0")) {
            return "This is your first time listening to this artist";
        }

        if (artistPlayCount.equals("1")) {
            return "You have listened to this artist " + artistPlayCount + " time";
        }

        return "You have listened to this artist " + artistPlayCount + " times";
    }
}

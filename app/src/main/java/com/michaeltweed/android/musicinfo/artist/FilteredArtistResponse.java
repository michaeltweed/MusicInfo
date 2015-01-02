package com.michaeltweed.android.musicinfo.artist;

import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.ArtistResponse;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.Image;

import java.util.List;

public class FilteredArtistResponse {
    private final String artistBio;
    private final String artistPlayCount;
    private final String artistImageUrl;

    private final static String DEFAULT_BIO = "No artist biography available";
    private final static String DEFAULT_PLAY_COUNT = "No personal statistics available";


    public FilteredArtistResponse(ArtistResponse artistResponse) {
        artistBio = getCorrectArtistBio(artistResponse);
        artistPlayCount = getCorrectArtistPlayCount(artistResponse);
        artistImageUrl = getCorrectArtistImageUrl(artistResponse);
    }

    public FilteredArtistResponse(String artistBio, String artistPlayCount, String artistImageUrl) {
        this.artistBio = artistBio;
        this.artistPlayCount = artistPlayCount;
        this.artistImageUrl = artistImageUrl;
    }

    public String getArtistBio() {
        return artistBio;
    }

    public String getArtistPlayCount() {
        return artistPlayCount;
    }

    public String getArtistImageUrl() {
        return artistImageUrl;
    }

    public boolean isEmptyResponse() {
        return (!hasArtistBio() && !hasArtistPlayCount() && !hasArtistImageUrl());
    }

    public boolean hasArtistImageUrl() {
        return (artistImageUrl != null && !(artistImageUrl.isEmpty()));
    }

    private boolean hasArtistBio() {
        return (artistBio != null && !artistBio.isEmpty() && !(DEFAULT_BIO.equals(artistBio)));
    }

    private boolean hasArtistPlayCount() {
        return (artistPlayCount != null && !artistPlayCount.isEmpty() && !(DEFAULT_PLAY_COUNT.equals(artistPlayCount)));
    }

    private String getCorrectArtistBio(ArtistResponse artistResponse) {
        String bioToReturn = DEFAULT_BIO;

        try {
            String artistBio = artistResponse.getArtist().getBio().getContent();
            if (artistBio != null && !(artistBio.isEmpty())) {
                bioToReturn = artistBio;
            }
        } catch (Exception e) {
            //data was not available
        }

        return bioToReturn;
    }

    private String getCorrectArtistPlayCount(ArtistResponse artistResponse) {
        String playCount = "No personal statistics available";

        try {
            String artistPlayCount = artistResponse.getArtist().getStats().getUserplaycount();

            if (!artistPlayCount.isEmpty()) {
                if (artistPlayCount.equals("1")) {
                    playCount = "You have listened to this artist " + artistPlayCount + " time";
                } else {
                    playCount = "You have listened to this artist " + artistPlayCount + " times";
                }
            }

        } catch (Exception e) {
            //data was not available
        }

        return playCount;
    }

    private String getCorrectArtistImageUrl(ArtistResponse artistResponse) {

        try {
            List<Image> imageList = artistResponse.getArtist().getImages();
            String artistImageUrl = imageList.get(imageList.size() - 1).getUrl();
            if (!artistImageUrl.isEmpty()) {
                return artistImageUrl;
            }
        } catch (Exception e) {
            //data was not available
        }

        return null;
    }

}

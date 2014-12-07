package com.michaeltweed.android.musicinfo.apis.lastfm;

import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.ArtistResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface LastFmInterface {
    @GET("/2.0/")
    void getArtistResponse(@Query("method") String method, @Query("artist") String artist, @Query("autocorrect") String autocorrect, @Query("username") String username, @Query("api_key") String apiKey, @Query("format") String format, Callback<ArtistResponse> callback);
}

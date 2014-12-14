package com.michaeltweed.android.musicinfo.artist;

public interface ArtistFragmentView {
    void updateArtistBioText(String text);

    void updateArtistPlayCount(String text);

    void setProgressBarVisibility(boolean shouldShow);

    void setBackgroundImageToUrl(String url);
}

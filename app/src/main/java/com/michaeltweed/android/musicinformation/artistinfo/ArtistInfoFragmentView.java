package com.michaeltweed.android.musicinformation.artistinfo;

public interface ArtistInfoFragmentView {
    void updateArtistBioText(String text);

    void updateArtistPlayCount(String text);

    void setProgressBarVisibility(boolean shouldShow);

    void setBackgroundImageToUrl(String url);
}

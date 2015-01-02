package com.michaeltweed.android.musicinfo.artist.info;

public interface ArtistInfoFragmentView {

    void updateArtistBioText(String text);

    void updateArtistPlayCount(String text);

    void setArtistPlayCountVisibility(boolean shouldShow);
}

package com.michaeltweed.android.musicinfo.nowplaying;

/**
 * Created by Michael on 06/12/2014.
 */
public interface NowPlayingFragmentView {
    void updateSongText(String toDisplay);

    void updateAlbumArtistText(String toDisplay);

    void updateBackgroundColor(int color);

    void updateTextColor(int color);

    void updateTitleColor(int color);
}

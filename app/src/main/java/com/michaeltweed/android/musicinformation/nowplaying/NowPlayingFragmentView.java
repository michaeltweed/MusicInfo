package com.michaeltweed.android.musicinformation.nowplaying;

/**
 * Created by Michael on 06/12/2014.
 */
public interface NowPlayingFragmentView {
    void updateText(String toDisplay);

    void updateBackgroundColor(int color);

    void updateTextColor(int color);

    void updateTitleColor(int color);
}

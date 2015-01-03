package com.michaeltweed.android.musicinfo;

public interface LastFmUsernameView {
    void updateUsername(String username);

    void setTextBoxEnabled(boolean isEnabled);

    void showToastWithMessage(String message);
}

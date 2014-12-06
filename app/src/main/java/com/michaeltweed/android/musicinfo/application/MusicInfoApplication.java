package com.michaeltweed.android.musicinfo.application;

import android.app.Application;

import com.squareup.otto.Bus;

/**
 * Created by Michael on 06/12/2014.
 */
public class MusicInfoApplication extends Application {
    private Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new Bus();
    }

    public Bus getBus() {
        return bus;
    }
}

package com.michaeltweed.android.musicinfo;

import com.squareup.otto.Bus;

public final class BusSingleton {
    private static final Bus BUS = new Bus();

    public static Bus getBus() {
        return BUS;
    }

    private BusSingleton() {
        // No instances.
    }
}
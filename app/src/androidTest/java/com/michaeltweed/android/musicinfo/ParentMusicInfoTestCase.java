package com.michaeltweed.android.musicinfo;

import android.test.AndroidTestCase;

public class ParentMusicInfoTestCase extends AndroidTestCase {

    public void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getContext().getCacheDir().getPath());
        Thread.currentThread().setContextClassLoader(
                getClass().getClassLoader());
    }
}
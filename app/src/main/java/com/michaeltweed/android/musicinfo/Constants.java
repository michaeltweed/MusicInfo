package com.michaeltweed.android.musicinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Constants {
    /* This class contains important information such as API keys
       and any other required credentials. You will have to get these for yourself :)
     */

    public static String getLastFmApiUrl() {
        return System.getenv().get("lastFmApiUrl");
    }

    public static String getLastFmApiKey() {
        return System.getenv().get("lastFmApiKey");
    }

    public static String getSharedPrefsFileName() {
        return System.getenv().get("sharedPrefsFileName");
    }

    public static LinkedHashMap<String, ArrayList<String>> getIntentFilterHashMap() {
        LinkedHashMap<String, ArrayList<String>> hashMap = new LinkedHashMap<>();

        hashMap.put("Default/Play Music", new ArrayList<>(Arrays.asList("com.android.music.metachanged", "com.android.music.playstatechanged", "com.android.music.playbackcomplete","com.android.music.queuechanged")));
        hashMap.put("Spotify", new ArrayList<>(Arrays.asList("com.spotify.music.metadatachanged")));
        hashMap.put("HTC Music", new ArrayList<>(Arrays.asList("com.htc.music.metachanged")));
        hashMap.put("Last.fm", new ArrayList<>(Arrays.asList("fm.last.android.metachanged")));
        hashMap.put("Samsung Music", new ArrayList<>(Arrays.asList("com.sec.android.app.music.metachanged", "com.samsung.sec.android.MusicPlayer.metachanged")));
        hashMap.put("Winamp", new ArrayList<>(Arrays.asList("com.nullsoft.winamp.metachanged")));
        hashMap.put("Amazon Music", new ArrayList<>(Arrays.asList("com.amazon.mp3.metachanged")));
        hashMap.put("MIUI Music", new ArrayList<>(Arrays.asList("com.miui.player.metachanged")));
        hashMap.put("Real Player", new ArrayList<>(Arrays.asList("com.real.IMP.metachanged")));
        hashMap.put("Sony Ericsson Music", new ArrayList<>(Arrays.asList("com.sonyericsson.music.metachanged")));
        hashMap.put("Rdio Player", new ArrayList<>(Arrays.asList("com.rdio.android.metachanged")));
        hashMap.put("Apollo Music", new ArrayList<>(Arrays.asList("com.andrew.apollo.metachanged")));

        return hashMap;
    }

}

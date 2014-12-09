package com.michaeltweed.android.musicinformation;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.michaeltweed.android.musicinformation.artistinfo.ArtistInfoFragment;
import com.michaeltweed.android.musicinformation.nowplaying.NowPlayingFragment;


public class MainActivity extends FragmentActivity {

    private SpotifyBroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        setUpBroadcastReceiver();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.now_playing_fragment, new NowPlayingFragment());
        ft.replace(R.id.artist_info_fragment, new ArtistInfoFragment());
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        BusSingleton.getBus().unregister(receiver);
    }

    private void setUpBroadcastReceiver() {
        receiver = new SpotifyBroadcastReceiver(BusSingleton.getBus());
        BusSingleton.getBus().register(receiver);
        registerReceiver(receiver, new IntentFilter("com.spotify.music.metadatachanged"));
    }

}

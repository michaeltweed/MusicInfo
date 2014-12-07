package com.michaeltweed.android.musicinfo;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.michaeltweed.android.musicinfo.nowplaying.NowPlayingFragment;


public class MainActivity extends FragmentActivity {

    private SpotifyBroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        setUpBroadcastReceiver();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, new NowPlayingFragment());
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        receiver.unRegisterBus();
    }

    private void setUpBroadcastReceiver() {
        receiver = new SpotifyBroadcastReceiver(BusSingleton.getBus());
        receiver.registerBus();
        registerReceiver(receiver, new IntentFilter("com.spotify.music.metadatachanged"));
    }

}

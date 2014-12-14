package com.michaeltweed.android.musicinfo;

import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.michaeltweed.android.musicinfo.artist.ArtistFragment;
import com.michaeltweed.android.musicinfo.nowplaying.NowPlayingFragment;


public class MainActivity extends FragmentActivity {

    private SpotifyBroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        setContentView(R.layout.main_activity_layout);

        setUpBroadcastReceiver();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.now_playing_fragment, new NowPlayingFragment());
        ft.replace(R.id.artist_info_fragment, new ArtistFragment());
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

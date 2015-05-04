package com.michaeltweed.android.musicinfo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.michaeltweed.android.musicinfo.apis.lastfm.LastFmInterface;
import com.michaeltweed.android.musicinfo.artist.ArtistFragment;
import com.michaeltweed.android.musicinfo.nowplaying.NowPlayingFragment;

import retrofit.RestAdapter;


public class MainActivity extends ActionBarActivity {

    private SpotifyBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        setContentView(R.layout.main_activity_layout);

        setUpBroadcastReceiver();

        setUpTestClickListeners();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.now_playing_fragment, new NowPlayingFragment());
        ft.replace(R.id.artist_info_fragment, new ArtistFragment());
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettings() {
        DialogFragment newFragment = new SettingsFragment();
        newFragment.show(getSupportFragmentManager(), "settings");
    }

    private void setUpTestClickListeners() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTestBroadcastOne();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTestBroadcastTwo();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        BusSingleton.getBus().unregister(receiver);
    }

    private void setUpBroadcastReceiver() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constants.getLastFmApiUrl()).build();

        receiver = new SpotifyBroadcastReceiver(BusSingleton.getBus(), restAdapter.create(LastFmInterface.class));
        BusSingleton.getBus().register(receiver);
        registerReceiver(receiver, new IntentFilter("com.spotify.music.metadatachanged"));
    }

    private void sendTestBroadcastOne() {
        String artist = "Van Morrison";
        String album = "testAlbum";
        String track = "testTrack";

        Intent testIntent = new Intent();
        testIntent.putExtra("artist", artist);
        testIntent.putExtra("album", album);
        testIntent.putExtra("track", track);

        receiver.onReceive(null, testIntent);
    }

    private void sendTestBroadcastTwo() {
        String artist = "Bruce Springsteen";
        String album = "testAlbum";
        String track = "testTrack";

        Intent testIntent = new Intent();
        testIntent.putExtra("artist", artist);
        testIntent.putExtra("album", album);
        testIntent.putExtra("track", track);

        receiver.onReceive(null, testIntent);
    }

}

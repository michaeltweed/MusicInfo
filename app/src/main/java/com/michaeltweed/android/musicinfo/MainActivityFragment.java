package com.michaeltweed.android.musicinfo;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivityFragment extends MusicInfoBaseFragment implements MainActivityFragmentView {

    private MainActivityFragmentPresenter presenter;
    private TextView currentlyPlayingTextView;

    private SpotifyBroadcastReceiver receiver;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        currentlyPlayingTextView = (TextView) rootView.findViewById(R.id.currently_playing_textview);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (presenter == null) {
            presenter = new MainActivityFragmentPresenter(getBus(), this);
        }

        if (receiver == null) {
            receiver = new SpotifyBroadcastReceiver(getBus());
        }

        getActivity().registerReceiver(receiver, new IntentFilter("com.spotify.music.metadatachanged"));
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void updateText(String toDisplay) {
        currentlyPlayingTextView.setText(toDisplay);
    }
}
package com.michaeltweed.android.musicinfo.nowplaying;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michaeltweed.android.musicinfo.BusSingleton;
import com.michaeltweed.android.musicinfo.R;

public class NowPlayingFragment extends Fragment implements NowPlayingFragmentView {

    private NowPlayingFragmentPresenter presenter;
    private TextView currentlyPlayingTextView;

    public NowPlayingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NowPlayingFragmentPresenter(BusSingleton.getBus(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.now_playing_fragment_layout, container, false);
        currentlyPlayingTextView = (TextView) rootView.findViewById(R.id.currently_playing_textview);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusSingleton.getBus().register(presenter);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusSingleton.getBus().unregister(presenter);
    }

    @Override
    public void updateText(String toDisplay) {
        currentlyPlayingTextView.setText(toDisplay);
    }
}
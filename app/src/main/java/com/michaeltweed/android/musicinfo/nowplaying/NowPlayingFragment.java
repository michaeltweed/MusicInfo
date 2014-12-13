package com.michaeltweed.android.musicinfo.nowplaying;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michaeltweed.android.musicinfo.BusSingleton;
import com.michaeltweed.android.musicinfo.R;

import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

public class NowPlayingFragment extends Fragment implements NowPlayingFragmentView {

    private NowPlayingFragmentPresenter presenter;
    private TextView currentlyPlayingTextView;
    private View rootView;
    private TextView titleTextView;

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
        rootView = inflater.inflate(R.layout.now_playing_fragment_layout, container, false);
        titleTextView = (TextView) rootView.findViewById(R.id.currently_playing_title_textview);
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

    @Override
    public void updateBackgroundColor(int color) {
        rootView.setBackgroundColor(color);

        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void updateTextColor(int color) {
        currentlyPlayingTextView.setTextColor(color);
    }

    @Override
    public void updateTitleColor(int color) {
        titleTextView.setTextColor(color);
    }
}
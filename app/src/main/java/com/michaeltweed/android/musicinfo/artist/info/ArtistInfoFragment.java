package com.michaeltweed.android.musicinfo.artist.info;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michaeltweed.android.musicinfo.BusSingleton;
import com.michaeltweed.android.musicinfo.R;

public class ArtistInfoFragment extends Fragment implements ArtistInfoFragmentView {

    private TextView artistInfoTextView;
    private TextView artistPlayCountTextView;

    private ArtistInfoFragmentPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ArtistInfoFragmentPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.artist_info_fragment_layout, container, false);
        artistInfoTextView = (TextView) rootView.findViewById(R.id.artist_info_textview);
        artistPlayCountTextView = (TextView) rootView.findViewById(R.id.artist_play_count_textview);


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
    public void updateArtistBioText(String text) {
        setCorrectArtistBioText(text);
    }


    @Override
    public void updateArtistPlayCount(String text) {
        artistPlayCountTextView.setText(text);
    }

    @Override
    public void setArtistPlayCountVisibility(boolean shouldShow) {
        artistPlayCountTextView.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    private void setCorrectArtistBioText(String text) {
        artistInfoTextView.setText(Html.fromHtml(text));
        Linkify.addLinks(artistInfoTextView, Linkify.WEB_URLS);
    }
}

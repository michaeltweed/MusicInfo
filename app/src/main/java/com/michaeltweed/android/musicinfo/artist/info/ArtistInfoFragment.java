package com.michaeltweed.android.musicinfo.artist.info;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michaeltweed.android.musicinfo.R;

public class ArtistInfoFragment extends Fragment {

    private TextView artistInfoTextView;
    private TextView artistPlayCountTextView;

    private String bioToSet;
    private String countToSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.artist_info_fragment_layout, container, false);
        artistInfoTextView = (TextView) rootView.findViewById(R.id.artist_info_textview);
        artistPlayCountTextView = (TextView) rootView.findViewById(R.id.artist_play_count_textview);

        if (bioToSet != null) {
            setCorrectArtistBioText(bioToSet);
        }


        if (countToSet != null) {
            artistPlayCountTextView.setText(countToSet);
        }


        return rootView;
    }

    public void updateArtistBioText(String text) {
        if (artistInfoTextView == null) {
            bioToSet = text;
        } else {
            setCorrectArtistBioText(text);
        }
    }

    private void setCorrectArtistBioText(String text) {
        artistInfoTextView.setText(Html.fromHtml(text));
        Linkify.addLinks(artistInfoTextView, Linkify.WEB_URLS);
    }

    public void updateArtistPlayCount(String text) {
        if (artistPlayCountTextView == null) {
            countToSet = text;
        } else {
            artistPlayCountTextView.setText(text);
        }
    }


}

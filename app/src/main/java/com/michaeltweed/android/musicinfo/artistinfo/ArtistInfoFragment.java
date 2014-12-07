package com.michaeltweed.android.musicinfo.artistinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.michaeltweed.android.musicinfo.BusSingleton;
import com.michaeltweed.android.musicinfo.Constants;
import com.michaeltweed.android.musicinfo.R;
import com.michaeltweed.android.musicinfo.apis.lastfm.LastFmInterface;

import retrofit.RestAdapter;

public class ArtistInfoFragment extends Fragment implements ArtistInfoFragmentView {

    private TextView artistInfoTextView;
    private ArtistInfoFragmentPresenter presenter;
    private ProgressBar progressBar;

    public ArtistInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constants.LAST_FM_API_URL).build();

        presenter = new ArtistInfoFragmentPresenter(this, restAdapter.create(LastFmInterface.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_info_fragment_layout, container, false);
        artistInfoTextView = (TextView) rootView.findViewById(R.id.artist_info_textview);
        progressBar = (ProgressBar) rootView.findViewById(R.id.artist_info_progress);

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
        artistInfoTextView.setText(Html.fromHtml(text));
        Linkify.addLinks(artistInfoTextView, Linkify.WEB_URLS);
    }

    @Override
    public void setProgressBarVisibility(boolean shouldShow) {
        if (shouldShow) {
            artistInfoTextView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            artistInfoTextView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
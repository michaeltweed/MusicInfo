package com.michaeltweed.android.musicinfo.artist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.michaeltweed.android.musicinfo.BusSingleton;
import com.michaeltweed.android.musicinfo.Constants;
import com.michaeltweed.android.musicinfo.R;
import com.michaeltweed.android.musicinfo.apis.lastfm.LastFmInterface;
import com.michaeltweed.android.musicinfo.artist.image.ArtistImageFragment;
import com.michaeltweed.android.musicinfo.artist.info.ArtistInfoFragment;

import retrofit.RestAdapter;

public class ArtistFragment extends Fragment implements ArtistFragmentView {

    private ViewPager viewPager;
    private ArtistFragmentPresenter presenter;
    private ArtistImageFragment imageFragment;
    private ArtistInfoFragment infoFragment;
    private ArtistViewPagerAdapter adapter;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constants.LAST_FM_API_URL).build();

        presenter = new ArtistFragmentPresenter(BusSingleton.getBus(), this, restAdapter.create(LastFmInterface.class));

        imageFragment = new ArtistImageFragment();
        infoFragment = new ArtistInfoFragment();

        adapter = new ArtistViewPagerAdapter(getChildFragmentManager(), imageFragment, infoFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_fragment_layout, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.artist_fragment_viewpager);
        progressBar = (ProgressBar) rootView.findViewById(R.id.artist_fragment_progress);

        viewPager.setAdapter(adapter);

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
        infoFragment.updateArtistBioText(text);
    }

    @Override
    public void updateArtistPlayCount(String text) {
        infoFragment.updateArtistPlayCount(text);
    }

    @Override
    public void setProgressBarVisibility(boolean shouldShow) {
        if (shouldShow) {
            viewPager.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            viewPager.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setBackgroundImageToUrl(String url) {
        imageFragment.setBackgroundImageToUrl(url);
    }
}

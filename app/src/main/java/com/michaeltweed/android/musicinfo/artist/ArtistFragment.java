package com.michaeltweed.android.musicinfo.artist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.michaeltweed.android.musicinfo.BusSingleton;
import com.michaeltweed.android.musicinfo.R;
import com.michaeltweed.android.musicinfo.utils.DepthPageTransformer;

public class ArtistFragment extends Fragment implements ArtistFragmentView {

    private ViewPager viewPager;
    private ArtistFragmentPresenter presenter;
    private ArtistViewPagerAdapter adapter;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ArtistFragmentPresenter(this);

        adapter = new ArtistViewPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_fragment_layout, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.artist_fragment_viewpager);
        progressBar = (ProgressBar) rootView.findViewById(R.id.artist_fragment_progress);

        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());

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
    public void setProgressBarVisibility(boolean shouldShow) {
        if (shouldShow) {
            viewPager.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            viewPager.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

}

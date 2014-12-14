package com.michaeltweed.android.musicinfo.artist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.michaeltweed.android.musicinfo.artist.image.ArtistImageFragment;
import com.michaeltweed.android.musicinfo.artist.info.ArtistInfoFragment;

public class ArtistViewPagerAdapter extends FragmentPagerAdapter {

    private final ArtistImageFragment imageFragment;
    private final ArtistInfoFragment infoFragment;

    public ArtistViewPagerAdapter(FragmentManager fm, ArtistImageFragment imageFragment, ArtistInfoFragment infoFragment) {
        super(fm);
        this.imageFragment = imageFragment;
        this.infoFragment = infoFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return imageFragment;
        }

        return infoFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

package com.michaeltweed.android.musicinfo.artist;

import android.support.v4.app.FragmentManager;

import com.michaeltweed.android.musicinfo.ParentMusicInfoTestCase;
import com.michaeltweed.android.musicinfo.artist.image.ArtistImageFragment;
import com.michaeltweed.android.musicinfo.artist.info.ArtistInfoFragment;

import org.mockito.Mockito;

public class ArtistViewPagerAdapterTest extends ParentMusicInfoTestCase {

    private ArtistViewPagerAdapter adapter;

    public void setUp() throws Exception {
        super.setUp();

        adapter = new ArtistViewPagerAdapter(Mockito.mock(FragmentManager.class));
    }

    public void testGetViewReturnsCorrectInteger() {
        assertEquals(2, adapter.getCount());
    }

    public void testGetItemReturnsCorrectFragment() {
        assertEquals(true, adapter.getItem(0) instanceof ArtistImageFragment);
        assertEquals(true, adapter.getItem(1) instanceof ArtistInfoFragment);
    }
}
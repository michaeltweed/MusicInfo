package com.michaeltweed.android.musicinfo.artist;

import android.support.v4.app.FragmentManager;

import com.michaeltweed.android.musicinfo.artist.image.ArtistImageFragment;
import com.michaeltweed.android.musicinfo.artist.info.ArtistInfoFragment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class ArtistViewPagerAdapterTest {

    private ArtistViewPagerAdapter adapter;

    @Before
    public void setUp() {
        adapter = new ArtistViewPagerAdapter(Mockito.mock(FragmentManager.class));
    }

    @Test
    public void testGetViewReturnsCorrectInteger() {
        assertEquals(2, adapter.getCount());
    }

    @Test
    public void testGetItemReturnsCorrectFragment() {
        assertEquals(true, adapter.getItem(0) instanceof ArtistImageFragment);
        assertEquals(true, adapter.getItem(1) instanceof ArtistInfoFragment);
    }
}
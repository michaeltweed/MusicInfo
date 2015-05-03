package com.michaeltweed.android.musicinfo.nowplaying;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.michaeltweed.android.musicinfo.events.PaletteAvailableEvent;
import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class NowPlayingFragmentPresenterTest {
    private NowPlayingFragmentPresenter presenter;
    private Bus bus;
    private NowPlayingFragmentView view;

    @Before
    public void setUp() {
        bus = Mockito.mock(Bus.class);
        view = Mockito.mock(NowPlayingFragmentView.class);
        presenter = new NowPlayingFragmentPresenter(bus, view);
    }

    @Test
    public void testReceivingSongChangedEventUpdatesViewCorrectly() {
        SongChangedEvent event = new SongChangedEvent("Bob Dylan", "Bringing It All Back Home", "Bob Dylan's 115th Dream");
        presenter.onSongChangedEvent(event);
        Mockito.verify(view).updateSongText("Bob Dylan's 115th Dream");
        Mockito.verify(view).updateAlbumArtistText("Bringing It All Back Home - Bob Dylan");
    }

    @Test
    public void testReceivingPaletteChangedEventUpdatesViewCorrectly() {

        PaletteAvailableEvent event = new PaletteAvailableEvent(Palette.generate(Mockito.mock(Bitmap.class)));

        presenter.onPaletteAvailableEvent(event);
        Mockito.verify(view).updateBackgroundColor(Mockito.anyInt());
        Mockito.verify(view).updateTitleColor(Mockito.anyInt());
        Mockito.verify(view).updateTextColor(Mockito.anyInt());
    }

}
package com.michaeltweed.android.musicinfo.nowplaying;

import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;

import com.michaeltweed.android.musicinfo.ParentMusicInfoTestCase;
import com.michaeltweed.android.musicinfo.R;
import com.michaeltweed.android.musicinfo.events.PaletteAvailableEvent;
import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;

import org.mockito.Mockito;

public class NowPlayingFragmentPresenterTest extends ParentMusicInfoTestCase {
    private NowPlayingFragmentPresenter presenter;
    private Bus bus;
    private NowPlayingFragmentView view;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        bus = Mockito.mock(Bus.class);
        view = Mockito.mock(NowPlayingFragmentView.class);
        presenter = new NowPlayingFragmentPresenter(bus, view);
    }

    public void testReceivingSongChangedEventUpdatesViewCorrectly() {
        SongChangedEvent event = new SongChangedEvent("Bob Dylan", "Bringing It All Back Home", "Bob Dylan's 115th Dream");
        presenter.onSongChangedEvent(event);
        Mockito.verify(view).updateSongText("Bob Dylan's 115th Dream");
        Mockito.verify(view).updateAlbumArtistText("Bringing It All Back Home - Bob Dylan");
    }

    public void testReceivingPaletteChangedEventUpdatesViewCorrectly() {
        PaletteAvailableEvent event = new PaletteAvailableEvent(Palette.generate(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher)));

        presenter.onPaletteAvailableEvent(event);
        Mockito.verify(view).updateBackgroundColor(Mockito.anyInt());
        Mockito.verify(view).updateTitleColor(Mockito.anyInt());
        Mockito.verify(view).updateTextColor(Mockito.anyInt());
    }



}
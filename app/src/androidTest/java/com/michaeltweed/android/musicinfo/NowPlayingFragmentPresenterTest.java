package com.michaeltweed.android.musicinfo;

import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.michaeltweed.android.musicinfo.nowplaying.NowPlayingFragmentPresenter;
import com.michaeltweed.android.musicinfo.nowplaying.NowPlayingFragmentView;
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

    public void testReceivingEventUpdatesViewCorrectly() {
        SongChangedEvent event = new SongChangedEvent("Bob Dylan", "Bringing It All Back Home", "Bob Dylan's 115th Dream");
        presenter.onSongChangedEvent(event);
        Mockito.verify(view).updateText("Bob Dylan's 115th Dream | Bringing It All Back Home - Bob Dylan");
    }

}
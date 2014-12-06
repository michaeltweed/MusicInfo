package com.michaeltweed.android.musicinfo;

import com.michaeltweed.android.musicinfo.events.SongChangedEvent;
import com.squareup.otto.Bus;

import junit.framework.TestCase;

import org.mockito.Mockito;

public class MainActivityFragmentPresenterTest extends ParentMusicInfoTestCase {
    private MainActivityFragmentPresenter presenter;
    private Bus bus;
    private MainActivityFragmentView view;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        bus = Mockito.mock(Bus.class);
        view = Mockito.mock(MainActivityFragmentView.class);
        presenter = new MainActivityFragmentPresenter(bus, view);
    }

    public void testReceivingEventUpdatesViewCorrectly() {
        SongChangedEvent event = new SongChangedEvent("Bob Dylan", "Bringing It All Back Home", "Bob Dylan's 115th Dream");
        presenter.onSongChangedEvent(event);
        Mockito.verify(view).updateText("Bob Dylan's 115th Dream | Bringing It All Back Home - Bob Dylan");
    }

}
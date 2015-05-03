package com.michaeltweed.android.musicinfo.artist;

import com.michaeltweed.android.musicinfo.events.ArtistResponseEvent;
import com.michaeltweed.android.musicinfo.events.DataChangedEvent;
import com.michaeltweed.android.musicinfo.events.NoArtistInfoAvailableEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ArtistFragmentPresenterTest {
    private ArtistFragmentView view;
    private ArtistFragmentPresenter presenter;

    @Before
    public void setUp() {
        view = Mockito.mock(ArtistFragmentView.class);
        presenter = new ArtistFragmentPresenter(view);
    }

    @Test
    public void testViewIsNotUpdatedWhenNullArtistChangedEventReceived() {
        presenter.onArtistChangedEvent(null);

        Mockito.verifyZeroInteractions(view);
    }

    @Test
    public void testViewIsNotUpdatedWithNullArtistResponse() {
        presenter.onArtistResponseEvent(null);

        Mockito.verifyZeroInteractions(view);
    }

    @Test
    public void testViewIsNotUpdatedWhenNullNoArtistEventReceived() {
        presenter.onNoArtistInfoAvailableEvent(null);

        Mockito.verifyZeroInteractions(view);
    }

    @Test
    public void testViewIsUpdatedWhenValidArtistChangedEventReceived() {
        presenter.onArtistChangedEvent(new DataChangedEvent());

        Mockito.verify(view).setProgressBarVisibility(true);
    }

    @Test
    public void testViewIsUpdatedWhenValidArtistResponseReceived() {
        presenter.onArtistResponseEvent(new ArtistResponseEvent(new FilteredArtistResponse(null, null, null)));

        Mockito.verify(view).setProgressBarVisibility(false);
    }

    @Test
    public void testViewIsUpdatedWhenValidNoArtistEventReceived() {
        presenter.onNoArtistInfoAvailableEvent(new NoArtistInfoAvailableEvent("Test"));

        Mockito.verify(view).setProgressBarVisibility(false);
    }

}
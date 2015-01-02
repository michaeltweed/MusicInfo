package com.michaeltweed.android.musicinfo.artist;

import com.michaeltweed.android.musicinfo.ParentMusicInfoTestCase;
import com.michaeltweed.android.musicinfo.events.ArtistChangedEvent;
import com.michaeltweed.android.musicinfo.events.ArtistResponseEvent;
import com.michaeltweed.android.musicinfo.events.NoArtistInfoAvailableEvent;

import org.mockito.Mockito;

public class ArtistFragmentPresenterTest extends ParentMusicInfoTestCase {
    private ArtistFragmentView view;
    private ArtistFragmentPresenter presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        view = Mockito.mock(ArtistFragmentView.class);
        presenter = new ArtistFragmentPresenter(view);
    }

    public void testViewIsNotUpdatedWhenNullArtistChangedEventReceived() {
        presenter.onArtistChangedEvent(null);

        Mockito.verifyZeroInteractions(view);
    }

    public void testViewIsNotUpdatedWithNullArtistResponse() {
        presenter.onArtistResponseEvent(null);

        Mockito.verifyZeroInteractions(view);
    }

    public void testViewIsNotUpdatedWhenNullNoArtistEventReceived() {
        presenter.onNoArtistInfoAvailableEvent(null);

        Mockito.verifyZeroInteractions(view);
    }

    public void testViewIsUpdatedWhenValidArtistChangedEventReceived() {
        presenter.onArtistChangedEvent(new ArtistChangedEvent());

        Mockito.verify(view).setProgressBarVisibility(true);
    }

    public void testViewIsUpdatedWhenValidArtistResponseReceived() {
        presenter.onArtistResponseEvent(new ArtistResponseEvent(new FilteredArtistResponse(null, null, null)));

        Mockito.verify(view).setProgressBarVisibility(false);
    }

    public void testViewIsUpdatedWhenValidNoArtistEventReceived() {
        presenter.onNoArtistInfoAvailableEvent(new NoArtistInfoAvailableEvent("Test"));

        Mockito.verify(view).setProgressBarVisibility(false);
    }

}
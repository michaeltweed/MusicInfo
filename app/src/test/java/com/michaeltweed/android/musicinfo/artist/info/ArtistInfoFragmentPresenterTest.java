package com.michaeltweed.android.musicinfo.artist.info;

import com.michaeltweed.android.musicinfo.artist.FilteredArtistResponse;
import com.michaeltweed.android.musicinfo.events.ArtistResponseEvent;
import com.michaeltweed.android.musicinfo.events.NoArtistInfoAvailableEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ArtistInfoFragmentPresenterTest {

    private ArtistInfoFragmentView view;
    private ArtistInfoFragmentPresenter presenter;

    @Before
    public void setUp() {
        view = Mockito.mock(ArtistInfoFragmentView.class);
        presenter = new ArtistInfoFragmentPresenter(view);
    }

    @Test
    public void testViewIsNotUpdatedWithNullArtistResponse() {
        presenter.onArtistResponseReceived(null);

        Mockito.verifyZeroInteractions(view);
    }

    @Test
    public void testViewIsNotUpdatedWhenNullNoArtistEventReceived() {
        presenter.onNoArtistInformationAvailable(null);

        Mockito.verifyZeroInteractions(view);
    }

    @Test
    public void testViewIsUpdatedCorrectlyWithValidArtistResponse() {
        FilteredArtistResponse response = new FilteredArtistResponse("Test Bio", "Test Play Count", null);

        presenter.onArtistResponseReceived(new ArtistResponseEvent(response));

        Mockito.verify(view).updateArtistBioText("Test Bio");
        Mockito.verify(view).updateArtistPlayCount("Test Play Count");
        Mockito.verify(view).setArtistPlayCountVisibility(true);
    }

    @Test
    public void testViewIsUpdatedCorrectlyWithValidNoArtistAvailableEventReceived() {
        presenter.onNoArtistInformationAvailable(new NoArtistInfoAvailableEvent("Test Artist"));

        Mockito.verify(view).updateArtistBioText("No information available for Test Artist");
        Mockito.verify(view).setArtistPlayCountVisibility(false);
    }
}
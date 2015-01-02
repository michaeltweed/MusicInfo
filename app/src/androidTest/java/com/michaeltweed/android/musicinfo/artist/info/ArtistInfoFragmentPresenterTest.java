package com.michaeltweed.android.musicinfo.artist.info;

import com.michaeltweed.android.musicinfo.ParentMusicInfoTestCase;
import com.michaeltweed.android.musicinfo.artist.FilteredArtistResponse;
import com.michaeltweed.android.musicinfo.events.ArtistResponseEvent;
import com.michaeltweed.android.musicinfo.events.NoArtistInfoAvailableEvent;

import org.mockito.Mockito;

public class ArtistInfoFragmentPresenterTest extends ParentMusicInfoTestCase {

    private ArtistInfoFragmentView view;
    private ArtistInfoFragmentPresenter presenter;

    public void setUp() throws Exception {
        super.setUp();

        view = Mockito.mock(ArtistInfoFragmentView.class);
        presenter = new ArtistInfoFragmentPresenter(view);
    }

    public void testViewIsNotUpdatedWithNullArtistResponse() {
        presenter.onArtistResponseReceived(null);

        Mockito.verifyZeroInteractions(view);
    }

    public void testViewIsNotUpdatedWhenNullNoArtistEventReceived() {
        presenter.onNoArtistInformationAvailable(null);

        Mockito.verifyZeroInteractions(view);
    }

    public void testViewIsUpdatedCorrectlyWithValidArtistResponse() {
        FilteredArtistResponse response = new FilteredArtistResponse("Test Bio", "Test Play Count", null);

        presenter.onArtistResponseReceived(new ArtistResponseEvent(response));

        Mockito.verify(view).updateArtistBioText("Test Bio");
        Mockito.verify(view).updateArtistPlayCount("Test Play Count");
        Mockito.verify(view).setArtistPlayCountVisibility(true);
    }

    public void testViewIsUpdatedCorrectlyWithValidNoArtistAvailableEventReceived() {
        presenter.onNoArtistInformationAvailable(new NoArtistInfoAvailableEvent("Test Artist"));

        Mockito.verify(view).updateArtistBioText("No information available for Test Artist");
        Mockito.verify(view).setArtistPlayCountVisibility(false);
    }
}
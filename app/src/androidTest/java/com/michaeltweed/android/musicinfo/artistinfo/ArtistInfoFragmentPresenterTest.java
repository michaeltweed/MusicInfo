package com.michaeltweed.android.musicinfo.artistinfo;

import com.michaeltweed.android.musicinfo.Constants;
import com.michaeltweed.android.musicinfo.ParentMusicInfoTestCase;
import com.michaeltweed.android.musicinfo.apis.lastfm.LastFmInterface;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.ArtistResponse;
import com.michaeltweed.android.musicinfo.events.SongChangedEvent;

import org.mockito.Mockito;

import retrofit.Callback;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;

public class ArtistInfoFragmentPresenterTest extends ParentMusicInfoTestCase {
    private LastFmInterface apiInterface;
    private ArtistInfoFragmentView view;
    private ArtistInfoFragmentPresenter presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        apiInterface = Mockito.mock(LastFmInterface.class);
        view = Mockito.mock(ArtistInfoFragmentView.class);
        presenter = new ArtistInfoFragmentPresenter(view, apiInterface);
    }

    public void testApiRequestIsMadeWhenSongChangedEventIsReceived() {
        SongChangedEvent event = new SongChangedEvent("Bruce Springsteen", "The River", "Jackson Cage");
        presenter.onSongChangedEvent(event);
        Mockito.verify(apiInterface).getArtistResponse(eq("artist.getinfo"), eq("Bruce Springsteen"), eq("1"), eq(""), eq(Constants.LAST_FM_API_KEY), eq("json"), isA(Callback.class));
        Mockito.verify(view).setProgressBarVisibility(true);
    }

    public void testViewIsUpdatedWhenApiRequestSucceeds() {
        //TODO populate the ArtistResponse with some actual text and make sure this is passed through correctly
        presenter.success(new ArtistResponse(), null);
        Mockito.verify(view).updateArtistBioText(Mockito.anyString());
        Mockito.verify(view).setProgressBarVisibility(false);
    }

    public void testViewIsUpdatedWhenApiRequestFails() {
        presenter.failure(null);
        Mockito.verify(view).updateArtistBioText("No data available");
        Mockito.verify(view).setProgressBarVisibility(false);
    }


}
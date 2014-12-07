package com.michaeltweed.android.musicinfo.artistinfo;

import com.michaeltweed.android.musicinfo.Constants;
import com.michaeltweed.android.musicinfo.ParentMusicInfoTestCase;
import com.michaeltweed.android.musicinfo.apis.lastfm.LastFmInterface;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.ArtistResponse;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.Image;
import com.michaeltweed.android.musicinfo.events.SongChangedEvent;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

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

    public void testViewIsUpdatedWhenApiRequestSucceedsWithValidData() {

        ArtistResponse response = Mockito.mock(ArtistResponse.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(response.getArtist().getImages()).thenReturn(getTestImageList());
        Mockito.when(response.getArtist().getBio().getContent()).thenReturn("biography");

        presenter.success(response, null);

        Mockito.verify(view).updateArtistBioText("biography");
        Mockito.verify(view).setBackgroundImageToUrl("url3");
        Mockito.verify(view).setProgressBarVisibility(false);
    }

    public void testViewIsUpdatedWhenApiRequestSucceedsWithNonValidData() {

        ArtistResponse response = Mockito.mock(ArtistResponse.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(response.getArtist().getBio()).thenReturn(null);

        presenter.success(response, null);

        Mockito.verify(view).updateArtistBioText("No data available");
        Mockito.verify(view).setProgressBarVisibility(false);
    }

    public void testViewIsUpdatedWhenApiRequestFails() {
        presenter.failure(null);
        Mockito.verify(view).updateArtistBioText("No data available");
        Mockito.verify(view).setProgressBarVisibility(false);
    }

    public void testCorrectImageUrlIsReturnedWithValidData() {
        List<Image> list = getTestImageList();
        assertEquals("url3", presenter.getCorrectImageUrlForArtist(list));
    }

    public void testNullPointerExceptionIsThrownIfImageUrlListIsEmpty() {
        Exception exception = null;
        try {
            presenter.getCorrectImageUrlForArtist(new ArrayList<Image>());
        } catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof NullPointerException);
    }

    public void testNullPointerExceptionIsThrownIfImageUrlListIsNull() {
        Exception exception = null;
        try {
            presenter.getCorrectImageUrlForArtist(null);
        } catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof NullPointerException);
    }

    private List<Image> getTestImageList() {
        List<Image> list = new ArrayList<>();
        list.add(new Image("url1", "small"));
        list.add(new Image("url2", "medium"));
        list.add(new Image("url3", "large"));
        return list;
    }

}
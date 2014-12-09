package com.michaeltweed.android.musicinformation.artistinfo;

import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;

import com.michaeltweed.android.musicinformation.Constants;
import com.michaeltweed.android.musicinformation.ParentMusicInfoTestCase;
import com.michaeltweed.android.musicinformation.R;
import com.michaeltweed.android.musicinformation.apis.lastfm.LastFmInterface;
import com.michaeltweed.android.musicinformation.apis.lastfm.pojos.ArtistResponse;
import com.michaeltweed.android.musicinformation.apis.lastfm.pojos.Image;
import com.michaeltweed.android.musicinformation.events.PaletteAvailableEvent;
import com.michaeltweed.android.musicinformation.events.SongChangedEvent;
import com.squareup.otto.Bus;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.client.Header;
import retrofit.client.Response;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;

public class ArtistInfoFragmentPresenterTest extends ParentMusicInfoTestCase {
    private Bus bus;
    private LastFmInterface apiInterface;
    private ArtistInfoFragmentView view;
    private ArtistInfoFragmentPresenter presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        bus = Mockito.mock(Bus.class);
        apiInterface = Mockito.mock(LastFmInterface.class);
        view = Mockito.mock(ArtistInfoFragmentView.class);
        presenter = new ArtistInfoFragmentPresenter(bus, view, apiInterface);
    }

    public void testApiRequestIsMadeWhenSongChangedEventIsReceived() {
        SongChangedEvent event = new SongChangedEvent("Bruce Springsteen & The E-Street Band", "The River", "Jackson Cage");
        presenter.onSongChangedEvent(event);
        Mockito.verify(apiInterface).getArtistResponse(eq("artist.getinfo"), eq("Bruce Springsteen & The E-Street Band"), eq("1"), eq(Constants.LAST_FM_USERNAME), eq(Constants.LAST_FM_API_KEY), eq("json"), isA(Callback.class));
        Mockito.verify(view).setProgressBarVisibility(true);
    }

    public void testThatChangingTheSongToTheSameArtistDoesNothing() {
        SongChangedEvent event = new SongChangedEvent("Bruce Springsteen & The E-Street Band", "The River", "Jackson Cage");
        presenter.onSongChangedEvent(event);

        Mockito.reset(apiInterface, view);

        SongChangedEvent event2 = new SongChangedEvent("Bruce Springsteen & The E-Street Band", "Darkness On The Edge Of Town", "The Promised Land");
        presenter.onSongChangedEvent(event2);

        Mockito.verifyZeroInteractions(apiInterface, view);
    }

    public void testThatChangingTheSongToADifferentArtistSendsAPIRequest() {
        SongChangedEvent event = new SongChangedEvent("Sam Cooke", "The Man Who Invented Soul", "Bring It On Home To Me");
        presenter.onSongChangedEvent(event);

        Mockito.reset(apiInterface, view);

        SongChangedEvent event2 = new SongChangedEvent("Otis Rush", "Right Place, Wrong Time", "Natural Ball");
        presenter.onSongChangedEvent(event2);

        Mockito.verify(apiInterface).getArtistResponse(eq("artist.getinfo"), eq("Otis Rush"), eq("1"), eq(Constants.LAST_FM_USERNAME), eq(Constants.LAST_FM_API_KEY), eq("json"), isA(Callback.class));
        Mockito.verify(view).setProgressBarVisibility(true);

    }

    public void testThatReceivingSongChangedEventWithNoArtistDoesNothing() {
        presenter.onSongChangedEvent(new SongChangedEvent(null, null, null));

        Mockito.verifyZeroInteractions(apiInterface, view);
    }

    public void testViewIsUpdatedWhenApiRequestSucceedsWithValidData() {

        ArtistResponse response = Mockito.mock(ArtistResponse.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(response.getArtist().getImages()).thenReturn(getTestImageList());
        Mockito.when(response.getArtist().getBio().getContent()).thenReturn("biography");
        Mockito.when(response.getArtist().getStats().getUserplaycount()).thenReturn("150");

        Response retrofitResponse = new Response("Van+Morrison", 400, "", new ArrayList<Header>(), null);

        presenter.onSongChangedEvent(new SongChangedEvent("Van Morrison", "Astral Weeks", "Beside You"));
        presenter.success(response, retrofitResponse);

        Mockito.verify(view).updateArtistBioText("biography");
        Mockito.verify(view).updateArtistPlayCount("You have listened to this artist 150 times");
        Mockito.verify(view).setBackgroundImageToUrl("url3");
        Mockito.verify(view).setProgressBarVisibility(false);
    }

    public void testViewIsUpdatedWhenApiRequestSucceedsWithNonValidData() {

        ArtistResponse response = Mockito.mock(ArtistResponse.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(response.getArtist().getBio()).thenReturn(null);

        Response retrofitResponse = new Response("The+Jimi+Hendrix+Experience", 400, "", new ArrayList<Header>(), null);

        presenter.onSongChangedEvent(new SongChangedEvent("The Jimi Hendrix Experience", "Electric Ladyland", "All Along The Watchtower"));
        presenter.success(response, retrofitResponse);

        Mockito.verify(view).updateArtistBioText("No data available");
        Mockito.verify(view).setProgressBarVisibility(false);
    }

    public void testArtistPlayCountIsCorrectIfApiReturnsNull() {
        ArtistResponse response = Mockito.mock(ArtistResponse.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(response.getArtist().getImages()).thenReturn(getTestImageList());
        Mockito.when(response.getArtist().getStats().getUserplaycount()).thenReturn(null);

        Response retrofitResponse = new Response("The+Jimi+Hendrix+Experience", 400, "", new ArrayList<Header>(), null);

        presenter.onSongChangedEvent(new SongChangedEvent("The Jimi Hendrix Experience", "Electric Ladyland", "All Along The Watchtower"));
        presenter.success(response, retrofitResponse);

        Mockito.verify(view).updateArtistPlayCount("You need to sign in to view personal statistics");
    }

    public void testArtistPlayCountIsCorrectIfApiReturnsZero() {
        ArtistResponse response = Mockito.mock(ArtistResponse.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(response.getArtist().getImages()).thenReturn(getTestImageList());
        Mockito.when(response.getArtist().getStats().getUserplaycount()).thenReturn("0");

        Response retrofitResponse = new Response("The+Jimi+Hendrix+Experience", 400, "", new ArrayList<Header>(), null);

        presenter.onSongChangedEvent(new SongChangedEvent("The Jimi Hendrix Experience", "Electric Ladyland", "All Along The Watchtower"));
        presenter.success(response, retrofitResponse);

        Mockito.verify(view).updateArtistPlayCount("This is your first time listening to this artist");
    }

    public void testArtistPlayCountIsCorrectIfApiReturnsOne() {
        ArtistResponse response = Mockito.mock(ArtistResponse.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(response.getArtist().getImages()).thenReturn(getTestImageList());
        Mockito.when(response.getArtist().getStats().getUserplaycount()).thenReturn("1");

        Response retrofitResponse = new Response("The+Jimi+Hendrix+Experience", 400, "", new ArrayList<Header>(), null);

        presenter.onSongChangedEvent(new SongChangedEvent("The Jimi Hendrix Experience", "Electric Ladyland", "All Along The Watchtower"));
        presenter.success(response, retrofitResponse);

        Mockito.verify(view).updateArtistPlayCount("You have listened to this artist 1 time");
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
    
    public void testBusEventIsSentWhenPaletteIsAvailable() {
        presenter.paletteAvailable(Palette.generate(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher)));
        Mockito.verify(bus).post(isA(PaletteAvailableEvent.class));
    }

    private List<Image> getTestImageList() {
        List<Image> list = new ArrayList<>();
        list.add(new Image("url1", "small"));
        list.add(new Image("url2", "medium"));
        list.add(new Image("url3", "large"));
        return list;
    }

}
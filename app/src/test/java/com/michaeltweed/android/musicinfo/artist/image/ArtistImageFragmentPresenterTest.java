package com.michaeltweed.android.musicinfo.artist.image;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.Artist;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.ArtistResponse;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.Image;
import com.michaeltweed.android.musicinfo.artist.FilteredArtistResponse;
import com.michaeltweed.android.musicinfo.events.ArtistResponseEvent;
import com.michaeltweed.android.musicinfo.events.NoArtistInfoAvailableEvent;
import com.michaeltweed.android.musicinfo.events.PaletteAvailableEvent;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.isA;

public class ArtistImageFragmentPresenterTest {
    private Bus bus;
    private ArtistImageFragmentView view;
    private ArtistImageFragmentPresenter presenter;

    @Before
    public void setUp() throws Exception {
        bus = Mockito.mock(Bus.class);
        view = Mockito.mock(ArtistImageFragmentView.class);
        presenter = new ArtistImageFragmentPresenter(bus, view);
    }

    @Test
    public void testBusEventIsSentWhenPaletteIsAvailable() {
        presenter.paletteAvailable(Palette.generate(Mockito.mock(Bitmap.class)));
        Mockito.verify(bus).post(isA(PaletteAvailableEvent.class));
    }

    @Test
    public void testViewIsUpdatedWithValidArtistResponse() {
        List<Image> images = new ArrayList<>();
        images.add(new Image("url3", "large"));

        ArtistResponse response = getArtistResponseWithImages(images);

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        presenter.onArtistResponseReceived(new ArtistResponseEvent(filteredResponse));

        Mockito.verify(view).setImageBackgroundToUrl("url3");
    }

    @Test
    public void testViewIsNotUpdatedWithNonValidArtistResponse() {
        ArtistResponse response = getArtistResponseWithImages(new ArrayList<Image>());

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        presenter.onArtistResponseReceived(new ArtistResponseEvent(filteredResponse));

        Mockito.verifyZeroInteractions(view);
    }

    @Test
    public void testViewIsNotUpdatedWithNullArtistResponse() {
        presenter.onArtistResponseReceived(null);

        Mockito.verifyZeroInteractions(view);
    }

    @Test
    public void testViewIsUpdatedWhenValidNoArtistEventReceived() {
        presenter.onNoArtistInformationAvailable(new NoArtistInfoAvailableEvent("Test"));

        Mockito.verify(view).setImageBackgroundToEmpty();
    }

    @Test
    public void testViewIsNotUpdatedWhenNullNoArtistEventReceived() {
        presenter.onNoArtistInformationAvailable(null);

        Mockito.verifyZeroInteractions(view);
    }


    /* PRIVATE HELPER METHODS */

    private ArtistResponse getArtistResponseWithImages(List<Image> images) {
        Artist artist = new Artist(null, null, null, images, null, null, null, null, null, null);
        return new ArtistResponse(artist);
    }

}
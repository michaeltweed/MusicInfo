package com.michaeltweed.android.musicinfo.artist.image;

import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;

import com.michaeltweed.android.musicinfo.ParentMusicInfoTestCase;
import com.michaeltweed.android.musicinfo.R;
import com.michaeltweed.android.musicinfo.events.PaletteAvailableEvent;
import com.squareup.otto.Bus;

import org.mockito.Mockito;

import static org.mockito.Matchers.isA;

public class ArtistImageFragmentPresenterTest extends ParentMusicInfoTestCase {
    private Bus bus;
    private ArtistImageFragmentPresenter presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        bus = Mockito.mock(Bus.class);
        presenter = new ArtistImageFragmentPresenter(bus);
    }

    public void testBusEventIsSentWhenPaletteIsAvailable() {
        presenter.paletteAvailable(Palette.generate(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher)));
        Mockito.verify(bus).post(isA(PaletteAvailableEvent.class));
    }

}
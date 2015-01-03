package com.michaeltweed.android.musicinfo;

import com.michaeltweed.android.musicinfo.events.LastFmUsernameChangedEvent;
import com.michaeltweed.android.musicinfo.utils.SharedPreferencesHelper;
import com.squareup.otto.Bus;

import org.mockito.Mockito;

import static com.michaeltweed.android.musicinfo.utils.SharedPreferencesHelper.PrefsType.LAST_FM_USERNAME;

public class LastFmUsernamePresenterTest extends ParentMusicInfoTestCase {

    private Bus bus;
    private LastFmUsernameView view;
    private SharedPreferencesHelper sharedPrefs;

    public void setUp() throws Exception {
        super.setUp();

        bus = Mockito.mock(Bus.class);
        view = Mockito.mock(LastFmUsernameView.class);
        sharedPrefs = Mockito.mock(SharedPreferencesHelper.class);
    }

    public void testCallIsMadeToSharedPreferencesOnStart() {
        LastFmUsernamePresenter presenter = new LastFmUsernamePresenter(bus, view, sharedPrefs);

        Mockito.verify(sharedPrefs).getStringFromSharedPreferences(LAST_FM_USERNAME);
    }

    public void testCorrectActionsPerformedWhenValidValueInSharedPreferences() {
        Mockito.when(sharedPrefs.getStringFromSharedPreferences(LAST_FM_USERNAME)).thenReturn("testUsername");

        LastFmUsernamePresenter presenter = new LastFmUsernamePresenter(bus, view, sharedPrefs);

        Mockito.verify(view).updateUsername("testUsername");
        Mockito.verify(view).setTextBoxEnabled(false);
        Mockito.verify(bus).post(new LastFmUsernameChangedEvent("testUsername"));
    }

    public void testCorrectActionsPerformedWhenEmptyValueInSharedPreferences() {
        Mockito.when(sharedPrefs.getStringFromSharedPreferences(LAST_FM_USERNAME)).thenReturn("");

        LastFmUsernamePresenter presenter = new LastFmUsernamePresenter(bus, view, sharedPrefs);

        Mockito.verifyZeroInteractions(view, bus);
    }

    public void testCorrectActionsPerformedWhenNullValueInSharedPreferences() {
        Mockito.when(sharedPrefs.getStringFromSharedPreferences(LAST_FM_USERNAME)).thenReturn(null);

        LastFmUsernamePresenter presenter = new LastFmUsernamePresenter(bus, view, sharedPrefs);

        Mockito.verifyZeroInteractions(view, bus);
    }

    public void testCorrectActionsPerformedOnButtonPressWithUsername() {
        Mockito.when(sharedPrefs.getStringFromSharedPreferences(LAST_FM_USERNAME)).thenReturn("testUsername");

        LastFmUsernamePresenter presenter = new LastFmUsernamePresenter(bus, view, sharedPrefs);

        presenter.onButtonPressed("some text");

        Mockito.verify(view).updateUsername("");
        Mockito.verify(view).setTextBoxEnabled(true);
    }

    public void testCorrectActionsPerformedOnButtonPressWithoutUsername() {
        Mockito.when(sharedPrefs.getStringFromSharedPreferences(LAST_FM_USERNAME)).thenReturn("");

        LastFmUsernamePresenter presenter = new LastFmUsernamePresenter(bus, view, sharedPrefs);

        presenter.onButtonPressed("newUsername");

        Mockito.verify(sharedPrefs).writeStringToSharedPreference(LAST_FM_USERNAME, "newUsername");
        Mockito.verify(view).setTextBoxEnabled(false);
        Mockito.verify(view).showToastWithMessage("Last.fm username set to " + "newUsername");
        Mockito.verify(bus).post(new LastFmUsernameChangedEvent("newUsername"));
    }
}
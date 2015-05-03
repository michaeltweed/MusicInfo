package com.michaeltweed.android.musicinfo.playerchooser;

import com.michaeltweed.android.musicinfo.Constants;
import com.michaeltweed.android.musicinfo.utils.SharedPreferencesHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class PlayerChooserPresenterTest {

    private SharedPreferencesHelper sharedPreferencesHelper;
    private PlayerChooserPresenter presenter;

    @Before
    public void setUp() {
        sharedPreferencesHelper = Mockito.mock(SharedPreferencesHelper.class);
        presenter = new PlayerChooserPresenter(sharedPreferencesHelper);
    }

    @Test
    public void testCallIsMadeToSharedPreferencesOnStart() {
        Mockito.reset(sharedPreferencesHelper);
        PlayerChooserPresenter testPresenter = new PlayerChooserPresenter(sharedPreferencesHelper);

        for (String key : Constants.getIntentFilterHashMap().keySet()) {
            Mockito.verify(sharedPreferencesHelper).getBooleanFromSharedPreferences(key);
        }
    }

    @Test
    public void testGetOptionNamesReturnsCorrectly() {
        String[] expected = Constants.getIntentFilterHashMap().keySet().toArray(new String[Constants.getIntentFilterHashMap().size()]);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], presenter.getOptionNames()[i]);
        }
    }

    @Test
    public void testSelectedArrayReturnsCorrectly() {
        Mockito.when(sharedPreferencesHelper.getBooleanFromSharedPreferences("Spotify")).thenReturn(true);
        Mockito.when(sharedPreferencesHelper.getBooleanFromSharedPreferences("HTC Music")).thenReturn(false);
        PlayerChooserPresenter testPresenter = new PlayerChooserPresenter(sharedPreferencesHelper);

        boolean[] selectedArray = testPresenter.getSelectedArray();

        assertEquals(true, selectedArray[1]);
        assertEquals(false, selectedArray[2]);
    }

    @Test
    public void testOnOptionClickedPerformsCorrectly() {

        //item will be false by default

        assertEquals(false, presenter.getSelectedArray()[1]);

        //test clicking the first time
        presenter.onOptionClicked("Spotify");
        Mockito.verify(sharedPreferencesHelper).writeBooleanToSharedPreferences("Spotify", true);
        assertEquals(true, presenter.getSelectedArray()[1]);


        //test clicking again (to toggle)
        Mockito.reset(sharedPreferencesHelper);

        presenter.onOptionClicked("Spotify");
        Mockito.verify(sharedPreferencesHelper).writeBooleanToSharedPreferences("Spotify", false);
        assertEquals(false, presenter.getSelectedArray()[1]);
    }
}
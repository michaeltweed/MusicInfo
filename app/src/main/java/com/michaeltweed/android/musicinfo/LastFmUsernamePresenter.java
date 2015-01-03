package com.michaeltweed.android.musicinfo;

import com.michaeltweed.android.musicinfo.events.LastFmUsernameChangedEvent;
import com.michaeltweed.android.musicinfo.utils.SharedPreferencesHelper;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import static com.michaeltweed.android.musicinfo.utils.SharedPreferencesHelper.PrefsType.LAST_FM_USERNAME;

public class LastFmUsernamePresenter {

    private Bus bus;
    private final LastFmUsernameView view;
    private final SharedPreferencesHelper sharedPrefsHelper;

    private String username = "";

    public LastFmUsernamePresenter(Bus bus, LastFmUsernameView view, SharedPreferencesHelper sharedPrefsHelper) {
        this.bus = bus;
        this.view = view;
        this.sharedPrefsHelper = sharedPrefsHelper;

        checkIfUsernameExistsInSharedPrefs();
    }

    //when we first come in we check to see if there is a username in shared preferences
    //if there is we callback to the view to show this in the textview
    //and set the textbox to be disabled
    //and set the button to read 'edit'

    //if there is not, the textbox should be empty and editable
    //and the button should read 'set'
    private void checkIfUsernameExistsInSharedPrefs() {
        username = sharedPrefsHelper.getStringFromSharedPreferences(LAST_FM_USERNAME);

        if (hasUsername()) {
            view.updateUsername(username);
            view.setTextBoxEnabled(false);
            bus.post(new LastFmUsernameChangedEvent(username));
        }
    }

    //when the user presses the 'edit' button
    //the textview should be made editable and given focus

    //when the user presses the 'set' button
    //the textview should be disabled
    //the button should read 'edit'
    //the username should be stored to sharedpreferences
    //a toast should appear telling the user it has been set
    //a message should be posted to the bus

    public void onButtonPressed(String textViewText) {
        if (hasUsername()) {
            username = "";
            view.updateUsername(username);
            view.setTextBoxEnabled(true);
        } else {
            username = textViewText;
            sharedPrefsHelper.writeStringToSharedPreference(LAST_FM_USERNAME, username);

            view.setTextBoxEnabled(false);
            view.showToastWithMessage("Last.fm username set to " + username);
            bus.post(new LastFmUsernameChangedEvent(username));
        }
    }

    @Produce
    public LastFmUsernameChangedEvent lastFmUsernameChangedEventProducer() {
        return new LastFmUsernameChangedEvent(username);
    }

    private boolean hasUsername() {
        if (username != null) {
            if (!username.isEmpty()) {
                return true;
            }
        }
        return false;
    }





}

package com.michaeltweed.android.musicinfo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.michaeltweed.android.musicinfo.Constants;

public class SharedPreferencesHelper {

    private final static String LAST_FM_USER_KEY = "last_fm_username";
    private final SharedPreferences sharedPref;

    public enum PrefsType {
        LAST_FM_USERNAME;
    }

    public SharedPreferencesHelper(Context context) {
        sharedPref = context.getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void writeStringToSharedPreference(PrefsType type, String toStore) {
        switch (type){
            case LAST_FM_USERNAME:
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(LAST_FM_USER_KEY, toStore);
                editor.commit();
        }
    }

    public String getStringFromSharedPreferences(PrefsType type) {
        switch (type) {
            case LAST_FM_USERNAME:
                return sharedPref.getString(LAST_FM_USER_KEY, "");
        }

        return null;
    }
}

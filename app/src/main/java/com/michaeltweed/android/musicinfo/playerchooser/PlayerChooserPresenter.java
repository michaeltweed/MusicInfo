package com.michaeltweed.android.musicinfo.playerchooser;

import com.michaeltweed.android.musicinfo.Constants;
import com.michaeltweed.android.musicinfo.utils.SharedPreferencesHelper;

import java.util.LinkedHashMap;

public class PlayerChooserPresenter {
    private SharedPreferencesHelper sharedPreferencesHelper;

    private LinkedHashMap<String, Boolean> listOfOptions = new LinkedHashMap<>();

    public PlayerChooserPresenter(SharedPreferencesHelper sharedPreferencesHelper) {
        this.sharedPreferencesHelper = sharedPreferencesHelper;

        loadItemsIntoHashMap();
    }

    public String[] getOptionNames() {
        return listOfOptions.keySet().toArray(new String[listOfOptions.size()]);
    }

    public boolean[] getSelectedArray() {
        boolean[] toReturn = new boolean[getOptionNames().length];
        for (int i = 0; i < getOptionNames().length; i++) {
            String currentItem = getOptionNames()[i];
            toReturn[i] = isSelected(currentItem);
        }

        return toReturn;
    }

    public void onOptionClicked(String optionName) {
        if (isSelected(optionName)) {
            sharedPreferencesHelper.writeBooleanToSharedPreferences(optionName, false);
            listOfOptions.put(optionName, false);
        } else {
            sharedPreferencesHelper.writeBooleanToSharedPreferences(optionName, true);
            listOfOptions.put(optionName, true);
        }
    }

    private void loadItemsIntoHashMap() {
        for (String key : Constants.getIntentFilterHashMap().keySet()) {
            listOfOptions.put(key, sharedPreferencesHelper.getBooleanFromSharedPreferences(key));
        }
    }

    private boolean isSelected(String optionName) {
        return listOfOptions.get(optionName);
    }
}

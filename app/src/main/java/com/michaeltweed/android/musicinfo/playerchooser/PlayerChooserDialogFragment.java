package com.michaeltweed.android.musicinfo.playerchooser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.michaeltweed.android.musicinfo.utils.SharedPreferencesHelper;

public class PlayerChooserDialogFragment extends DialogFragment {

    private PlayerChooserPresenter presenter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        presenter = new PlayerChooserPresenter(new SharedPreferencesHelper(getActivity()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select").setMultiChoiceItems(presenter.getOptionNames(), presenter.getSelectedArray(),
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        String name = presenter.getOptionNames()[which];
                        presenter.onOptionClicked(name);
                    }
                })
                // Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}

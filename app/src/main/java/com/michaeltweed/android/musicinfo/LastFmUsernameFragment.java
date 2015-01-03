package com.michaeltweed.android.musicinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.michaeltweed.android.musicinfo.utils.SharedPreferencesHelper;

public class LastFmUsernameFragment extends Fragment implements LastFmUsernameView {

    private LastFmUsernamePresenter presenter;

    private EditText editText;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.last_fm_username_fragment_layout, container, false);

        editText = (EditText) rootView.findViewById(R.id.editText);
        button = (Button) rootView.findViewById(R.id.button);

        presenter = new LastFmUsernamePresenter(BusSingleton.getBus(), this, new SharedPreferencesHelper(getActivity()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonPressed(editText.getText().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusSingleton.getBus().register(presenter);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusSingleton.getBus().unregister(presenter);
    }

    @Override
    public void updateUsername(String username) {
        editText.setText(username);
    }

    @Override
    public void setTextBoxEnabled(boolean shouldEnable) {
        if (shouldEnable) {
            editText.setEnabled(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            button.setText("Set");
        } else {
            editText.setEnabled(false);
            editText.setFocusable(false);
            button.setText("Edit");
        }
    }

    @Override
    public void showToastWithMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}

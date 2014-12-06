package com.michaeltweed.android.musicinfo;

import android.support.v4.app.Fragment;

import com.michaeltweed.android.musicinfo.application.MusicInfoApplication;
import com.squareup.otto.Bus;

/**
 * Created by Michael on 06/12/2014.
 */
public class MusicInfoBaseFragment extends Fragment {

    protected Bus getBus() {
        MusicInfoApplication application = (MusicInfoApplication) getActivity().getApplication();
        return application.getBus();
    }
}

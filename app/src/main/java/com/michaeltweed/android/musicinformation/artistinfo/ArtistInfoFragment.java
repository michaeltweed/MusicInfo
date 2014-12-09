package com.michaeltweed.android.musicinformation.artistinfo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.michaeltweed.android.musicinformation.BusSingleton;
import com.michaeltweed.android.musicinformation.Constants;
import com.michaeltweed.android.musicinformation.R;
import com.michaeltweed.android.musicinformation.apis.lastfm.LastFmInterface;
import com.michaeltweed.android.musicinformation.utils.PaletteTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import retrofit.RestAdapter;

public class ArtistInfoFragment extends Fragment implements ArtistInfoFragmentView {

    private TextView artistInfoTextView;
    private ArtistInfoFragmentPresenter presenter;
    private ImageView artistInfoImageView;
    private ProgressBar progressBar;
    private TextView artistPlayCountTextView;

    public ArtistInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constants.LAST_FM_API_URL).build();

        presenter = new ArtistInfoFragmentPresenter(BusSingleton.getBus(), this, restAdapter.create(LastFmInterface.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_info_fragment_layout, container, false);
        artistInfoTextView = (TextView) rootView.findViewById(R.id.artist_info_textview);
        artistPlayCountTextView = (TextView) rootView.findViewById(R.id.artist_play_count_textview);
        artistInfoImageView = (ImageView) rootView.findViewById(R.id.artist_info_imageview);
        progressBar = (ProgressBar) rootView.findViewById(R.id.artist_info_progress);

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
    public void updateArtistBioText(String text) {
        artistInfoTextView.setText(Html.fromHtml(text));
        Linkify.addLinks(artistInfoTextView, Linkify.WEB_URLS);
    }

    @Override
    public void updateArtistPlayCount(String text) {
        artistPlayCountTextView.setText(text);
    }

    @Override
    public void setProgressBarVisibility(boolean shouldShow) {
        if (shouldShow) {
            artistInfoTextView.setVisibility(View.GONE);
            artistInfoImageView.setImageBitmap(null);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            artistInfoTextView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setBackgroundImageToUrl(String url) {
        Picasso.with(getActivity()).load(url).fit().centerCrop().transform(PaletteTransformation.instance()).into(artistInfoImageView, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) artistInfoImageView.getDrawable()).getBitmap();
                Palette palette = PaletteTransformation.getPalette(bitmap);
                presenter.paletteAvailable(palette);
            }

            @Override
            public void onError() {

            }
        });
    }
}

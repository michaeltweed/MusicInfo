package com.michaeltweed.android.musicinfo.artist.image;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.michaeltweed.android.musicinfo.BusSingleton;
import com.michaeltweed.android.musicinfo.R;
import com.michaeltweed.android.musicinfo.utils.PaletteTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ArtistImageFragment extends Fragment implements ArtistImageFragmentView{

    private ImageView artistInfoImageView;
    private ArtistImageFragmentPresenter presenter;

    public ArtistImageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ArtistImageFragmentPresenter(BusSingleton.getBus(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_image_fragment_layout, container, false);
        artistInfoImageView = (ImageView) rootView.findViewById(R.id.artist_info_imageview);

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
    public void setImageBackgroundToUrl(String url) {
        Picasso.with(getActivity()).load(url).fit().centerCrop().transform(PaletteTransformation.instance()).into(artistInfoImageView, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) artistInfoImageView.getDrawable()).getBitmap();
                Palette palette = PaletteTransformation.getPalette(bitmap);

                presenter.paletteAvailable(palette);
            }

            @Override
            public void onError() {
                setImageToPlaceholder();
            }
        });
    }

    @Override
    public void setImageBackgroundToEmpty() {
        setImageToPlaceholder();
    }

    private void setImageToPlaceholder() {
        artistInfoImageView.setImageResource(R.drawable.artist_image_placeholder);
    }
}

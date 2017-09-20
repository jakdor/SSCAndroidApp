package com.jakdor.sscapp.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakdor.sscapp.BaseFragment;
import com.jakdor.sscapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class MapFragment extends BaseFragment {

    @BindView(R.id.mapHotelImg)
    ImageView hotelImg;
    @BindView(R.id.mapHotelText)
    TextView hotelInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.map_fragment, container, false);
        ButterKnife.bind(this, view);

        Glide.with(this)
                .load("http://ssc.pwr.edu.pl/wp/wp-content/uploads/2017/01/hotel.jpg")
                .asBitmap()
                .placeholder(R.drawable.host_image_place_holder)
                .format(PREFER_ARGB_8888)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(hotelImg);

        hotelInfo.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }

    @OnClick({R.id.mapInfo, R.id.mapButton})
    public void onMapInfoClick(){
        lunchMap();
    }

    @OnClick({R.id.mapHotelInfo, R.id.mapHotelButton})
    public void onHotelMapInfoClick(){
        lunchHotelMap();
    }

    private void lunchMap(){
        Uri gmmIntentUri = Uri.parse("geo:51.1068336,17.0609089?q=51.1068336,17.0609089(H-14 building)");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void lunchHotelMap(){
        Uri gmmIntentUri = Uri.parse("geo:50.8633496,15.6762323,17?q=Hotel Cieplice");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}

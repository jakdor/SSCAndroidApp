package com.jakdor.sscapp.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakdor.sscapp.BaseFragment;
import com.jakdor.sscapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.map_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.mapInfo, R.id.mapButton})
    public void onMapInfoClick(){
        lunchMap();
    }

    private void lunchMap(){
        Uri gmmIntentUri = Uri.parse("geo:51.1068336,17.0609089?q=51.1068336,17.0609089(H-14 building)");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}

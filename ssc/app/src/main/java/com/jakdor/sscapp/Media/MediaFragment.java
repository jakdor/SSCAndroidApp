package com.jakdor.sscapp.Media;

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

public class MediaFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.media_fragment, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    private void openWebsite(String url){
        getContext().startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
    }

    @OnClick({R.id.media_image1, R.id.media_text1})
    public void onMedia1Click(){
        openWebsite("https://twitter.com/SSCPWr");
    }

    @OnClick({R.id.media_image2, R.id.media_text2})
    public void onMedia2Click(){
        openWebsite("https://www.facebook.com/Students.Science.Conference?");
    }

    @OnClick({R.id.media_image3, R.id.media_text3})
    public void onMedia3Click(){
        openWebsite("https://www.youtube.com/channel/UCVX_LsODut87d7MC6NeTsow");
    }

    @OnClick({R.id.media_image4, R.id.media_text4})
    public void onMedia4Click(){
        openWebsite("https://www.instagram.com/studentsscience/");
    }

    @OnClick({R.id.media_image5, R.id.media_text5})
    public void onMedia5Click(){
        openWebsite("http://ssc.pwr.edu.pl/");
    }

}

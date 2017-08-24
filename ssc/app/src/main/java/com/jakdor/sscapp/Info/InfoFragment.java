package com.jakdor.sscapp.Info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakdor.sscapp.BaseFragment;
import com.jakdor.sscapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class InfoFragment extends BaseFragment {

    @BindView(R.id.info_asi_logo)
    ImageView logo;
    @BindView(R.id.info_image)
    ImageView infoImg;
    @BindView(R.id.me_image)
    ImageView meImg;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);
        ButterKnife.bind(this, view);
        loadImages();
        return view;
    }

    private void loadImages(){
        Glide.with(this)
                .load(getString(R.string.link_asi_img))
                .asBitmap()
                .fitCenter()
                .format(PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.host_image_place_holder)
                .into(logo);

        Glide.with(this)
                .load(getString(R.string.link_roks_img))
                .fitCenter()
                .crossFade()
                .into(infoImg);

        Glide.with(this)
                .load(getString(R.string.link_me_img))
                .asBitmap()
                .fitCenter()
                .format(PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(meImg);
    }

    private void lunchWebsite(String url){
        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
    }

    private void sendMail(String mail){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + mail));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_topic));
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(),
                    getContext().getString(R.string.mail_fail), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.info_asi_logo)
    public void onLogoClick(){
        lunchWebsite("http://asi.wroclaw.pl/");
    }

    @OnClick({R.id.me_image_frame, R.id.me_mail})
    public void onMailMeClick(){
        sendMail("jak_dor@wp.pl");
    }

    @OnClick(R.id.me_fb)
    public void onMeFbClick(){
        lunchWebsite("https://www.facebook.com/jakub.dorda");
    }

    @OnClick(R.id.me_git)
    public void onMeGitClick(){
        lunchWebsite("https://github.com/jakdor");
    }

    @OnClick({R.id.info_image, R.id.info_mail})
    public void onInfoMailClick(){
        sendMail("roksanakowalczyk94@gmail.com");
    }
}

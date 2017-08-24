package com.jakdor.sscapp.Contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakdor.sscapp.BaseFragment;
import com.jakdor.sscapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.contactInfo, R.id.contactButton})
    public void onContactMailClick(){
        sendMail(getString(R.string.contact_mail));
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
}

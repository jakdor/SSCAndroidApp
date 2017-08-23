package com.jakdor.sscapp.Sponsor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jakdor.sscapp.Model.Sponsor;
import com.jakdor.sscapp.NetContentBaseFragment;
import com.jakdor.sscapp.R;

import java.util.List;

import butterknife.BindView;

public class SponsorFragment extends NetContentBaseFragment {

    @BindView(R.id.sponsor_recycler_view)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sponsor_fragment, container, false);

        createView(view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    protected void removeContentWhileLoad() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void loadContent() {
        super.loadContent();
        recyclerView.setVisibility(View.VISIBLE);
        loadRecyclerView();
    }

    private void loadRecyclerView(){
        List<Sponsor> sponsors = Sponsor.listAll(Sponsor.class);

        SponsorAdapter hostAdapter = new SponsorAdapter(getContext(), Glide.with(this), sponsors);
        recyclerView.setAdapter(hostAdapter);
    }
}

package com.jakdor.sscapp.Sponsor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakdor.sscapp.Model.Sponsor;
import com.jakdor.sscapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.Holder> {

    private List<Sponsor> sponsors;
    private Context context;
    private RequestManager glide;

    SponsorAdapter(Context context, RequestManager glide, List<Sponsor> sponsors) {
        this.sponsors = sponsors;
        this.context = context;
        this.glide = glide;
    }

    @Override
    public SponsorAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_sponsor, parent, false);

        return new SponsorAdapter.Holder(inflatedView);
    }

    @Override
    public void onBindViewHolder(SponsorAdapter.Holder holder, int position) {
        Sponsor sponsor = sponsors.get(position);

        holder.name.setText(sponsor.getName());

        glide.load(sponsor.getImgUrl())
                .asBitmap()
                .format(PREFER_ARGB_8888)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.host_image_place_holder)
                .into(holder.image);

        holder.name.setOnClickListener(view -> lunchSponsorWebsite(sponsor.getWebUrl()));
        holder.image.setOnClickListener(view -> lunchSponsorWebsite(sponsor.getWebUrl()));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return sponsors.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.sponsor_item_name)
        TextView name;
        @BindView(R.id.sponsor_item_image)
        ImageView image;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void lunchSponsorWebsite(String url){
        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
    }
}
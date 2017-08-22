package com.jakdor.sscapp.Host;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakdor.sscapp.Model.Host;
import com.jakdor.sscapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class HostAdapter extends RecyclerView.Adapter<HostAdapter.Holder> {

    private List<Host> hosts;
    private Context context;

    HostAdapter(Context context, List<Host> hosts) {
        this.hosts = hosts;
        this.context = context;
    }

    @Override
    public HostAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_host, parent, false);

        return new HostAdapter.Holder(inflatedView);
    }

    @Override
    public void onBindViewHolder(HostAdapter.Holder holder, int position) {
        Host host = hosts.get(position);

        holder.name.setText(host.getName());
        holder.info.setText(host.getInfo());
        Glide.with(context)
                .load(host.getImgUrl())
                .placeholder(R.drawable.host_image_place_holder)
                .fitCenter()
                .crossFade()
                .into(holder.image);

        holder.image.setOnClickListener(view -> mailTo(host.getMail()));
        holder.mail.setOnClickListener(view -> mailTo(host.getMail()));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return hosts.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.host_item_name)
        TextView name;
        @BindView(R.id.host_item_info)
        TextView info;
        @BindView(R.id.host_item_image)
        ImageView image;
        @BindView(R.id.host_item_mail)
        ImageView mail;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void mailTo(String mail){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + mail));
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.mail_topic));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, context.getString(R.string.mail_fail), Toast.LENGTH_SHORT).show();
        }
    }
}
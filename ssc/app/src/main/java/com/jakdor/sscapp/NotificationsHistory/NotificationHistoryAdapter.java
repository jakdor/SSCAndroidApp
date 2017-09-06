package com.jakdor.sscapp.NotificationsHistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakdor.sscapp.Model.NotificationHistory;
import com.jakdor.sscapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class NotificationHistoryAdapter extends RecyclerView.Adapter<NotificationHistoryAdapter.Holder> {

    private List<NotificationHistory> notifications;
    private Context context;

    NotificationHistoryAdapter(Context context, List<NotificationHistory> notifications) {
        this.notifications = notifications;
        this.context = context;
    }

    @Override
    public NotificationHistoryAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_notif_hist, parent, false);

        return new NotificationHistoryAdapter.Holder(inflatedView);
    }

    @Override
    public void onBindViewHolder(NotificationHistoryAdapter.Holder holder, int position) {
        NotificationHistory notification = notifications.get(position);

        holder.time.setText(notification.getTime());
        holder.title.setText(notification.getTitle());
        holder.message.setText(notification.getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_hist_time)
        TextView time;
        @BindView(R.id.notification_hist_title)
        TextView title;
        @BindView(R.id.notification_hist_message)
        TextView message;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
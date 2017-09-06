package com.jakdor.sscapp.NotificationsHistory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakdor.sscapp.BaseFragment;
import com.jakdor.sscapp.Model.NotificationHistory;
import com.jakdor.sscapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsHistoryFragment extends BaseFragment {

    @BindView(R.id.notification_hist_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.notification_hist_card)
    CardView card;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_history_fragment, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadRecyclerView();

        return view;
    }

    private void loadRecyclerView(){
        List<NotificationHistory> notifications;

        try {
            notifications = NotificationHistory.listAll(NotificationHistory.class);
        }
        catch (Exception e){
            recyclerView.setVisibility(View.GONE);
            card.setVisibility(View.VISIBLE);
            return;
        }

        if(notifications.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            card.setVisibility(View.VISIBLE);
            return;
        }

        NotificationHistoryAdapter adapter = new NotificationHistoryAdapter(notifications);
        recyclerView.setAdapter(adapter);
    }
}

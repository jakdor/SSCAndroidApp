package com.jakdor.sscapp.Timetable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakdor.sscapp.Model.Timetable;
import com.jakdor.sscapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.Holder> {

    private List<Timetable> timetables;
    private Context context;

    TimetableAdapter(Context context, List<Timetable> timetables) {
        this.timetables = timetables;
        this.context = context;
    }

    @Override
    public TimetableAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_timetable, parent, false);
        return new TimetableAdapter.Holder(inflatedView);
    }

    @Override
    public void onBindViewHolder(TimetableAdapter.Holder holder, int position) {
        Timetable timetable = timetables.get(position);

        holder.timetableItemName.setText(timetable.getName());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return timetables.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.timetable_item_name)
        TextView timetableItemName;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
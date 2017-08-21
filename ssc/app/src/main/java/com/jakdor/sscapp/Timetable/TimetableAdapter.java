package com.jakdor.sscapp.Timetable;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakdor.sscapp.Model.Timetable;
import com.jakdor.sscapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.Holder> {

    private List<Timetable> timetables;
    private Context context;

    private SimpleDateFormat formatter;

    TimetableAdapter(Context context, List<Timetable> timetables) {
        this.timetables = timetables;
        this.context = context;
    }

    @Override
    public TimetableAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_timetable, parent, false);
        
        formatter = new SimpleDateFormat("HH:mm", Locale.GERMAN);

        return new TimetableAdapter.Holder(inflatedView);
    }

    @Override
    public void onBindViewHolder(TimetableAdapter.Holder holder, int position) {
        Timetable timetable = timetables.get(position);

        holder.itemName.setText(timetable.getName());
        holder.itemTime.setText(formatTime(timetable.getHStart(),
                timetable.getMStart(), timetable.getHEnd(), timetable.getMEnd()));

        if(nowHappening(timetable)){
            holder.nowCard.setVisibility(View.VISIBLE);
        }
        else {
            holder.nowCard.setVisibility(View.GONE);
        }
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
        TextView itemName;
        @BindView(R.id.timetable_item_time)
        TextView itemTime;
        @BindView(R.id.timetable_item_now_tab)
        CardView nowCard;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private boolean nowHappening(Timetable timetable){
        Calendar calendar = Calendar.getInstance();
        int calMonth = calendar.get(Calendar.MONTH);
        if(calMonth != 8) {
            return false;
        }

        int calDay = calendar.get(Calendar.DAY_OF_MONTH);
        int calHour = calendar.get(Calendar.HOUR_OF_DAY);
        int calMin = calendar.get(Calendar.MINUTE);

        int hourStart = timetable.getHStart();
        int minutesStart = timetable.getMStart();
        int hourEnd = timetable.getHEnd();
        int minutesEnd = timetable.getMEnd();

        if(hourEnd == -1 || minutesEnd == -1){
            int index = timetables.indexOf(timetable) + 1;
            if(index != timetables.size()){
                hourEnd = timetables.get(index).getHStart();
                minutesEnd = timetables.get(index).getMStart();
            }
            else {
                hourEnd = 0;
                minutesEnd = 0;
            }
        }

        if (calDay == timetable.getDay() + 20) {
            if (hourStart * 100 + minutesStart <= calHour * 100 + calMin) {
                if (hourEnd * 100 + minutesEnd > calHour * 100 + calMin) {
                    return true;
                }
            }
        }

        return false;
    }

    private String formatTime(int hStart, int mStart, int hEnd, int mEnd){
        String time;

        Calendar calendar = new GregorianCalendar();
        formatter.setTimeZone(calendar.getTimeZone());

        calendar.set(1970, 1, 2, hStart, mStart);

        time = formatter.format(calendar.getTime());

        if(hEnd == -1 || mEnd == -1){
            return time;
        }

        calendar.set(1970, 1, 2, hEnd, mEnd);
        time += " - " + formatter.format(calendar.getTime());

        return time;
    }
}
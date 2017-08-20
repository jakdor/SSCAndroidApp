package com.jakdor.sscapp.Timetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakdor.sscapp.Model.Timetable;
import com.jakdor.sscapp.R;
import com.jakdor.sscapp.Utilities.SimpleSectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagerFragment extends Fragment {
    int pagerFragmentNum;

    @BindView(R.id.timetable_recycler_view)
    RecyclerView recyclerView;


    //Create a new instance of CountingFragment, providing "num" as an argument.
    static PagerFragment newInstance(int num) {
        PagerFragment f = new PagerFragment();

        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    //When creating, retrieve this instance's number from its arguments.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerFragmentNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View pagerFragmentView = inflater.inflate(R.layout.fragment_pager, container, false);
        ButterKnife.bind(this, pagerFragmentView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        loadRecyclerView();

        return pagerFragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private final String[] sectionText = {
            "DAY I - THURSDAY, 21st September",
            "DAY II - FRIDAY, 22nd September",
            "DAY III - SATURDAY, 23rd September",
            "DAY IV - SUNDAY, 24th September"};

    private void loadRecyclerView(){
        String query = "mode=" + Integer.toString(pagerFragmentNum+1);
        List<Timetable> timetables = Timetable.find(Timetable.class, query);

        if(timetables.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            return;
        }

        TimetableAdapter timetableAdapter = new TimetableAdapter(getContext(), timetables);

        //Provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();

        //add sections
        int currentSection = -1;
        for (int i = 0; i < timetables.size(); ++i){
            int day = timetables.get(i).getDay();
            if(currentSection != day){
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(i, sectionText[day - 1]));
                currentSection = day;
            }
        }

        //Add adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy =
                new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter =
                new SimpleSectionedRecyclerViewAdapter(getContext(),
                R.layout.section, R.id.section_title, timetableAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        recyclerView.setAdapter(mSectionedAdapter);
    }

}
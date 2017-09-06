package com.jakdor.sscapp.Timetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    @BindView(R.id.timetable_recycler_view_info)
    TextView dayInfoView;


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
            "Sharing",
            "Learning",
            "Networking"};

    private final String[] dayInfoText = {
            "THURSDAY, 21st September",
            "FRIDAY, 22nd September",
            "SATURDAY, 23rd September",
            "SUNDAY, 24th September"};

    private void loadRecyclerView(){
        String query = "day=" + Integer.toString(pagerFragmentNum + 1);
        List<Timetable> timetables = Timetable.find(Timetable.class, query);

        if(timetables.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            dayInfoView.setVisibility(View.GONE);
            return;
        }

        dayInfoView.setText(dayInfoText[pagerFragmentNum]);

        TimetableAdapter timetableAdapter =
                new TimetableAdapter(getContext(), timetables);

        //Provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();

        //add sections
        int currentSection = -1;
        for (int i = 0; i < timetables.size(); ++i){
            int mode = timetables.get(i).getMode();
            if(currentSection != mode){
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(i, sectionText[mode - 1]));
                currentSection = mode;
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
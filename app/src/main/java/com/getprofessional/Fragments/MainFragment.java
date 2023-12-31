package com.getprofessional.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getprofessional.Adapters.MainFragmentRVAdapter;
import com.getprofessional.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView mfRecyclerView = (RecyclerView)view.findViewById(R.id.main_fragment_recycler_view);
        MainFragmentRVAdapter mfAdapter = new MainFragmentRVAdapter(getActivity());
        mfRecyclerView.setAdapter(mfAdapter);
        RecyclerView.LayoutManager mfLayoutManager = new LinearLayoutManager(getActivity());
        mfRecyclerView.setLayoutManager(mfLayoutManager);
        return view;

    }//ok

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}

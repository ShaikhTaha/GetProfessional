package com.getprofessional.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.getprofessional.Activity.Book;
import com.getprofessional.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaptopMajorRepairingFragment extends Fragment {


    public LaptopMajorRepairingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_laptop_major_repairing, container, false);

        Button bookBtn = view.findViewById(R.id.ls_book_btn);

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Book.class);
                intent.putExtra("ServiceName", "Laptop Major Repairing.");
                intent.putExtra("OrderAmt", "550");
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

}

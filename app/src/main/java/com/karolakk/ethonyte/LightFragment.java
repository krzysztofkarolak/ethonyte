package com.karolakk.ethonyte;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class LightFragment extends Fragment {


    public LightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_light, container, false);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        try {
            if (visible) {
                ((EthonyteActivity) getActivity()).positionId=2;
                ((EthonyteActivity) getActivity()).getSupportActionBar().setTitle("Light");
                ((EthonyteActivity) getActivity()).changeBarColorFromFragment(R.color.green);
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: Color TopFragment change too early - before assigment!");
        }
    }

}

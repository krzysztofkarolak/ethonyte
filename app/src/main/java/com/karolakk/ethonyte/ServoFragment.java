package com.karolakk.ethonyte;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServoFragment extends Fragment {


    private View rootServoView;

    private SeekBar seekBar1;
    private SeekBar seekBar2;

    public ServoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootServoView = inflater.inflate(R.layout.fragment_servo, container, false);

        seekBar1 = (SeekBar) rootServoView.findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar) rootServoView.findViewById(R.id.seekBar2);

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                String progress = Integer.toString(progresValue);
                ((EthonyteActivity)getActivity()).makeRequestPin("V64", progress, "putPin");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(((EthonyteActivity)getActivity()).debugState) {    System.out.println("started tracking"); }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(((EthonyteActivity)getActivity()).debugState) { System.out.println("stop tracking s1"); }
            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                String progress = Integer.toString(progresValue);
                //  System.out.println("changed progress on s2" + progress);
                ((EthonyteActivity)getActivity()).makeRequestPin("V65", progress, "putPin");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(((EthonyteActivity)getActivity()).debugState) {  System.out.println("started tracking");}
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(((EthonyteActivity)getActivity()).debugState) { System.out.println("stop tracking s2"); }
            }
        });



        return rootServoView;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            ((EthonyteActivity) getActivity()).positionId=1;
            ((EthonyteActivity) getActivity()).getSupportActionBar().setTitle("Serwa");
            ((EthonyteActivity)getActivity()).changeBarColorFromFragment(R.color.colorPrimaryDark);
        }
    }

}

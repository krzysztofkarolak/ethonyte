package com.karolakk.ethonyte;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;


public class ServoFragment extends Fragment {


    private View rootServoView, bm;

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
                if(((EthonyteActivity)getActivity()).debugState) {  System.out.println("started tracking"); }
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

        // Bind buttons
        bm = rootServoView.findViewById(R.id.magnetButton);

        setupButtonMagnet();
        checkMagnetState();


        return rootServoView;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            ((EthonyteActivity) getActivity()).positionId=1;
            ((EthonyteActivity) getActivity()).getSupportActionBar().setTitle("Serwa");
            ((EthonyteActivity)getActivity()).changeBarColorFromFragment(R.color.colorPrimaryDark);
            ((EthonyteActivity) getActivity()).checkPrefs();
            ((EthonyteActivity) getActivity()).checkMagnetVal();
            checkMagnetState();
        }
    }

    public void checkMagnetState() {
        ((EthonyteActivity) getActivity()).checkMagnetVal();
        if(((EthonyteActivity) getActivity()).debugState) {System.out.println("Magnet:" + (((EthonyteActivity) getActivity()).magnetState));}
        if(((EthonyteActivity) getActivity()).magnetState) {
            bm.setPressed(true);
        }
        else {
            bm.setPressed(false);
        }
    }

    public void setupButtonMagnet() {
        bm.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent evt) {
                        int action = evt.getAction();
                        if(action == MotionEvent.ACTION_DOWN) {
                            if(((EthonyteActivity) getActivity()).magnetState) {
                                ((EthonyteActivity) getActivity()).makeRequestPin("D2", "0", "putPin");
                                ((EthonyteActivity) getActivity()).magnetState = false;
                                bm.setPressed(false);
                            }
                            else {
                                ((EthonyteActivity) getActivity()).makeRequestPin("D2", "1", "putPin");
                                ((EthonyteActivity) getActivity()).magnetState = true;
                                bm.setPressed(true);
                            }
                        }
                        else if(action == MotionEvent.ACTION_UP) {
                        }
                        return true;
                    }

                });
    }

}

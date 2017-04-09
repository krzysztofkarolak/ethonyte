package com.karolakk.ethonyte;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {


    public TopFragment() {
        // Required empty public constructor
    }

    private View b1, b2, b3, b4, rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_top, container, false);

        // Bind buttons
        b1 = rootView.findViewById(R.id.sendButton);
        b2 = rootView.findViewById(R.id.sendButton2);
        b3 = rootView.findViewById(R.id.sendButton3);
        b4 = rootView.findViewById(R.id.sendButton4);

        setupButton1();
        setupButton2();
        setupButton3();
        setupButton4();

        return rootView;
    }

    //Setup buttons

    public void setupButton1() {
        b1.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent evt) {
                        int action = evt.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            ((EthonyteActivity) getActivity()).makeRequestPin("D48", "1", "putPin");
                            if(((EthonyteActivity)getActivity()).debugState) {((EthonyteActivity) getActivity()).makethisToastup("D48 V:1");}
                            b1.setPressed(true);
                        } else if (action == MotionEvent.ACTION_UP) {
                            ((EthonyteActivity) getActivity()).makeRequestPin("D48", "0", "putPin");
                            if(((EthonyteActivity)getActivity()).debugState) { ((EthonyteActivity) getActivity()).makethisToastup("D48 V:0");}
                            b1.setPressed(false);
                        }
                        return true;
                    }

                });
    }
    public void setupButton2() {
        b2.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent evt) {
                        int action = evt.getAction();
                        if(action == MotionEvent.ACTION_DOWN) {
                            ((EthonyteActivity)getActivity()).makeRequestPin("D50", "1", "putPin");
                            if(((EthonyteActivity)getActivity()).debugState) {((EthonyteActivity)getActivity()).makethisToastup("D50 V:1");}
                            b2.setPressed(true);
                        }
                        else if(action == MotionEvent.ACTION_UP) {
                            ((EthonyteActivity)getActivity()).makeRequestPin("D50", "0", "putPin");
                            if(((EthonyteActivity)getActivity()).debugState) {((EthonyteActivity)getActivity()).makethisToastup("D50 V:0");}
                            b2.setPressed(false);
                        }
                        return true;
                    }

                });
    }
    public void setupButton3() {
        b3.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent evt) {
                        int action = evt.getAction();
                        if(action == MotionEvent.ACTION_DOWN) {
                            ((EthonyteActivity)getActivity()).makeRequestPin("D49", "1", "putPin");
                            if(((EthonyteActivity)getActivity()).debugState) {((EthonyteActivity)getActivity()).makethisToastup("D49 V:1");}
                            b3.setPressed(true);
                        }
                        else if(action == MotionEvent.ACTION_UP) {
                            ((EthonyteActivity)getActivity()).makeRequestPin("D49", "0", "putPin");
                            if(((EthonyteActivity)getActivity()).debugState) {((EthonyteActivity)getActivity()).makethisToastup("D49 V:0");}
                            b3.setPressed(false);
                        }
                        return true;
                    }

                });
    }
    public void setupButton4() {
        b4.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent evt) {
                        int action = evt.getAction();
                        if(action == MotionEvent.ACTION_DOWN) {
                            ((EthonyteActivity)getActivity()).makeRequestPin("D51", "1", "putPin");
                            if(((EthonyteActivity)getActivity()).debugState) {((EthonyteActivity)getActivity()).makethisToastup("D51 V:1");}
                            b4.setPressed(true);
                        }
                        else if(action == MotionEvent.ACTION_UP) {
                            ((EthonyteActivity)getActivity()).makeRequestPin("D51", "0", "putPin");
                            if(((EthonyteActivity)getActivity()).debugState) { ((EthonyteActivity)getActivity()).makethisToastup("D51 V:0");}
                            b4.setPressed(false);
                        }
                        return true;
                    }

                });
    }


    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        try {
            if (visible) {
                ((EthonyteActivity) getActivity()).positionId=0;
                ((EthonyteActivity) getActivity()).getSupportActionBar().setTitle("Ethonyte");
                ((EthonyteActivity) getActivity()).changeBarColorFromFragment(R.color.colorPrimary);
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: Color TopFragment change too early - before assigment!");
        }
    }


}


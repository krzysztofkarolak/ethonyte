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

    private View b1, b2, b3, b4, bb, bled, rootView;
    private boolean bledState = false;

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
        bb = rootView.findViewById(R.id.buzzerButton);
        bled = rootView.findViewById(R.id.instantLEDButton);

        setupButton1();
        setupButton2();
        setupButton3();
        setupButton4();
        setupBuzzerButton();
        setupLEDButton();

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

    public void setupBuzzerButton() {
        bb.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent evt) {
                        int action = evt.getAction();
                        if(action == MotionEvent.ACTION_DOWN) {
                                ((EthonyteActivity)getActivity()).makeRequestPin("D11", "1", "putPin");
                                if(((EthonyteActivity)getActivity()).debugState) {((EthonyteActivity)getActivity()).makethisToastup("Buzzer On");}
                                bb.setPressed(true);
                        }
                        else if(action == MotionEvent.ACTION_UP) {
                            ((EthonyteActivity)getActivity()).makeRequestPin("D11", "0", "putPin");
                            if(((EthonyteActivity)getActivity()).debugState) {((EthonyteActivity)getActivity()).makethisToastup("Buzzer Off");}
                            bb.setPressed(false);
                        }
                        return true;
                    }

                });
    }

    public void setupLEDButton() {
        bled.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent evt) {
                        int action = evt.getAction();
                        if(action == MotionEvent.ACTION_DOWN) {
                            if(bledState) {
                                ((EthonyteActivity)getActivity()).makeRequestPin("V56", "0", "putPin");
                                ((EthonyteActivity)getActivity()).makeRequestPin("V57", "0", "putPin");
                                ((EthonyteActivity)getActivity()).makeRequestPin("V58", "0", "putPin");
                                bled.setPressed(false);
                                bledState = false;
                            } else {
                                ((EthonyteActivity)getActivity()).makeRequestPin("V56", "255", "putPin");
                                ((EthonyteActivity)getActivity()).makeRequestPin("V57", "255", "putPin");
                                ((EthonyteActivity)getActivity()).makeRequestPin("V58", "255", "putPin");
                                bled.setPressed(true);
                                bledState = true;
                            }
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


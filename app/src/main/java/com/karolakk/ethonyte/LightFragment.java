package com.karolakk.ethonyte;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class LightFragment extends Fragment {

    private View rootLightView;
    private int cR,cG,cB;

    public LightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootLightView = inflater.inflate(R.layout.fragment_light, container, false);
        ColorPickerView colorPickerView = (ColorPickerView) rootLightView.findViewById(R.id.color_picker_view);
        colorPickerView.addOnColorChangedListener(new OnColorChangedListener() {
            @Override public void onColorChanged(int selectedColor) {
                // Handle on color change
                if(((EthonyteActivity)getActivity()).debugState) { Log.d("ColorPicker", "onColorChanged: 0x" + Integer.toHexString(selectedColor));}

                if(!((EthonyteActivity) getActivity()).lessRequestsVar) {
                    sendColor(selectedColor);
                }

            }
        });
        colorPickerView.addOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int selectedColor) {

                if(((EthonyteActivity) getActivity()).lessRequestsVar) {
                    sendColor(selectedColor);
                }

            }
        });

        // Inflate the layout for this fragment
        return rootLightView;
    }

    private void sendColor(int selectedColor) {
        int color = Color.parseColor("#" + Integer.toHexString(selectedColor).toUpperCase());
        //int cA = (color >> 24) & 0xff;
        cR = (color >> 16) & 0xff;
        cG = (color >>  8) & 0xff;
        cB = (color      ) & 0xff;
        ((EthonyteActivity) getActivity()).makeRequestPin("V56", Integer.toString(cR), "putPin");
        ((EthonyteActivity) getActivity()).makeRequestPin("V57", Integer.toString(cG), "putPin");
        ((EthonyteActivity) getActivity()).makeRequestPin("V58", Integer.toString(cB), "putPin");
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        try {
            if (visible) {
                ((EthonyteActivity) getActivity()).positionId=2;
                ((EthonyteActivity) getActivity()).getSupportActionBar().setTitle("Światło");
                ((EthonyteActivity) getActivity()).changeBarColorFromFragment(R.color.green);
                ((EthonyteActivity) getActivity()).checkPrefs();
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: Color TopFragment change too early - before assigment!");
        }
    }

}

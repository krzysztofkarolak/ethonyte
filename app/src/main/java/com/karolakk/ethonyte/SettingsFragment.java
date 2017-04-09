package com.karolakk.ethonyte;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((EthonyteActivity) getActivity()).checkPrefs();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((EthonyteActivity) getActivity()).checkPrefs();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((EthonyteActivity) getActivity()).checkPrefs();
    }


    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        try {
            if (visible) {
                ((EthonyteActivity) getActivity()).positionId=3;
                ((EthonyteActivity) getActivity()).getSupportActionBar().setTitle("Ustawienia");
                ((EthonyteActivity) getActivity()).changeBarColorFromFragment(R.color.colorAccent);

            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: Color SettingsFragment change too early - before assigment!");
        }
    }

}

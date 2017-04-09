package com.karolakk.ethonyte;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Timer;
import java.util.TimerTask;

public class EthonyteActivity extends AppCompatActivity {

    private MyPagerAdapter adapter;
    private Drawable oldBackground = null;
    private int currentColor;
    private SystemBarTintManager mTintManager;

    private Toolbar toolbar;
    private ViewPager pager;
    private TabLayout tabLayout;

    //From Settings
    private String tokenVar;
    private String portVar;
    private String urlVar;
    private boolean measureVar;
    public boolean debugState;

    Timer timerAsync = new Timer();
    private TimerTask timerTaskAsync;

    public int positionId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethonyte);




        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Initialize the ViewPager and set an adapter
        pager = (ViewPager) findViewById(R.id.pager);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(pager);

        setSupportActionBar(toolbar);

        // create our manager instance after the content view is set
        mTintManager = new SystemBarTintManager(this);
        // enable status bar tint
        mTintManager.setStatusBarTintEnabled(true);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setCurrentItem(0);
        changeBarColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));

        //Tabs icons
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_touchwhite);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_servo);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_light);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_settings);


        //Preferences init
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        urlVar = sharedPref.getString("keyServerAddress", "127.0.0.1");
        portVar = sharedPref.getString("keyPortAddress", "9999");
        tokenVar = sharedPref.getString("keyTokenAddress", "0000");
        measureVar = sharedPref.getBoolean("keyMeasureSetting", false);

        timerTaskAsync = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        checkMeasureValue();
                        makeRequestGetPin("V2", "status");
                    }
                });
            }
        };

        checkMeasureSetting();

    }

    public void checkPrefs() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        urlVar = sharedPref.getString("keyServerAddress", "127.0.0.1");
        portVar = sharedPref.getString("keyPortAddress", "9999");
        tokenVar = sharedPref.getString("keyTokenAddress", "0000");
        measureVar = sharedPref.getBoolean("keyMeasureSetting", false);
        debugState = sharedPref.getBoolean("keyEnableDebug", false);
        checkMeasureSetting();
    }

    public void checkMeasureSetting() {
        if(measureVar) {
            makeRequestPin("V62", "1", "putPin");
            makeRequestPin("V63", "1", "putPin");
        }
        else {
            makeRequestPin("V62", "0", "putPin");
            makeRequestPin("V63", "0", "putPin");
        }
    }
    private void checkMeasureValue() {
        if (measureVar) {
            makeRequestGetPin("V5", "measure1");
            makeRequestGetPin("V6", "measure2");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkPrefs();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPrefs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            timerAsync.schedule(timerTaskAsync, 0, 1000);
        } catch (Exception e) {
            System.out.println("Timer already defined.");
        }
        checkPrefs();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            timerAsync.cancel();
        } catch (Exception e) {
            System.out.println("Timer already defined.");
        }
        checkPrefs();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerAsync.cancel();
        checkPrefs();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerAsync.cancel();
        checkPrefs();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            timerTaskAsync.cancel();
            timerAsync.cancel();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_to_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        changeBarColor(currentColor);
    }

    public void changeBarColorFromFragment(int barcolor) {
        changeBarColor(ContextCompat.getColor(getBaseContext(), barcolor));
    }

    private void changeBarColor(int newColor) {
        tabLayout.setBackgroundColor(newColor);
        try {
        mTintManager.setTintColor(newColor);
        // change ActionBar color just if an ActionBar is available
        Drawable colorDrawable = new ColorDrawable(newColor);
        Drawable bottomDrawable = new ColorDrawable(ContextCompat.getColor(getBaseContext(), android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});

            if (oldBackground == null) {
                getSupportActionBar().setBackgroundDrawable(ld);
            } else {
                TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});
                getSupportActionBar().setBackgroundDrawable(td);
                td.startTransition(30);
            }


        oldBackground = ld;
        currentColor = newColor;
        } catch (Exception e) {
            System.out.println("Color exception");
        }
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Main", "Servo", "Colors", "Settings"};

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
/*
        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
*/
        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TopFragment tab1 = new TopFragment();
                    return tab1;
                case 1:
                    ServoFragment tab2 = new ServoFragment();
                    return tab2;
                case 2:
                    LightFragment tab3 = new LightFragment();
                    return tab3;
                case 3:
                    SettingsFragment tab4 = new SettingsFragment();
                    return tab4;
                default:
                    TopFragment tabd = new TopFragment();
                    return tabd;
            }
        }
    }


    public void makethisToastup(String stringText){
        Toast.makeText(this, stringText, Toast.LENGTH_SHORT).show();
    }

    /* Server requests */


    public void makeRequestPin(String pinVar, String valVar, String RespondableType) {
        makeGetRequest("http://" + urlVar + ":" + portVar + "/" + tokenVar + "/update/" + pinVar + "?value=" + valVar, RespondableType);
    }
    public void makeRequestGetPin(String pinVar, String RespondableType) {
        makeGetRequest("http://" + urlVar + ":" + portVar + "/" + tokenVar + "/get/" + pinVar, RespondableType);
    }

    private RequestQueue queue;
    public void makeGetRequest(String url, final String isRespondableType) {
        final TextView textViewResults = (TextView) findViewById(R.id.textViewResults);

        if (queue == null) {
            queue = Volley.newRequestQueue(this);
        }

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if(isRespondableType=="status") {
                            try {
                                responseHandler(response, "status");
                            } catch (IndexOutOfBoundsException e) {
                                e.printStackTrace();
                                System.out.println("Invalid indexes or empty string");
                            }
                            catch (NullPointerException e) {
                                e.printStackTrace();
                                System.out.println("Something went wrong, missed initialization");
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("Something unexpected happened, move on or can see stacktrace ");
                            }


                        }
                        else if(isRespondableType=="measure1") {
                            responseHandler(response, "measure1");
                        }

                        else if(isRespondableType=="measure2") {
                            responseHandler(response, "measure2");
                        }
                        else if(isRespondableType!="putPin") {
/*
                               try {
                               responseHandler(response.substring(0, 3), "unknown");
                               } catch (IndexOutOfBoundsException e) {
                                       e.printStackTrace();
                                       System.out.println("Invalid indexes or empty string");
                                   }
                                   catch (NullPointerException e) {
                                       e.printStackTrace();
                                       System.out.println("Something went wrong, missed initialization");
                                   }
                                   catch (Exception e) {
                                       e.printStackTrace();
                                       System.out.println("Something unexpected happened, move on or can see stacktrace ");
                                   }*/

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(positionId==0){
                    try {
                        textViewResults.setText(getString(R.string.server_connection_error));
                    } catch (Exception e) {
                        System.out.println("EXCEPTION: no screen to show the error");
                    }
                }
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



    void responseHandler(String responseText, String responseType) {

        try {
            if (positionId == 0) {
                final TextView textViewResults = (TextView) findViewById(R.id.textViewResults);
                final TextView textViewMeasure1 = (TextView) findViewById(R.id.textViewMeasure1);
                final TextView textViewMeasure2 = (TextView) findViewById(R.id.textViewMeasure2);
                if (responseType.equals("status")) {
                    textViewResults.setText(responseText.replaceAll("[\\[\\]\":,]", ""));
                } else if (responseType.equals("unknown")) {
                    textViewResults.setText(responseText);
                } else if (responseType.equals("measure1")) {
                    textViewMeasure1.setText(responseText.replaceAll("[\\[\\]\":,]", "") + " cm");
                } else if (responseType.equals("measure2")) {
                    textViewMeasure2.setText(responseText.replaceAll("[\\[\\]\":,]", "") + " cm");
                } else {
                    //  makethisToastup(responseText);
                }
            }
        } catch(Exception e) {
            System.out.print("EXCEPTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
    }



}

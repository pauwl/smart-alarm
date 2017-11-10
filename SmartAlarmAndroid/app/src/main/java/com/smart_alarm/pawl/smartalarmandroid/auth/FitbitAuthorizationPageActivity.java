package com.smart_alarm.pawl.smartalarmandroid.auth;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;

import com.smart_alarm.pawl.smartalarmandroid.properties.IRawProperties;
import com.smart_alarm.pawl.smartalarmandroid.properties.fitbit.FitbitProperties;


/**
 * A login screen that offers login via email/password.
 */
public class FitbitAuthorizationPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        openFitbitAuthPage(intent.getStringExtra("fitbit_client_id"));
    }

    /**
     * Open Chrome Custom Tab on Fitbit OAuth 2.0 Authorization Page
     */
    private void openFitbitAuthPage(String fitbitClientId) {
        IRawProperties fitbitProperties = new FitbitProperties();
        String fitbitAuthPageUrlString = fitbitProperties.getPropertyValue(
                getApplicationContext(),
                "fitbit_auth_url");
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        Uri fitbitAuthPageUrl = Uri.parse(fitbitAuthPageUrlString)
                .buildUpon()
                .appendQueryParameter("client_id", fitbitClientId)
                .build();
        customTabsIntent.launchUrl(this, fitbitAuthPageUrl);
    }
}


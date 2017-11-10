package com.smart_alarm.pawl.smartalarmandroid.auth;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;

import com.smart_alarm.pawl.smartalarmandroid.properties.IRawProperties;
import com.smart_alarm.pawl.smartalarmandroid.properties.fitbit.FitbitProperties;

/**
 * Allows User (Resource Owner) to authorize SmartAlarm app to
 * access to Fitbit Web API on behalf of the User
 */
public class FitbitAuthorizationPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        openFitbitAuthPage(intent.getStringExtra("fitbit_client_id"));
    }

    /**
     * Opens Chrome Custom Tab with Fitbit OAuth 2.0 Authorization Page
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


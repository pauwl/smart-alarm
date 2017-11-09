package com.smart_alarm.pawl.smartalarmandroid.auth;


import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.smart_alarm.pawl.smartalarmandroid.R;
import com.smart_alarm.pawl.smartalarmandroid.properties.IRawProperties;
import com.smart_alarm.pawl.smartalarmandroid.properties.smart_alarm.SmartAlarmProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * A login screen that offers login via email/password.
 */
public class FitbitAuthorizationActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private GetClientIdTask mGetClientIdTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitbit_authorization);

        Button mAuthorizeButton = (Button) findViewById(R.id.authorize_button);
        mAuthorizeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mGetClientIdTask != null) {
            return;
        }

        boolean cancel = false;

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
        } else {
            mGetClientIdTask = new GetClientIdTask();
            mGetClientIdTask.execute((Void) null);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GetClientIdTask extends AsyncTask<Void, Void, String> {
        private static final String TAG = "GetClientId";

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            URL smartAlarmClientIdUrl;
            StringBuilder fitbitClientIdBuilder = new StringBuilder();
            String fitbitClientId;
            IRawProperties smartAlarmProperties = new SmartAlarmProperties();
            try {
                String smartAlarmUrl = smartAlarmProperties.getPropertyValue(
                        getApplicationContext(),
                        "smart_alarm_url");
                String smartAlarmClientIdEndpoint = smartAlarmProperties.getPropertyValue(
                        getApplicationContext(),
                        "client_id_endpoint"
                );
                smartAlarmClientIdUrl = new URL(
                        new URL(smartAlarmUrl),
                        smartAlarmClientIdEndpoint);
            } catch (Resources.NotFoundException e) {
                return null;
            } catch (MalformedURLException e) {
                Log.d(TAG, "Not valid URL to Smart Alarm server");
                return null;
            }


            try {
                URLConnection urlConnection = smartAlarmClientIdUrl.openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader inBuffered = new BufferedReader(new InputStreamReader(
                        in));

                String readChars;
                while ((readChars = inBuffered.readLine()) != null) {
                    fitbitClientIdBuilder.append(readChars);
                }
                in.close();

            } catch (IOException e) {
                Log.d(TAG, "Failed to receive response from" +
                        " Fitbit Client ID endpoint\n" + e.getMessage());
                return null;
            }
            fitbitClientId = fitbitClientIdBuilder.toString();
            return fitbitClientId;
        }

        @Override
        protected void onPostExecute(String clientId) {
            mGetClientIdTask = null;

            if (clientId != null) {
                finish();

                // TODO Open fitbit authorization page
            } else {
                // TODO show dialog about error
                Log.d(TAG, TAG + " task failed");
            }
        }

        @Override
        protected void onCancelled() {
            mGetClientIdTask = null;
        }
    }

}

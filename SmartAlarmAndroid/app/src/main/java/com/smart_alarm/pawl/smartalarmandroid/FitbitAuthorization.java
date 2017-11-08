package com.smart_alarm.pawl.smartalarmandroid;


import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.smart_alarm.pawl.smartalarmandroid.fitbit.FitbitProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class FitbitAuthorization extends AppCompatActivity {

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
            String smartAlarmUrl;
            StringBuilder fitbitClientIdBuilder = new StringBuilder();
            String fitbitClientId;
            try {
                smartAlarmUrl = FitbitProperties.getPropertyValue(
                        getApplicationContext(),
                        "smart_alarm_url");
            } catch (Resources.NotFoundException e) {
                return null;
            }

            try {
                URL url = new URL(smartAlarmUrl);
                URLConnection urlConnection = url.openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader inBuffered = new BufferedReader(new InputStreamReader(
                        in));
                String readChars;
                while ((readChars = inBuffered.readLine()) != null) {
                    fitbitClientIdBuilder.append(readChars);
                }
            } catch (MalformedURLException e) {
                Log.d(TAG, "Not valid URL to Smart Alarm server");
                return null;
            } catch (IOException e) {
                Log.d(TAG, "Failed to receive response from Fitbit Client ID endpoint");
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


package com.smart_alarm.pawl.smartalarmandroid.auth;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
                getClientId();
            }
        });

    }

    /**
     * Retrieves SmartAlarm Client ID
     */
    private void getClientId() {
        if (mGetClientIdTask != null) {
            return;
        }

        boolean cancel = false;

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
        } else {
            mGetClientIdTask = new GetClientIdTask((Context) this);
            mGetClientIdTask.execute((Void) null);
        }
    }

    /**
     * Represents an asynchronous task used to retrieve
     * SmartAlarm Client ID required to grant access to Fitbit Web API.
     */
    public class GetClientIdTask extends AsyncTask<Void, Void, String> {
        private static final String TAG = "GetClientId";
        Context activity = null;

        GetClientIdTask(Context activity) {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(Void... params) {
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
            try {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(fitbitClientId, JsonObject.class);
                fitbitClientId = jsonObject.get( "fitbit_client_id").getAsString();
            } catch (NullPointerException e) {
                Log.d(TAG,"Cannot find Fitbit Client ID in response");
                fitbitClientId = null;
            }
            return fitbitClientId;
        }

        @Override
        protected void onPostExecute(String clientId) {
            mGetClientIdTask = null;

            if (clientId != null) {
                finish();
                Intent fitbitAuthPageIntent = new Intent(
                        activity,
                        FitbitAuthorizationPageActivity.class);
                fitbitAuthPageIntent.putExtra("fitbit_client_id", clientId);
                startActivity(fitbitAuthPageIntent);
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


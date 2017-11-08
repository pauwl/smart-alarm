package com.smart_alarm.pawl.smartalarmandroid.fitbit;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.smart_alarm.pawl.smartalarmandroid.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class FitbitProperties {
    private static final String TAG = "FitbitProperties";

    public static String getPropertyValue(Context context, String name) {
        Resources resources = context.getResources();
        String error_msg;
        try {
            InputStream rawResource = resources.openRawResource(R.raw.fitbit);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(name);
        } catch (Resources.NotFoundException e) {
            error_msg = "Unable to find the config file: " + e.getMessage();
            Log.e(TAG, error_msg);
        } catch (IOException e) {
            error_msg = "Failed to open config file.";
            Log.e(TAG, error_msg);
        }

        throw new Resources.NotFoundException(error_msg);
    }
}
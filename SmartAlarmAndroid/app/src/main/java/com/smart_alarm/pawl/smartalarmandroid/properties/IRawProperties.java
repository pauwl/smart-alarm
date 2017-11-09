package com.smart_alarm.pawl.smartalarmandroid.properties;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.smart_alarm.pawl.smartalarmandroid.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public interface IRawProperties {
    abstract int rawPropertyId();
    abstract String logTag();

    default String getPropertyValue(Context context, String name) {
        Resources resources = context.getResources();
        String error_msg;
        try {
            InputStream rawResource = resources.openRawResource(this.rawPropertyId());
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(name);
        } catch (Resources.NotFoundException e) {
            error_msg = "Unable to find the config file: " + e.getMessage();
            Log.e(this.logTag(), error_msg);
        } catch (IOException e) {
            error_msg = "Failed to open config file.";
            Log.e(this.logTag(), error_msg);
        }

        throw new Resources.NotFoundException(error_msg);
    }
}
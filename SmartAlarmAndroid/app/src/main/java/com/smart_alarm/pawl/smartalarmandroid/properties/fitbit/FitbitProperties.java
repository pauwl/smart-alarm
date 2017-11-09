package com.smart_alarm.pawl.smartalarmandroid.properties.fitbit;

import com.smart_alarm.pawl.smartalarmandroid.R;
import com.smart_alarm.pawl.smartalarmandroid.properties.IRawProperties;


public final class FitbitProperties implements IRawProperties {
    public int rawPropertyId() {
        return R.raw.fitbit;
    }
    public String logTag() {
        return "FitbitProperties";
    }
}

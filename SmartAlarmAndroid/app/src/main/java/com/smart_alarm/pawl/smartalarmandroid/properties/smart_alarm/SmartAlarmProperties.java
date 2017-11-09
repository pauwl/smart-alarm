package com.smart_alarm.pawl.smartalarmandroid.properties.smart_alarm;


import com.smart_alarm.pawl.smartalarmandroid.R;
import com.smart_alarm.pawl.smartalarmandroid.properties.IRawProperties;


public final class SmartAlarmProperties implements IRawProperties {
    public int rawPropertyId() {
        return R.raw.smart_alarm;
    }
    public String logTag() {
        return "SmartAlarmProperties";
    }
}
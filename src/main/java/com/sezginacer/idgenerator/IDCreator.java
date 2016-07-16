package com.sezginacer.idgenerator;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by Sezgin on 11.07.2016
 */

public class IDCreator {
    private Context context = null;

    public IDCreator(Context context){
        this.context = context;
    }

    private String getMacAddress(){
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return manager.getConnectionInfo().getMacAddress();
    }

    private String getBluetoothHWaddr(){
        return android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
    }

    private String getIMEI(){
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    private String getSimSerial(){
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getSimSerialNumber();
    }

    private String getAndroidVersion(){
        return Build.VERSION.RELEASE;
    }

    private String getAndroidID(){
        return android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

    public String getAndroidMobileID(){
        String ret = getAndroidID();
        ret += getAndroidVersion();
        ret += getSimSerial();
        ret += getIMEI();
        ret += getBluetoothHWaddr();
        ret += getMacAddress();
        return Hex.encodeHex(DigestUtils.sha1Hex(ret));
    }
}

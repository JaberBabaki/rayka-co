package com.raykaco.andriod.internet;

import java.io.IOException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.raykaco.andriod.G;


public class CheckInternet {

    public boolean Access() {
        ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (isConecctedToInternet()) {
            return true;
        }
        return false;
    }


    public boolean isConecctedToInternet() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void refresh() {
        // ShopFinderOrginalActivity.vpPager.setCurrentItem(4);
        //ShopFinderOrginalActivity.vpPager.re);
    }


    public void goToSettingWiFiNet() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
        G.currentActivity.startActivity(intent);

    }


    public void goToSettingMobileNet() {
        final Intent dataUsage = new Intent(Intent.ACTION_MAIN);
        dataUsage.setClassName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity");
        G.currentActivity.startActivity(dataUsage);
    }
}

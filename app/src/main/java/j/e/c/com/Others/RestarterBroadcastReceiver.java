package j.e.c.com.Others;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import j.e.c.com.services.AutoStartService;

public class RestarterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(RestarterBroadcastReceiver.class.getName(), "Service Stopped, but this is a never ending service.");
        //Toast.makeText(context, "Service Stopped, but this is a never ending service.", Toast.LENGTH_SHORT).show();
        context.startService(new Intent(context, AutoStartService.class));
    }
}

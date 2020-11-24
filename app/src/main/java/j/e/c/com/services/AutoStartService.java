package j.e.c.com.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import j.e.c.com.Others.RestarterBroadcastReceiver;

public class AutoStartService extends Service {

    private static final String TAG = "AutoService";
    public int counter = 0;
    private Timer timer;
    private TimerTask timerTask;
    Context context;

    public AutoStartService(){}

    public AutoStartService(Context applicationContext) {
        this.context = applicationContext;
        Log.i(TAG, "AutoStartService: Here we go.....");
        //Toast.makeText(context, "AutoStartService: Here we go.....", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();

        Intent service = new Intent(getApplicationContext(), FirebaseMessageService.class);
        startService(service);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: Service is destroyed :( ");
        //Toast.makeText(context, "onDestroy: Service is destroyed :( ", Toast.LENGTH_SHORT).show();
        Intent broadcastIntent = new Intent(this, RestarterBroadcastReceiver.class);

        sendBroadcast(broadcastIntent);
        stoptimertask();
    }


    private void startTimer() {
        timer = new Timer();

        //initialize the TimerTask's job
        initialiseTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    private void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void initialiseTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "Timer is running " + counter++);
                //Toast.makeText(context, "Timer is running " + counter++, Toast.LENGTH_SHORT).show();

            }
        };
    }
}

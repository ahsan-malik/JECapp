package j.e.c.com.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;


public class AutoStartService extends Service {

    private static final String TAG = "AutoService";
    public int counter = 0;
    private Timer timer;
    private TimerTask timerTask;
    Context context;

    public AutoStartService(){}

    public AutoStartService(Context applicationContext) {
        super();
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

        //Intent service = new Intent(getApplicationContext(), FirebaseMessageService.class);
        //startService(service);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: Service is destroyed :( ");
        //Toast.makeText(context, "onDestroy: Service is destroyed :( ", Toast.LENGTH_SHORT).show();
        //Intent broadcastIntent = new Intent(this, RestarterBroadcastReceiver.class);
        Intent broadcastIntent = new Intent("j.e.c.com.ActivityRecognition.RestartSensor");

        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i(TAG, "serviceonTaskRemoved()");



        // workaround for kitkat: set an alarm service to trigger service again
        Intent intent = new Intent(getApplicationContext(), AutoStartService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5000, pendingIntent);

        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, "onLowMemory()");
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

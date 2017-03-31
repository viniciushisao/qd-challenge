package com.hisao.qdrestaurant.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.widget.Toast;

import com.hisao.qdrestaurant.database.DbHelper;

public class QdService extends Service {


    private final int ONE_MINUTE = 60000;
    private final int TIME_TO_RESET_RESERVATIONS = 10 * ONE_MINUTE;

    public QdService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Runnable mRunnable;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Handler mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                DbHelper myDBHelper = new DbHelper(getApplicationContext());
                int amountTablesAffected = myDBHelper.setAllTablesEmpty();
                Toast.makeText(getApplicationContext(), "amountTables: " + amountTablesAffected, Toast.LENGTH_LONG).show();
                mHandler.postDelayed(mRunnable, TIME_TO_RESET_RESERVATIONS);
            }
        };
        mHandler.postDelayed(mRunnable, TIME_TO_RESET_RESERVATIONS);

        return START_STICKY;
    }
}

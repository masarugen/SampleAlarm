package me.gensan.android.samplealarm;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class SampleAlarm extends Activity {
	
	private PendingIntent mSender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_alarm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sample_alarm, menu);
        return true;
    }

    /**
     * アラームを設定
     */
	public void setAlarm(View view) {
    	Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
    	mSender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(System.currentTimeMillis());
    	calendar.add(Calendar.SECOND, 10);
    	AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    	alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), mSender);
    	Toast.makeText(getApplicationContext(), "Alarm Setting", Toast.LENGTH_SHORT).show();
    }

    /**
     * アラーム(リピート)を設定
     * 最初１０秒後にアラートが通知され、その後５秒毎にアラート通知が来る
     */
	public void setRepeat(View view) {
    	Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
    	mSender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(System.currentTimeMillis());
    	calendar.add(Calendar.SECOND, 10);
    	long firstTime = calendar.getTimeInMillis();
    	long repeatTime = 5000;
    	AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    	alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, firstTime, repeatTime, mSender);
    	Toast.makeText(getApplicationContext(), "Alarm Repeat Setting", Toast.LENGTH_SHORT).show();
    }
	
	/**
	 * リピートの解除
	 */
	public void cancelRepeat(View view) {
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		if (mSender != null) {
			alarmManager.cancel(mSender);
		}
	}

}

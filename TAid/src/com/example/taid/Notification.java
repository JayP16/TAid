package com.example.taid;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;

public class Notification extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showNotification();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}
	
	private void showNotification(){		
		//Basic Notification
		
		
		//Get the current year,month,day and time
	    Calendar c = Calendar.getInstance(); 
		int day = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		int time = c.get(Calendar.HOUR_OF_DAY);
		c.get(Calendar.SECOND);
		//------------------------
		//building notification
		NotificationCompat.Builder mBuilder =
		new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.bk_red)
		.setContentTitle("notification")
		.setContentText("Current time is "+year+" "+month+" "+day+" "+time);
		//-------------------
		//Go back to the app on click
		Intent resultIntent = new Intent(this, FullscreenActivity.class);
		PendingIntent resultPendingIntent =
		    PendingIntent.getActivity(
		    this,
		    0,
		    resultIntent,
		    PendingIntent.FLAG_UPDATE_CURRENT
		);
				
		//-------------------
		mBuilder.setContentIntent(resultPendingIntent);
		//------------
		// Sets an ID for the notification
		int mNotificationId = 001;
		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr = 
		        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
		//--------------------
		//Notification		
	}

}

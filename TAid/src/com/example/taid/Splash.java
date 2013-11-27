package com.example.taid;

import com.example.taid.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread th = new Thread()
		{
			public void run()
			{
				try
				{
					sleep(3000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				finally
				{
					//Intent must match the android manifest
					Intent openStartingPoint = new Intent("com.example.taid.FullscreenActivity");
					//Starts a new activity so splash calls it's onPause method. In the onPause, it gets rid of the splash
					startActivity(openStartingPoint);
				}
			}
		};
		th.start();
				
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		finish(); //gets rid of the activity
	}

}

package com.example.taid;

import UserInformation.LessonPlan;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayLessonPlan extends Activity
{
	
	private TextView header;
	private TextView content;
	private LessonPlan lessonPlan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaylessonplan);
		init();
	}
	
	private void init()
	{
		header = (TextView)findViewById(R.id.displayLessonHeader);
		content = (TextView)findViewById(R.id.displayLessonContent);
		
		Intent i = getIntent();
		lessonPlan = (LessonPlan)i.getSerializableExtra("lessonPlan");
		
		header.setText(lessonPlan.getHeader());
		content.setText(lessonPlan.getContent());
	}

}

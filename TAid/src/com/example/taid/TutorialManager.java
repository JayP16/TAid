package com.example.taid;

import UserInformation.Course;
import UserInformation.TeachingAssistant;
import UserInformation.Tutorial;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class TutorialManager extends Activity implements View.OnClickListener
{
	
	private TextView course;
	private TextView tutorialSection;
	private Button studentList;
	private Button displayLesson;
	private Button createLesson;
	
	private TeachingAssistant t;
	private Course c;
	private Tutorial tut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutmanager);
		init();

		course.setText("Course: " + c.getCourseCode());
		tutorialSection.setText("Tutorial Section: " + tut.getTutCode());
	}

	private void init()
	{
		course = (TextView)findViewById(R.id.courseDisplay);
		tutorialSection = (TextView)findViewById(R.id.tutorialDisplay);
		
		studentList = (Button)findViewById(R.id.stuListButton);
		displayLesson = (Button)findViewById(R.id.displayLessonButton);
		createLesson = (Button)findViewById(R.id.createLessonButton);
		
		studentList.setOnClickListener(this);
		displayLesson.setOnClickListener(this);
		createLesson.setOnClickListener(this);
		
		Intent i = getIntent();
		t = (TeachingAssistant)i.getSerializableExtra("teachingAssistant");
		c = (Course)i.getSerializableExtra("course");
		tut = (Tutorial)i.getSerializableExtra("tutorial");
	}

	@Override
	public void onClick(View view) 
	{
		switch(view.getId())
		{
			case R.id.stuListButton:
				//Go to student list activity
				//Connect to server with student list request
			case R.id.displayLessonButton:
				//Go to display lesson list activity
				//Connect to the server with display lesson plan request
			case R.id.createLessonButton:
				//Go to create a lesson activity
		}
		
	}
	
}

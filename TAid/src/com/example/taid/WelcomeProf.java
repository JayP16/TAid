package com.example.taid;

import UserInformation.Professor;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.view.View;

public class WelcomeProf extends Activity implements View.OnClickListener
{
	
	
	private TextView welcome;
	private Button courseButton;
	private Button changePassButton;
	private Button addCourse;
	
	private Professor t;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_prof);
		init();
		Intent i = getIntent();
		t = (Professor)i.getSerializableExtra("Professor");
		welcome.setText("Welcome " + t.getUsername());
	}

	private void init()
	{
		welcome = (TextView)findViewById(R.id.welcomeTv);
		courseButton = (Button)findViewById(R.id.courseButton);
		changePassButton = (Button)findViewById(R.id.changePassButton);
		addCourse = (Button)findViewById(R.id.addCourse);
		
		courseButton.setOnClickListener(this);
		changePassButton.setOnClickListener(this);
		addCourse.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) 
	{
		switch(view.getId())
		{
			case R.id.courseButton:
				//Go to ListActivity of courses
				if (t.getCourseLoad() >= 1)
				{
					Intent intent = new Intent(this, DisplayCoursesProf.class);
				    intent.putExtra("Professor", t);
				    startActivity(intent);
				}
				break;
			case R.id.changePassButton:
		        break;
			case R.id.addCourse:

				Intent intent = new Intent(this, DisplayCoursesProf.class);
				intent.putExtra("Professor", t);
			    intent.putExtra("showAll", 1);
			    startActivity(intent);
			    break;
		}
		
	}
	


}

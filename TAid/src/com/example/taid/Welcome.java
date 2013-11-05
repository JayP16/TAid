package com.example.taid;

import UserInformation.TeachingAssistant;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class Welcome extends Activity implements View.OnClickListener
{
	
	private TextView welcome;
	private Button courseButton;
	private Button changePassButton;
	private TeachingAssistant t;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		init();
		Intent i = getIntent();
		t = (TeachingAssistant)i.getSerializableExtra("teachingAssistant");
		welcome.setText("Welcome " + t.getUsername());
	}

	private void init()
	{
		welcome = (TextView)findViewById(R.id.welcomeTv);
		courseButton = (Button)findViewById(R.id.courseButton);
		changePassButton = (Button)findViewById(R.id.changePassButton);
		
		courseButton.setOnClickListener(this);
		changePassButton.setOnClickListener(this);
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
					Intent intent = new Intent(this, DisplayCourses.class);
				    intent.putExtra("teachingAssistant", t);
				    startActivity(intent);
				}
			case R.id.changePassButton:
				//Go to Password Change activity
		}
		
	}
	
}

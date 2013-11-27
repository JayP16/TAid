package com.example.taid;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import UserInformation.Course;
import UserInformation.Globals;
import UserInformation.LessonPlan;
import UserInformation.Tutorial;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
public class CreateLessonPlan extends Activity implements View.OnClickListener{

	private Course course;
	private Tutorial tutorial;
	
	private EditText header;
	private EditText content;
	private Button save;
	private int newOrEdit; // creating new lesson plan from scratch = 0, 1 for edit
	private LessonPlan lessonPlan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createlesson);
		init();
	}
	
	private void init()
	{
		header = (EditText)findViewById(R.id.createLessonTitle);
		content = (EditText)findViewById(R.id.createLessonContent);
		save = (Button)findViewById(R.id.createLessonSaveButton);
		save.setOnClickListener(this);
		Intent i = getIntent();
		course = (Course)i.getSerializableExtra("course");
		tutorial = (Tutorial)i.getSerializableExtra("tutorial");
		newOrEdit = i.getIntExtra("newOrEdit", 0);
		if (newOrEdit == 1)
		{
			lessonPlan = (LessonPlan)i.getSerializableExtra("lessonPlan");
			header.setText(lessonPlan.getHeader());
			content.setText(lessonPlan.getContent());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_lesson_plan, menu);
		return true;
	}
	
	private void saveLessonPlan()
	{
		Socket clientSocket;
		System.out.println("Clicked Save!!");
		try
		{
			clientSocket = new Socket(Globals.ipAddress, Globals.port);
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream printwriter = new ObjectOutputStream(clientSocket.getOutputStream());
			
			//Notify the server that it's a create lesson plan command
			//Send the lesson plan data to the server
			printwriter.writeObject("createLessonPlan");
			printwriter.writeObject(newOrEdit);
			printwriter.writeObject(course.getCourseCode());
			printwriter.writeObject(tutorial.getTutCode());
			printwriter.writeObject(header.getText().toString());
			String convertedContent = content.getText().toString();
			printwriter.writeObject(convertedContent);
			
			String result = (String)in.readObject(); // 0 or 1 for successful save
			System.out.println("Result: " + result);
			if (result.equals("0"))
				Toast.makeText(getApplicationContext(), "Filename exists", Toast.LENGTH_LONG).show();
			else if (result.equals("1"))
				Toast.makeText(getApplicationContext(), "Saved...", Toast.LENGTH_LONG).show();
			
			clientSocket.close();
			in.close();
			printwriter.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View view) 
	{
		switch(view.getId())
		{
			case R.id.createLessonSaveButton:
				saveLessonPlan();
		}
		
	}

}

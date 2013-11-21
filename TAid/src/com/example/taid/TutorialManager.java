package com.example.taid;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import UserInformation.Course;
import UserInformation.Globals;
import UserInformation.LessonPlan;
import UserInformation.Student;
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
	private Button editLesson;
	
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
		editLesson = (Button)findViewById(R.id.editLessonButton);
		
		studentList.setOnClickListener(this);
		displayLesson.setOnClickListener(this);
		createLesson.setOnClickListener(this);
		editLesson.setOnClickListener(this);
		
		Intent i = getIntent();
		t = (TeachingAssistant)i.getSerializableExtra("teachingAssistant");
		c = (Course)i.getSerializableExtra("course");
		tut = (Tutorial)i.getSerializableExtra("tutorial");
	}

	private ArrayList<Student> studentListButton()
	{
     	Socket clientSocket;
		try {
			clientSocket = new Socket(Globals.ipAddress, Globals.port);
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream printwriter = new ObjectOutputStream(clientSocket.getOutputStream());
			printwriter.writeObject("getStudentList");
			printwriter.writeObject(c.getCourseCode());
			printwriter.writeObject(tut.getTutCode());
			@SuppressWarnings("unchecked")
			ArrayList<Student> students = (ArrayList<Student>)in.readObject();
			clientSocket.close();
			in.close();
			printwriter.close();
			return students;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	@SuppressWarnings("unchecked")
	private ArrayList<LessonPlan> getLessonPlans()
	{
     	Socket clientSocket;
		try {
			clientSocket = new Socket(Globals.ipAddress, Globals.port);
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream printwriter = new ObjectOutputStream(clientSocket.getOutputStream());
			printwriter.writeObject("getLessonPlans");
			printwriter.writeObject(c.getCourseCode());
			printwriter.writeObject(tut.getTutCode());
			ArrayList<LessonPlan> lp = (ArrayList<LessonPlan>)in.readObject();
			clientSocket.close();
			in.close();
			printwriter.close();
			return lp;
		}catch(Exception e) 
		{
			e.printStackTrace(); 
		}
		return null;
	}
	@Override
	public void onClick(View view) 
	{
		switch(view.getId())
		{
			case R.id.stuListButton:
				Intent intent = new Intent(this, DisplayStudents.class);
			    intent.putExtra("course", c);
			    intent.putExtra("tutorial", tut);
			    intent.putExtra("students", studentListButton());
			    startActivity(intent);
			case R.id.displayLessonButton:
				System.out.println("DISPLAY CLICKED");
				ArrayList<LessonPlan> lessonPlans = getLessonPlans();
				Intent intent3 = new Intent(this, DisplayLessonPlans.class);
				intent3.putExtra("lessonPlans", lessonPlans);
				intent3.putExtra("displayOrEdit", 0);
				startActivity(intent3);
				break;
			case R.id.createLessonButton:
				System.out.println("CREATE CLICKED");
				Intent intent2 = new Intent(this, CreateLessonPlan.class);
				intent2.putExtra("course", c);
				intent2.putExtra("tutorial", tut);
				intent2.putExtra("newOrEdit", 0);
				startActivity(intent2);
				break;
			case R.id.editLessonButton:
				Intent intent4 = new Intent(this, DisplayLessonPlans.class);
				ArrayList<LessonPlan> lessonPlans2 = getLessonPlans();
				intent4.putExtra("lessonPlans", lessonPlans2);
				intent4.putExtra("displayOrEdit", 1);
				intent4.putExtra("course", c);
				intent4.putExtra("tutorial", tut);
				startActivity(intent4);	
				break;
		}
		
	}
	
}

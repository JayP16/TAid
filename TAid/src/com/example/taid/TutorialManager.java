package com.example.taid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import UserInformation.Course;
import UserInformation.Globals;
import UserInformation.LessonPlan;
import UserInformation.Student;
import UserInformation.TeachingAssistant;
import UserInformation.Tutorial;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.view.View;

public class TutorialManager extends Activity implements View.OnClickListener
{
	private static final int PICKFILE_RESULT_CODE = 0;
	private TextView course;
	private TextView tutorialSection;
	private Button studentList;
	private Button displayLesson;
	private Button createLesson;
	private Button editLesson;
	private Button emailButton;
	private Button groupButton;
	private Button addGradeButton;
	private Button addGroupGradeButton;	
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
		emailButton = (Button)findViewById(R.id.emailProfessor);
		groupButton = (Button)findViewById(R.id.group);
		addGradeButton = (Button)findViewById(R.id.addGradeButton);
		addGroupGradeButton = (Button)findViewById(R.id.addGroupGradeButton);
		
		studentList.setOnClickListener(this);
		displayLesson.setOnClickListener(this);
		createLesson.setOnClickListener(this);
		editLesson.setOnClickListener(this);
		emailButton.setOnClickListener(this);
		groupButton.setOnClickListener(this);
		addGradeButton.setOnClickListener(this);
		addGroupGradeButton.setOnClickListener(this);
		
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
			    break;
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
			case R.id.emailProfessor:
				System.out.println("hello world....");
				Intent intent5 = new Intent(this, EmailActivity.class);
				startActivity(intent5);
				break;
			case R.id.group:
				Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
		        fileintent.setType("gagt/sdf");
		        try {
					startActivityForResult(fileintent, PICKFILE_RESULT_CODE);
		        } catch (ActivityNotFoundException e) {
		            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
		        }
				break;
			case R.id.addGradeButton:
				Intent intent6 = new Intent(this, AddGrade.class);
				intent6.putExtra("course", c);
				intent6.putExtra("tutorial", tut);
				startActivity(intent6);
				break;
			case R.id.addGroupGradeButton:
				Intent intent7 = new Intent(this, AddGroupGrade.class);
				intent7.putExtra("course", c);
				intent7.putExtra("tutorial", tut);
				startActivity(intent7);
		}
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       
        if (data == null)
            return;
        if (requestCode == PICKFILE_RESULT_CODE) {
			if (resultCode == RESULT_OK) {
                String FilePath = data.getData().getPath();
                //FilePath is your file as a string
                List<String> lines = new ArrayList<String>();
				try {
					FileReader fr = new FileReader(FilePath);
			        BufferedReader br = new BufferedReader(fr);
			        String line;
			        while ((line = br.readLine()) != null) {
			        	lines.add(line);
			        }
			        br.close();
			        
					Socket clientSocket = new Socket(Globals.ipAddress, Globals.port);
					ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
					ObjectOutputStream printwriter = new ObjectOutputStream(clientSocket.getOutputStream());
					printwriter.writeObject("addGroupFile");
					printwriter.writeObject(tut);
					printwriter.writeObject(c);
					printwriter.writeObject(lines.toArray(new String[lines.size()]));
					clientSocket.close();
					in.close();
					printwriter.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                
                
            }
		}
	}
}

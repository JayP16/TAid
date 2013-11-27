package com.example.taid;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import UserInformation.Course;
import UserInformation.Globals;
import UserInformation.Student;
import UserInformation.Tutorial;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.text.InputType;
import android.view.View;

public class AddGrade extends Activity implements View.OnClickListener
{

	private EditText assignmentName;
	private EditText assignmentTotal;
	private LinearLayout studentListLayout;
	private Button submitButton;
	
	private Course course;
	private Tutorial tut;
	
	private ArrayList<Student> students;
	private ArrayList<EditText> studentGrades;
	
	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream printwriter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addgrade);
		init();
	}
	
	private void init()
	{
		initPassedVariables(); //From tutorial manager activity
		initLayoutVariables();
		initStudentList();
	}
	
	private void initPassedVariables()
	{
		Intent i = getIntent();
		course = (Course)i.getSerializableExtra("course");	
		tut = (Tutorial)i.getSerializableExtra("tutorial");
	}
	
	private void initLayoutVariables()
	{
		assignmentName = (EditText)findViewById(R.id.assignmentName);
		assignmentTotal = (EditText)findViewById(R.id.addGradeAssignmentTotal);
		studentListLayout = (LinearLayout)findViewById(R.id.addGradeStudentListLayout);
		submitButton = (Button)findViewById(R.id.addGradeSubmitButton);
		
		submitButton.setOnClickListener(this);
	}
	
	private void initStudentList()
	{
		students = getStudentListFromServer();
		studentGrades = new ArrayList<EditText>();
		for (int i = 0; i < students.size(); i++)
		{
			LinearLayout l = new LinearLayout(this);
			l.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			l.setOrientation(LinearLayout.HORIZONTAL);
			
			TextView studentName = new TextView(this);
			studentName.setText(students.get(i).getUtorid());
			l.addView(studentName);
			
			EditText studentGrade = new EditText(this);
			studentGrade.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			l.addView(studentGrade);
			studentGrades.add(studentGrade);
			
			studentListLayout.addView(l);
		}
	}
	
	private void initSocketStreams()
	{
		try {
			clientSocket = new Socket(Globals.ipAddress, Globals.port);
			in = new ObjectInputStream(clientSocket.getInputStream());
			printwriter = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeCourseTutInfo()
	{
		try {
			printwriter.writeObject(course.getCourseCode());
			printwriter.writeObject(tut.getTutCode());	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void closeConnections()
	{
		try {
			clientSocket.close();
			in.close();
			printwriter.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeCommand(String cmd)
	{
		try {
			printwriter.writeObject(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<Student> getStudentListFromServer()
	{
		try {
			initSocketStreams();
			writeCommand("getStudentList");
			writeCourseTutInfo();
			@SuppressWarnings("unchecked")
			ArrayList<Student> students = (ArrayList<Student>)in.readObject();
			closeConnections();
			return students;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean hasInputErrors()
	{
		String assignmentName[];
		assignmentName = this.assignmentName.getText().toString().split("\\s+");
		if (assignmentName.length > 1)
		{
			Toast.makeText(getApplicationContext(), "Invalid Assignment Name", Toast.LENGTH_LONG).show();
			return true;
		}
		if (assignmentNameExists())
		{
			Toast.makeText(getApplicationContext(), "Assignment Name Exists", Toast.LENGTH_LONG).show();
			return true;
		}
		try
		{
			Integer.parseInt(assignmentTotal.getText().toString());
		} 
		catch(NumberFormatException e)
		{ 
			Toast.makeText(getApplicationContext(), "Invalid Assignment Total", Toast.LENGTH_LONG).show();
			return true; 
		}
		
		for (int i = 0; i < studentGrades.size(); i++)
		{
			try
			{
				Integer.parseInt(studentGrades.get(i).getText().toString());
			}
			catch(NumberFormatException e)
			{
				Toast.makeText(getApplicationContext(), "Invalid Student Grade: " + students.get(i).getUtorid(), Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	private boolean assignmentNameExists()
	{
		try {
			initSocketStreams();
			writeCommand("checkAssignmentName");
			writeCourseTutInfo();
			printwriter.writeObject(assignmentName.getText().toString() + ".txt");
			String response = (String)in.readObject();
			closeConnections();
			if (response.equals("0"))
				return false;
			else
				return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;			
	}
	
	private void addGradesToStudents()
	{
		for (int i = 0; i < studentGrades.size(); i++)
		{
			students.get(i).setGrade(studentGrades.get(i).getText().toString());
		}
	}
	
	private void addGradesToServer()
	{
		try {
			initSocketStreams();
			writeCommand("addGrades");
			writeCourseTutInfo();
			printwriter.writeObject(assignmentName.getText().toString());
			printwriter.writeObject(assignmentTotal.getText().toString());
			printwriter.writeObject(students);
			closeConnections();
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void onClick(View view) 
	{
		switch(view.getId())
		{
			case R.id.addGradeSubmitButton:
				if (!hasInputErrors())
				{
					addGradesToStudents();
					addGradesToServer();
					Toast.makeText(getApplicationContext(), "Submitted Grades", Toast.LENGTH_LONG).show();
				}
				break;
		}
		
	}

}

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

public class AddGroupGrade extends Activity implements View.OnClickListener
{

	private EditText assignmentName;
	private EditText assignmentTotal;
	private LinearLayout studentListLayout;
	private Button submitButton;
	
	private Course course;
	private Tutorial tut;
	
	private ArrayList<ArrayList<Student>> groups;
	private ArrayList<EditText> groupGrades;
	
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
		groups = getGroupsFromServer();
		groupGrades = new ArrayList<EditText>();
		for (int i = 0; i < groups.size(); i++)
		{
			LinearLayout l = new LinearLayout(this);
			l.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			l.setOrientation(LinearLayout.HORIZONTAL);
			
			TextView groupName = new TextView(this);
			String groupMembers = "";
			for (int j = 0; j < groups.get(i).size(); j++)
			{
				groupMembers += groups.get(i).get(j).getUtorid() + ",";
			}
			groupName.setText(groupMembers);
			l.addView(groupName);
			
			EditText groupGrade = new EditText(this);
			groupGrade.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			l.addView(groupGrade);
			groupGrades.add(groupGrade);
			
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
	
	private ArrayList<ArrayList<Student>> getGroupsFromServer()
	{
		try {
			initSocketStreams();
			writeCommand("getGroups");
			writeCourseTutInfo();
			@SuppressWarnings("unchecked")
			ArrayList<ArrayList<Student>> groups = (ArrayList<ArrayList<Student>>)in.readObject();
			closeConnections();
			return groups;
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
		
		for (int i = 0; i < groupGrades.size(); i++)
		{
			try
			{
				Integer.parseInt(groupGrades.get(i).getText().toString());
			}
			catch(NumberFormatException e)
			{
				Toast.makeText(getApplicationContext(), "A Group Grade Is Invalid", Toast.LENGTH_LONG).show();
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
	
	private void addGradesToGroups()
	{
		for (int i = 0; i < groupGrades.size(); i++)
		{
			for (int j = 0; j < groups.get(i).size(); j++)
			{
				groups.get(i).get(j).setGrade(groupGrades.get(i).getText().toString());
			}
			
		}
	}
	
	private ArrayList<Student> breakdownGroupsToStudents()
	{
		ArrayList<Student> students = new ArrayList<Student>();
		for (int i = 0; i < groups.size(); i++)
		{
			for (int j = 0; j < groups.get(i).size(); j++)
			{
				students.add(groups.get(i).get(j));
			}
		}
		return students;
	}
	private void addGradesToServer()
	{
		ArrayList<Student> students = breakdownGroupsToStudents();
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
					addGradesToGroups();
					addGradesToServer();
					Toast.makeText(getApplicationContext(), "Submitted Grades", Toast.LENGTH_LONG).show();
				}
				break;
		}
		
	}

}
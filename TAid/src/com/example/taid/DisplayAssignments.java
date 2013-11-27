package com.example.taid;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import UserInformation.Course;
import UserInformation.Globals;
import UserInformation.Tutorial;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayAssignments extends ListActivity
{
	private String[] classes;
	private String studentId;
	private Course course;
	private Tutorial tut;
	
	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream printwriter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
		init();
		setListAdapter(new ArrayAdapter<String>(DisplayAssignments.this, android.R.layout.simple_list_item_1, classes));
	}
	

	private void init() 
	{
		Intent i = getIntent();	
		studentId = i.getStringExtra("utorid");
		course = (Course)i.getSerializableExtra("course");
		tut = (Tutorial)i.getSerializableExtra("tutorial");
		ArrayList<String> assignmentNames = getAssignments();
		classes = new String[assignmentNames.size()];
		for (int j = 0; j < assignmentNames.size(); j++)
		{
			classes[j] = assignmentNames.get(j);
		}
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<String> getAssignments()
	{
		initSocketStreams();
		writeCommand("getAssignments");
		writeCourseTutInfo();
		try {
			ArrayList<String> assignmentNames = (ArrayList<String>)in.readObject();
			return assignmentNames;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
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
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{	
		if (getIntent().getIntExtra("showAll", 0) == 0)
		{
			super.onListItemClick(l, v, position, id);
			Intent intent = new Intent(this, IndividualGrade.class);
			intent.putExtra("course", course);
			intent.putExtra("tutorial", tut);
			intent.putExtra("utorid", studentId);
			intent.putExtra("assignmentName", classes[position]);
			intent.putExtra("assignmentTotal", getAssignmentTotal(classes[position]));
			intent.putExtra("studentGrade", getStudentGrade(classes[position]));
			startActivity(intent);
		}
	}
	
	private String getAssignmentTotal(String assignmentName)
	{
		initSocketStreams();
		writeCommand("getAssignmentTotal");
		writeCourseTutInfo();
		try {
			printwriter.writeObject(assignmentName);
			String assignmentTotal = (String)in.readObject();
			closeConnections();
			return assignmentTotal;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	private String getStudentGrade(String assignmentName)
	{
		initSocketStreams();
		writeCommand("getStudentGrade");
		writeCourseTutInfo();
		try {
			printwriter.writeObject(assignmentName);
			printwriter.writeObject(studentId);
			String grade = (String)in.readObject();
			closeConnections();
			return grade;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	
	
}

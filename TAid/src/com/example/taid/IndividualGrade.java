package com.example.taid;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import UserInformation.Course;
import UserInformation.Globals;
import UserInformation.Tutorial;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class IndividualGrade extends Activity implements View.OnClickListener
{
	private String studentId;
	private Course course;
	private Tutorial tut;
	private String assignmentName;
	private String assignmentTotal;
	private String studentGrade;
	
	private TextView assignmentTotalTv;
	private TextView studentIdTv;
	private TextView assignmentNameTv;
	private EditText studentGradeEdit;
	private Button individualGradeSubmitButton;
	
	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream printwriter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.individuagrade);
		init();
	}
	
	private void init()
	{
		studentGradeEdit = (EditText)findViewById(R.id.individualGradeStudentGrade);
		assignmentTotalTv = (TextView)findViewById(R.id.individualGradeAssignmentTotal);
		studentIdTv = (TextView)findViewById(R.id.individualGradeStudentId);
		assignmentNameTv = (TextView)findViewById(R.id.individualGradeAssignmentName);
		individualGradeSubmitButton = (Button)findViewById(R.id.individualGradeSubmitButton);
		individualGradeSubmitButton.setOnClickListener(this);
		
		Intent i = getIntent();
		studentId = i.getStringExtra("utorid");
		assignmentName = i.getStringExtra("assignmentName");
		assignmentTotal = i.getStringExtra("assignmentTotal");
		course = (Course)i.getSerializableExtra("course");
		tut = (Tutorial)i.getSerializableExtra("tutorial");
		studentGrade = i.getStringExtra("studentGrade");
		
		assignmentTotalTv.setText("/" + assignmentTotal);
		studentIdTv.setText(studentId);
		assignmentNameTv.setText(assignmentName);
		studentGradeEdit.setText(studentGrade);
		
		
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
	private void submitChangedGrade()
	{
		initSocketStreams();
		writeCommand("changeGrade");
		writeCourseTutInfo();
		try {
			printwriter.writeObject(assignmentName);
			printwriter.writeObject(studentId);
			printwriter.writeObject(studentGradeEdit.getText().toString());
		}
		catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		closeConnections();
	}
	@Override
	public void onClick(View view) 
	{
		// TODO Auto-generated method stub
		switch(view.getId())
		{
			case R.id.individualGradeSubmitButton:
				submitChangedGrade();
				break;
		}
		
	}
	
}

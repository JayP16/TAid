package com.example.taid;

import java.util.ArrayList;

import UserInformation.Course;
import UserInformation.Student;
import UserInformation.TeachingAssistant;
import UserInformation.Tutorial;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayStudents extends ListActivity {

	private String[] classes;
	private ArrayList<Student> students;
	
	private Course course;
	private Tutorial tut;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		init();
		setListAdapter(new ArrayAdapter<String>(DisplayStudents.this, android.R.layout.simple_list_item_1, classes));
	}

	private void init() 
	{
		Intent i = getIntent();
		course = (Course)i.getSerializableExtra("course");
		tut = (Tutorial)i.getSerializableExtra("tutorial");
		students = (ArrayList<Student>)i.getSerializableExtra("students");
		classes = new String[students.size()];
		for (int j = 0; j < students.size(); j++)
		{
			classes[j] = students.get(j).getUtorid();
		}		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{	
		
		if (getIntent().getIntExtra("showAll", 0) == 0)
		{
			super.onListItemClick(l, v, position, id);
			Intent intent = new Intent(this, DisplayAssignments.class);
			intent.putExtra("course", course);
			intent.putExtra("tutorial", tut);
			intent.putExtra("utorid", students.get(position).getUtorid());
			startActivity(intent);		
		}
	}



}

package com.example.taid;

import java.util.ArrayList;

import UserInformation.Student;
import UserInformation.TeachingAssistant;
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
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		init();
		setListAdapter(new ArrayAdapter<String>(DisplayStudents.this, android.R.layout.simple_list_item_1, classes));
	}

	private void init() 
	{
		Intent i = getIntent();
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
		/**
		if (getIntent().getIntExtra("showAll", 0) == 0)
		{
			super.onListItemClick(l, v, position, id);
			Intent intent = new Intent(this, IndividualGradeActivity.class);
			intent.putExtra("student", students.get(position));
			startActivity(intent);		
		}*/
	}



}

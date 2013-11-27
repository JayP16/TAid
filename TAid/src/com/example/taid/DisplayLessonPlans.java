package com.example.taid;

import java.util.ArrayList;

import UserInformation.Course;
import UserInformation.LessonPlan;
import UserInformation.TeachingAssistant;
import UserInformation.Tutorial;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayLessonPlans extends ListActivity
{
	private String[] classes;
	private ArrayList<LessonPlan> lessonPlans;
	private int displayOrEdit;
	private Course course;
	private Tutorial tutorial;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
		init();
		setListAdapter(new ArrayAdapter<String>(DisplayLessonPlans.this, android.R.layout.simple_list_item_1, classes));
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		finish(); //gets rid of the activity
	}
	
	private void init()
	{
		Intent i = getIntent();
		lessonPlans = (ArrayList<LessonPlan>)i.getSerializableExtra("lessonPlans");
		displayOrEdit = i.getIntExtra("displayOrEdit", 0);
		if (displayOrEdit == 1)
		{
			course = (Course)i.getSerializableExtra("course");
			tutorial = (Tutorial)i.getSerializableExtra("tutorial");
		}
		classes = new String[lessonPlans.size()];
		for (int j = 0; j < lessonPlans.size(); j++)
		{
			classes[j] = lessonPlans.get(j).getHeader();
		}
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id)

	{	
		if (getIntent().getIntExtra("showAll", 0) == 0){
			super.onListItemClick(l, v, position, id);	
			Intent intent;
			LessonPlan lessonPlan = lessonPlans.get(position);
			if (displayOrEdit == 0) // display lesson plan
				intent = new Intent(this, DisplayLessonPlan.class);
			else // edit lesson plan
			{
				intent = new Intent(this, CreateLessonPlan.class);  
				intent.putExtra("newOrEdit", 1);
				intent.putExtra("course", course);
				intent.putExtra("tutorial", tutorial);
			}
			intent.putExtra("lessonPlan", lessonPlan);
			startActivity(intent);	
		}
	}
}

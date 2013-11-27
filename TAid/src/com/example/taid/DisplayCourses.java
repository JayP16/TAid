package com.example.taid;

import UserInformation.TeachingAssistant;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class DisplayCourses extends ListActivity
{
	private String[] classes;
	private TeachingAssistant t;
	ListView listView;
    ArrayAdapter<String> adapter;
    Button saveButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
		init();
		setListAdapter(new ArrayAdapter<String>(DisplayCourses.this, android.R.layout.simple_list_item_1, classes));
	}
	

	private void init() 
	{
		Intent i = getIntent();
		t = (TeachingAssistant)i.getSerializableExtra("teachingAssistant");
		classes = new String[t.getCourseLoad()];
		for (int j = 0; j < t.getCourseLoad(); j++)
		{
			classes[j] = t.getCourses().get(j).getCourseCode();
		}		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)

	{	
		if (getIntent().getIntExtra("showAll", 0) == 0){
			super.onListItemClick(l, v, position, id);
			if (t.getCourses().get(position).getTutorialLoad() >= 1)
			{
				Intent intent = new Intent(this, DisplayTutorials.class);
			    intent.putExtra("teachingAssistant", t);
			    intent.putExtra("course", t.getCourses().get(position));
			    startActivity(intent);
			}			
		}
	}
}

package com.example.taid;

import UserInformation.Course;
import UserInformation.TeachingAssistant;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayTutorials extends ListActivity
{
	private String[] classes;
	private TeachingAssistant t;
	private Course c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		init();
		setListAdapter(new ArrayAdapter<String>(DisplayTutorials.this, android.R.layout.simple_list_item_1, classes));
		
	}
	
	private void init() 
	{
		Intent i = getIntent();
		t = (TeachingAssistant)i.getSerializableExtra("teachingAssistant");
		c = (Course)i.getSerializableExtra("course");
		
		classes = new String[c.getTutorialLoad()];
		for (int j = 0; j < c.getTutorialLoad(); j++)
		{
			classes[j] = c.getTutorials().get(j).getTutCode();
		}
	}
	
	@Override

	protected void onListItemClick(ListView l, View v, int position, long id)

	{
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, TutorialManager.class);
		intent.putExtra("teachingAssistant", t);
		intent.putExtra("course", c);
		intent.putExtra("tutorial", c.getTutorials().get(position));
	    startActivity(intent);

	}
	
}
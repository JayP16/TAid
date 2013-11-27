package com.example.taid;

import java.util.ArrayList;
import java.util.List;

import UserInformation.Course;
import UserInformation.Professor;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class DisplayCoursesProf extends ListActivity implements OnClickListener
{
	private String[] classes;
	private Professor t;
	ListView listView;
    ArrayAdapter<String> adapter;
    Button saveButton;
    private int PICKFILE_RESULT_CODE = 0;
    Button uploadCourseFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		t = (Professor)i.getSerializableExtra("Professor");
		if (getIntent().getIntExtra("showAll", 0) == 1)
		{
			setContentView(R.layout.show_all_courses);
			saveButton = (Button) findViewById(R.id.regCourses);
			showAll();
			listView = (ListView) findViewById(android.R.id.list);

			adapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_multiple_choice, classes);
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	        listView.setAdapter(adapter);
	        saveButton.setOnClickListener(this);	
		}
		else
		{
			init();
			setContentView(R.layout.show_registered_courses);
			setListAdapter(new ArrayAdapter<String>(DisplayCoursesProf.this, android.R.layout.simple_list_item_1, classes));
			uploadCourseFile = (Button) findViewById(R.id.uploadCourseFile);
			uploadCourseFile.setOnClickListener(this);
		}
	}
	
	private void showAll() {
		Course c = new Course();
		classes = c.getAllCourses(t);
		List<String> list = new ArrayList<String>();

	    for(String s : classes) {
	       if(s != null && s.length() > 0) {
	          list.add(s);
	       }
	    }

	    classes = list.toArray(new String[list.size()]);
	  
		
	}

	private void init() 
	{
		
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
			    intent.putExtra("Professor", t);
			    intent.putExtra("course", t.getCourses().get(position));
			    startActivity(intent);
			}			
		}
	}
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.regCourses:
				regCourses();
			    break;
			case R.id.uploadCourseFile:
				Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
		        fileintent.setType("gagt/sdf");
		        try {
					startActivityForResult(fileintent, PICKFILE_RESULT_CODE);
		        } catch (ActivityNotFoundException e) {
		            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
		        }
				break;
		}
	}
	
	public void regCourses()
	{
		SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }
		Professor p = new Professor();
		p.registerCourses(t, selectedItems);
		for (int i = 0; i < selectedItems.size(); i++)
		{
			Boolean has = t.getCourses().contains(new Course(selectedItems.get(i)));
			if (!has)
			{
				t.addEmptyCourse(selectedItems.get(i));
			}
		}
		Intent intent = new Intent(this, WelcomeProf.class);
	    intent.putExtra("Professor", t);
	    intent.putExtra("Result", 1);
	    startActivity(intent);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Fix no activity available
        if (data == null)
            return;
        if (requestCode == PICKFILE_RESULT_CODE) {
			if (resultCode == RESULT_OK) {
                String FilePath = data.getData().getPath();
                //FilePath is your file as a string
                System.out.println(FilePath);
            }
		}
	}
}

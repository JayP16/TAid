package com.example.taid;



import UserInformation.TeachingAssistant;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;
 

public class DisplayMessageActivity extends Activity{
	
	private TeachingAssistant t;
    
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // Get the message from the intent
	    Intent intent = getIntent();
	    t = (TeachingAssistant)intent.getSerializableExtra("teachingAssistant");
	    
	    // Create the text view
	    TextView textView = new TextView(this);
	    textView.setTextSize(40);
 
	    // Set the text view as the activity layout
	   // setContentView(textView);
	    init();
	}    

	private void init() 
	{
			// Create the text view
		    TextView textView = new TextView(this);
		    textView.setTextSize(40);
		    textView.setText("Welcome " + t.getUsername());
	 
		    // Set the text view as the activity layout
		   setContentView(textView);			
		}
		
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
}

package com.example.taid;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;
 
public class DisplayMessageActivity extends Activity{
	
    @SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // Get the message from the intent
	    Intent intent = getIntent();
	    String userId = intent.getStringExtra(FullscreenActivity.userId);
	    String password = intent.getStringExtra(FullscreenActivity.password);
	    
	    // Create the text view
	    TextView textView = new TextView(this);
	    textView.setTextSize(40);
	    textView.setText(password);
 
	    // Set the text view as the activity layout
	   // setContentView(textView);
	    init(userId, password);
	}    

	private void init(String username, String password) 
	{
			// Create the text view
		    TextView textView = new TextView(this);
		    textView.setTextSize(40);
		    textView.setText("Coming Soon!");
	 
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

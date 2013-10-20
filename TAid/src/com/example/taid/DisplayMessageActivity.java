package com.example.taid;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
 
public class DisplayMessageActivity extends Activity{
	
	private String username;
	private String password;
	
	private Button loginButton;
	private String usernameText;
	private String passwordText;
	private TextView loginResultsView;
	
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
		    textView.setText("checking..");
		    System.out.print("here");
	 
		    // Set the text view as the activity layout
		   setContentView(textView);
		   AsyncTask<String, Void, String> result = new CheckAccount().execute(username,password);
			
	
			
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

class CheckAccount extends AsyncTask<String, Void, String>{

    private Exception exception;
    private TextView loginResultsView;
    
    protected String doInBackground(String username, String password) {
    	TextView textView = new TextView(null);
    	textView.setText("done..");
		 System.out.print("here");
		return password;
    	
    }

    protected void onPostExecute() {
        // TODO: check this.exception 
        // TODO: do something with the feed
    }

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
}
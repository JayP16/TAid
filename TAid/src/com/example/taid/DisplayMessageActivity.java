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
			Socket clientSocket;
			// Create the text view
		    TextView textView = new TextView(this);
		    textView.setTextSize(40);
		    textView.setText("checking..");
	 
		    // Set the text view as the activity layout
		   setContentView(textView);
			try {
				clientSocket = new Socket("135.23.105.149", 6789);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter printwriter = new PrintWriter(clientSocket.getOutputStream(),true);
				printwriter.println(username);
				printwriter.println(password);
				String result = in.readLine();
				loginResultsView.setText(result);
				loginResultsView.setTextSize(40);
				setContentView(loginResultsView);
				clientSocket.close();
				in.close();
				printwriter.close();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textView.setText("done..");
			 
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
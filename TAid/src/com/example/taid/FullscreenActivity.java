package com.example.taid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import UserInformation.TeachingAssistant;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taid.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
	
    private EditText userName;
    private EditText passw;
    private Button btnSubmit;
	private Exception exception;
    private Button btnReg;
    private TeachingAssistant t;
 
	public final static String userId = "com.example.TAid.MESSAGE";
	public final static String password = "com.example.TAid.MESSAGE";
	
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        System.out.println("created activitayy");
        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                    }
                });

    
        findViewById(R.id.submit).setOnTouchListener(mDelayHideTouchListener);
        registerViews();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
       // delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

 private void registerViews() {
 	userName = (EditText) findViewById(R.id.user_id);
     // TextWatcher would let us check validation error on the fly
 	userName.addTextChangedListener(new TextWatcher() {
         public void afterTextChanged(Editable s) {
             Validation.hasText(userName);
         }
         public void beforeTextChanged(CharSequence s, int start, int count, int after){}
         public void onTextChanged(CharSequence s, int start, int before, int count){}
     });

     passw = (EditText) findViewById(R.id.password);
     passw.addTextChangedListener(new TextWatcher() {
         // after every change has been made to this editText, we would like to check validity
         public void afterTextChanged(Editable s) {
             Validation.hasText(passw);
         }
         public void beforeTextChanged(CharSequence s, int start, int count, int after){}
         public void onTextChanged(CharSequence s, int start, int before, int count){}
     });


     btnSubmit = (Button) findViewById(R.id.submit);
     btnSubmit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) 
         {
             /*
             Validation class will check the error and display the error on respective fields
             but it won't resist the form submission, so we need to check again before submit
              */
        	 
			if (checkValidation ())
            	 submitForm(view);
         }
     });
     btnReg = (Button) findViewById(R.id.register);
     btnReg.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {            	 
            	 Register(view);            	 
         }
     });
 }

 private void submitForm(View view) 
 {
	 String re = CheckAccount();
	 if (re.equals("1"))
	 {
		  // Submit form here. form is valid
			 		Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(this, Welcome.class);
				    intent.putExtra("teachingAssistant", t);
				    startActivity(intent);
	 }
	 else
	 {
		 Toast.makeText(this, "Invalid Login. Try Again.", Toast.LENGTH_SHORT).show();
	 }
   
 }

private boolean checkValidation()
{
	 Toast.makeText(this, "Verifying...", Toast.LENGTH_SHORT).show();
     boolean ret = true;
     if (!Validation.hasText(userName)) ret = false;
     if (!Validation.hasText(passw)) ret = false;
     return ret;
 }
 
 @SuppressLint("NewApi")
private String CheckAccount() 
 {
	 System.out.println("Check account method.");
	 String address = "135.23.105.149";
     if (android.os.Build.VERSION.SDK_INT > 9) 
     {
    	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	    StrictMode.setThreadPolicy(policy);
     }
     String result = "";
     EditText editText = (EditText) findViewById(R.id.user_id);
	 EditText pass = (EditText) findViewById(R.id.password);
	 String username = editText.getText().toString();
	 String passwords = pass.getText().toString();
     Socket clientSocket;
     
     try 
     {
     	clientSocket = new Socket(address, 50857);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter printwriter = new PrintWriter(clientSocket.getOutputStream(),true);
		printwriter.println("login");
		printwriter.println(username);
		printwriter.println(passwords);
		result = in.readLine();
		if (result.equals("1"))
		{
	        ObjectInputStream out = new ObjectInputStream(clientSocket.getInputStream());
	        t = (TeachingAssistant)out.readObject();
	        out.close();
	        if (t != null)
	        {
		    TextView textView = (TextView)findViewById(R.id.fullscreen_content);
		    textView.setText("working!!");
	        }
		}
		clientSocket.close();
		in.close();
		printwriter.close();
     } 
     catch (Exception e) {
         this.exception = e;
     }
	return result;
 }
 
 private void Register(View view) {
			Intent intent = new Intent(this, RegisterActivity.class);
		    startActivity(intent);   
 }
 
}

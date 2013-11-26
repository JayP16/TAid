package com.example.taid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class EmailActivity extends Activity implements View.OnClickListener
{

	private TextView subjectTv;
	private TextView emailTv;
	private EditText emailAddress;
	private EditText subjectLine;
	private EditText body;
	private Button sendButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emailactivity);
		init();
	}
	
	private void init()
	{
		subjectTv = (TextView)findViewById(R.id.emailSubjectTv);
		emailTv = (TextView)findViewById(R.id.emailTv);
		emailAddress = (EditText)findViewById(R.id.email);
		subjectLine = (EditText)findViewById(R.id.emailSubject);
		body = (EditText)findViewById(R.id.emailBody);
		sendButton = (Button)findViewById(R.id.emailSendButton);
		sendButton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) 
	{
		switch(view.getId())
		{
		case R.id.emailSendButton:
			String address = emailAddress.getText().toString();
			String subject = subjectLine.getText().toString();
			String message = body.getText().toString();
			
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, address);
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
			startActivity(emailIntent);
			break;
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}

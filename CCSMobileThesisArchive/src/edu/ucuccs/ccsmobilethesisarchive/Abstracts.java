package edu.ucuccs.ccsmobilethesisarchive;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Abstracts extends Activity {
	
	TextView title,researcher,adviser,year,abs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abstracts);
		title = (TextView)findViewById(R.id.title);
		researcher = (TextView)findViewById(R.id.researcher);
		adviser = (TextView)findViewById(R.id.adviser);
		year = (TextView)findViewById(R.id.year);
		abs = (TextView)findViewById(R.id.abs);
		
		title.setText(getIntent().getStringExtra("title"));
		researcher.setText(getIntent().getStringExtra("researcher"));
		adviser.setText(getIntent().getStringExtra("adviser"));
		year.setText(getIntent().getStringExtra("year"));
		abs.setText(getIntent().getStringExtra("abs"));
	}

	

}

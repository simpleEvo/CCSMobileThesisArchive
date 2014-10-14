package edu.ucuccs.ccsmobilethesisarchive;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class Abstracts extends Activity {
	
	TextView title,researcher,adviser,year,desc,abs,rating,rate;
	private RatingBar ratingBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abstracts);
		title = (TextView)findViewById(R.id.title);
		researcher = (TextView)findViewById(R.id.researcher);
		adviser = (TextView)findViewById(R.id.adviser);
		year = (TextView)findViewById(R.id.year);
		desc = (TextView)findViewById(R.id.desc);
		rating = (TextView)findViewById(R.id.rating);
		rate = (TextView)findViewById(R.id.rate);
		abs = (TextView)findViewById(R.id.abs);
		
		title.setText(getIntent().getStringExtra("title"));
		researcher.setText(getIntent().getStringExtra("researcher"));
		researcher.setTextColor(Color.GRAY);
		adviser.setText(getIntent().getStringExtra("adviser"));
		adviser.setTextColor(Color.GRAY);
		year.setText(getIntent().getStringExtra("year"));
		year.setTextColor(Color.GRAY);
		desc.setBackgroundColor(Color.GRAY);
		desc.setTextColor(Color.WHITE);
		rating.setBackgroundColor(Color.GRAY);
		rating.setTextColor(Color.WHITE);
		abs.setText(getIntent().getStringExtra("abs"));
	}
	
	public void addListenerOnRatingBar() {

		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		rate = (TextView) findViewById(R.id.rate);

		//if rating is changed,
		//display the current rating value in the result (textview) automatically
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {

				rate.setText(String.valueOf(rating));

			}
		});
	}

	

}

package edu.ucuccs.ccsmobilethesisarchive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ComRate extends Activity {
	final String APPLICATION_ID = "BAlnxVkeOecfO5yzZVpjokXpXqpJJpyhB8Y1ircf";
	final String CLIENT_KEY = "aCd3DFv5bgHscrNZ7vku9B6y5JW051XFxvFNK1rT";
	DBADapter db;
	ListView lstview;
	HashMap<String, String> map;
	List<HashMap<String, String>> mylist;
	ArrayList<GsRac> ratelist;
	String getId;
	TextView txtTitle;
	SimpleAdapter mRate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_com_rate);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
		db = new DBADapter(this);
		getId = getIntent().getStringExtra("id");
		mylist = new ArrayList<HashMap<String, String>>();
		lstview = (ListView) findViewById(R.id.lstview);
		txtTitle = (TextView) findViewById(R.id.txtTT);
		txtTitle.setText(getIntent().getStringExtra("title"));
		update();
		
		populateList();
		
		
	
	}
	
	public void populateList() {
		List<GsRac> list = db.findTID(getId.toString());
		if(list.size() == 0) {
		} else {
		for (GsRac c : list) {	

			map = new HashMap<String, String>();
			map.put("flname", c.getRac_fname() + " " + c.getRac_lname());
			map.put("rating", c.getRac_rate() + " STARS");
			map.put("comment", c.getRac_comment());
			mylist.add(map);
		}
		mRate = new SimpleAdapter(this, mylist,
				R.layout.row_flrc,
				new String[] { "flname", "rating", "comment" }, new int[] {
						R.id.FLname, R.id.ratelist, R.id.commentlist });
		lstview.setAdapter(mRate);
		mRate.notifyDataSetChanged();
		}
	}
	
	public void update() {
		ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>(
				"Rating");
		query1.addAscendingOrder("createdAt");
		query1.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> rate, ParseException arg1) {
			

				for (int c = 0; c < rate.size(); c++) {
					String reet = rate.get(c).getInt("rate") + "";
				

					db.addRate(new GsRac(rate.get(c).getString("Fname"),
							rate.get(c).getString("Lname"), reet, rate.get(
									c).getString("comment"), rate.get(c)
									.getString("user_id"), rate.get(c)
									.getString("thesis_id")));
				}

			}
		});
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	    	finish();
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		this.overridePendingTransition(R.anim.slide_down,
				R.anim.slide_up);
	}

}

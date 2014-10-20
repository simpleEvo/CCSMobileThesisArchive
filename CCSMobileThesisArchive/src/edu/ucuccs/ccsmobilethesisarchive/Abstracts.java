package edu.ucuccs.ccsmobilethesisarchive;

import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Abstracts extends Activity {
	final String APPLICATION_ID = "BAlnxVkeOecfO5yzZVpjokXpXqpJJpyhB8Y1ircf";
	final String CLIENT_KEY = "aCd3DFv5bgHscrNZ7vku9B6y5JW051XFxvFNK1rT";
	TextView title, researcher, adviser, year, desc, abs, rating, rate;
	RatingBar ratingBar, ratingBarCustom;
	Dialog customlogin, customsignup, customrate, customcurrent;
	EditText username, password, comment, uname, pword, fname, lname;
	TextView txt, FLname;
	Button login, signup, savebtn, canbtn, signupa, can, yes, no, rac;
	String getId, getUser, getRate, getTitle;
	ParseObject obj;
	float gg;
	CollectionFragment cf;

	DBADapter db;
	Boolean isInternetPresent;
	ConnectionDetector cd;

	
	ListView lstview;
	HashMap<String, String> map;
	List<HashMap<String, String>> mylist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abstracts);
		Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
	
		db = new DBADapter(this);

		
		title = (TextView) findViewById(R.id.title);
		researcher = (TextView) findViewById(R.id.researcher);
		adviser = (TextView) findViewById(R.id.adviser);
		year = (TextView) findViewById(R.id.year);
		desc = (TextView) findViewById(R.id.desc);
		rating = (TextView) findViewById(R.id.rating);
		rate = (TextView) findViewById(R.id.rate);
		rac = (Button) findViewById(R.id.rac);
		abs = (TextView) findViewById(R.id.abs);

		getId = getIntent().getStringExtra("id");
		getTitle = getIntent().getStringExtra("title");
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
		rac.setBackgroundColor(Color.GRAY);
		rac.setTextColor(Color.WHITE);
		abs.setText(getIntent().getStringExtra("abs"));
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		List<GsRac> list = db.findTID(getId.toString());
		float rit = 0;
		if(list.size() == 0) {
		} else {
		for (GsRac c : list) {	
			rit += Float.parseFloat(c.getRac_rate());
			
		}
		rit = rit/list.size();
		}
		ratingBar.setRating(rit);
		rate.setText(rit+"");
		cd = new ConnectionDetector(this);
		isInternetPresent = cd.isConnectingToInternet();
		rac.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Abstracts.this, ComRate.class);
				intent.putExtra("id", getId);
				intent.putExtra("title", getTitle);
				startActivity(intent);
			}
			
		});
		
		
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				gg = rating;
				if (isInternetPresent) {
					ParseUser currentUser = ParseUser.getCurrentUser();
					if (currentUser != null) {
						customcurrent = new Dialog(Abstracts.this);
						customcurrent.setContentView(R.layout.currentdialog);
						customcurrent.setTitle("Current User?");
						yes = (Button) customcurrent.findViewById(R.id.yes);
						no = (Button) customcurrent.findViewById(R.id.no);
						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								customrate = new Dialog(Abstracts.this);
								customrate.setContentView(R.layout.dialog);
								comment = (EditText) customrate
										.findViewById(R.id.comment);
								ratingBarCustom = (RatingBar) customrate
										.findViewById(R.id.ratingBarCustom);
								savebtn = (Button) customrate
										.findViewById(R.id.savebtn);
								canbtn = (Button) customrate.findViewById(R.id.canbtn);
								customrate.setTitle("Rate and Comments?");
								ratingBarCustom.setRating(gg);
								savebtn.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View view) {
										
										ParseUser currentUser = ParseUser
												.getCurrentUser();
										
										
										obj = new ParseObject("Rating");
										obj.put("user_id", currentUser.getObjectId());
										obj.put("Fname", currentUser.get("Fname"));
										obj.put("Lname", currentUser.get("Lname"));
										obj.put("user_id", currentUser.getObjectId());
										obj.put("thesis_id", getId.toString());
										obj.put("rate", gg);
										obj.put("comment", comment.getText().toString());
										obj.saveInBackground();
										Toast.makeText(getApplicationContext(),
												"Sucessful", Toast.LENGTH_SHORT).show();
										customrate.dismiss();
									}
								});
								canbtn.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View view) {
										// TODO Auto-generated method stub
										customrate.dismiss();
									}
								});
								customrate.show();
								customcurrent.dismiss();
							}
						});
						no.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								ParseUser.logOut();
								customcurrent.dismiss();
								customlogin = new Dialog(Abstracts.this);
								customlogin.setContentView(R.layout.logindialog);
								username = (EditText) customlogin.findViewById(R.id.username);
								password = (EditText) customlogin.findViewById(R.id.password);
								login = (Button) customlogin.findViewById(R.id.login);
								signup = (Button) customlogin.findViewById(R.id.signup);
								customlogin.setTitle("Login Please..");
								login.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View view) {
										// TODO Auto-generated method stub
										ParseUser.logInInBackground(username.getText()
												.toString(), password.getText().toString(),
												new LogInCallback() {

													@Override
													public void done(ParseUser user,
															ParseException arg1) {
														if (user != null) {
															customrate = new Dialog(
																	Abstracts.this);
															customrate
																	.setContentView(R.layout.dialog);
															comment = (EditText) customrate
																	.findViewById(R.id.comment);
															ratingBarCustom = (RatingBar) customrate
																	.findViewById(R.id.ratingBarCustom);
															savebtn = (Button) customrate
																	.findViewById(R.id.savebtn);
															canbtn = (Button) customrate
																	.findViewById(R.id.canbtn);
															customrate
																	.setTitle("Rate and Comments?");
															ratingBarCustom.setRating(gg);
															savebtn.setOnClickListener(new View.OnClickListener() {
																@Override
																public void onClick(View view) {
																	ParseUser currentUser = ParseUser
																			.getCurrentUser();
																	obj = new ParseObject(
																			"Rating");
																	obj.put("user_id",
																			currentUser
																					.getObjectId());
																	obj.put("Fname", currentUser.get("Fname"));
																	obj.put("Lname", currentUser.get("Lname"));
																	obj.put("thesis_id",
																			getId.toString());
																	obj.put("rate", gg);
																	obj.put("comment", comment
																			.getText()
																			.toString());
																	obj.saveInBackground();
																	Toast.makeText(
																			getApplicationContext(),
																			"Sucessful",
																			Toast.LENGTH_SHORT)
																			.show();

																	customrate.dismiss();
																}
															});
															canbtn.setOnClickListener(new View.OnClickListener() {
																@Override
																public void onClick(View view) {
																	// TODO Auto-generated
																	// method stub
																	customrate.dismiss();
																}
															});
															customrate.show();
														} else {
															Toast.makeText(
																	getApplicationContext(),
																	"No existing user",
																	Toast.LENGTH_LONG).show();
														}

													}

												});
										customlogin.dismiss();
									}
								});
								customlogin.show();
								signup.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View view) {
										customsignup = new Dialog(Abstracts.this);
										customsignup.setContentView(R.layout.signupdialog);
										uname = (EditText) customsignup
												.findViewById(R.id.uname);
										pword = (EditText) customsignup
												.findViewById(R.id.pword);
										fname = (EditText) customsignup
												.findViewById(R.id.fname);
										lname = (EditText) customsignup
												.findViewById(R.id.lname);
										signupa = (Button) customsignup
												.findViewById(R.id.signupa);
										can = (Button) customsignup.findViewById(R.id.can);
										customsignup.setTitle("Sign Up..");
										signupa.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View view) {
												ParseUser user = new ParseUser();
												String s_uname = uname.getText().toString();
												String s_pword = pword.getText().toString();
												// Force user to fill up the form
												if (s_uname.equals("") || s_pword.equals("")
														|| fname.equals("") || lname.equals("")) {
													Toast.makeText(getApplicationContext(),
															"Please complete the sign up form",
															Toast.LENGTH_LONG).show();

												} else {
													user.setUsername(s_uname);
													user.setPassword(s_pword);
													user.put("Fname", fname.getText()
															.toString());
													user.put("Lname", lname.getText()
															.toString());
													user.signUpInBackground(new SignUpCallback() {

														@Override
														public void done(ParseException e) {
															if (e == null) {
																Toast.makeText(
																		getApplicationContext(),
																		"Successful",
																		Toast.LENGTH_LONG)
																		.show();

																customlogin.show();
																customsignup.dismiss();
															} else {
																Toast.makeText(
																		getApplicationContext(),
																		"Error to sign-up!",
																		Toast.LENGTH_LONG)
																		.show();
															}
														}

													});

												}
											}

										});
										can.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View v) {
												customsignup.dismiss();
												customlogin.show();
											}
										});
										customsignup.show();
									}
								});
								

							}
						});
						customcurrent.show();
					} else {
						customlogin = new Dialog(Abstracts.this);
						customlogin.setContentView(R.layout.logindialog);
						username = (EditText) customlogin.findViewById(R.id.username);
						password = (EditText) customlogin.findViewById(R.id.password);
						login = (Button) customlogin.findViewById(R.id.login);
						signup = (Button) customlogin.findViewById(R.id.signup);
						customlogin.setTitle("Login Please..");
						login.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								// TODO Auto-generated method stub
								ParseUser.logInInBackground(username.getText()
										.toString(), password.getText().toString(),
										new LogInCallback() {

											@Override
											public void done(ParseUser user,
													ParseException arg1) {
												if (user != null) {
													customrate = new Dialog(
															Abstracts.this);
													customrate
															.setContentView(R.layout.dialog);
													comment = (EditText) customrate
															.findViewById(R.id.comment);
													ratingBarCustom = (RatingBar) customrate
															.findViewById(R.id.ratingBarCustom);
													savebtn = (Button) customrate
															.findViewById(R.id.savebtn);
													canbtn = (Button) customrate
															.findViewById(R.id.canbtn);
													customrate
															.setTitle("Rate and Comments?");
													ratingBarCustom.setRating(gg);
													savebtn.setOnClickListener(new View.OnClickListener() {
														@Override
														public void onClick(View view) {
															ParseUser currentUser = ParseUser
																	.getCurrentUser();
															obj = new ParseObject(
																	"Rating");
															obj.put("user_id",
																	currentUser
																			.getObjectId());
															obj.put("Fname", currentUser.get("Fname"));
															obj.put("Lname", currentUser.get("Lname"));
															obj.put("thesis_id",
																	getId.toString());
															obj.put("rate", gg);
															obj.put("comment", comment
																	.getText()
																	.toString());
															obj.saveInBackground();
															Toast.makeText(
																	getApplicationContext(),
																	"Sucessful",
																	Toast.LENGTH_SHORT)
																	.show();

															customrate.dismiss();
														}
													});
													canbtn.setOnClickListener(new View.OnClickListener() {
														@Override
														public void onClick(View view) {
															// TODO Auto-generated
															// method stub
															customrate.dismiss();
														}
													});
													customrate.show();
												} else {
													Toast.makeText(
															getApplicationContext(),
															"No existing user",
															Toast.LENGTH_LONG).show();
												}

											}

										});
								customlogin.dismiss();
							}
						});
						customlogin.show();
						signup.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								customsignup = new Dialog(Abstracts.this);
								customsignup.setContentView(R.layout.signupdialog);
								uname = (EditText) customsignup
										.findViewById(R.id.uname);
								pword = (EditText) customsignup
										.findViewById(R.id.pword);
								fname = (EditText) customsignup
										.findViewById(R.id.fname);
								lname = (EditText) customsignup
										.findViewById(R.id.lname);
								signupa = (Button) customsignup
										.findViewById(R.id.signupa);
								can = (Button) customsignup.findViewById(R.id.can);
								customsignup.setTitle("Sign Up..");
								signupa.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View view) {
										ParseUser user = new ParseUser();
										String s_uname = uname.getText().toString();
										String s_pword = pword.getText().toString();
										// Force user to fill up the form
										if (s_uname.equals("") || s_pword.equals("")
												|| fname.equals("") || lname.equals("")) {
											Toast.makeText(getApplicationContext(),
													"Please complete the sign up form",
													Toast.LENGTH_LONG).show();

										} else {
											user.setUsername(s_uname);
											user.setPassword(s_pword);
											user.put("Fname", fname.getText()
													.toString());
											user.put("Lname", lname.getText()
													.toString());
											user.signUpInBackground(new SignUpCallback() {

												@Override
												public void done(ParseException e) {
													if (e == null) {
														Toast.makeText(
																getApplicationContext(),
																"Successful",
																Toast.LENGTH_LONG)
																.show();

														customlogin.show();
														customsignup.dismiss();
													} else {
														Toast.makeText(
																getApplicationContext(),
																"Error to sign-up!",
																Toast.LENGTH_LONG)
																.show();
													}
												}

											});

										}
									}

								});
								can.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										customsignup.dismiss();
										customlogin.show();
									}
								});
								customsignup.show();
							}
						});

					}

				} else {
					customcurrent = new Dialog(Abstracts.this);
					customcurrent.setContentView(R.layout.currentdialog);
					customcurrent.setTitle("You want to connect in internet?");
					yes = (Button) customcurrent.findViewById(R.id.yes);
					no = (Button) customcurrent.findViewById(R.id.no);
					yes.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							final Intent intent = new Intent(Intent.ACTION_MAIN, null);
							intent.addCategory(Intent.CATEGORY_LAUNCHER);
							final ComponentName cn = new ComponentName("com.android.settings",
									"com.android.settings.wifi.WifiSettings");
							intent.setComponent(cn);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						}
						
					});
					no.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							customcurrent.dismiss();
						}
						
					});
					
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

package edu.ucuccs.ccsmobilethesisarchive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CollectionFragment extends Fragment {
	public CollectionFragment() {
	}

	final String APPLICATION_ID = "BAlnxVkeOecfO5yzZVpjokXpXqpJJpyhB8Y1ircf";
	final String CLIENT_KEY = "aCd3DFv5bgHscrNZ7vku9B6y5JW051XFxvFNK1rT";
	ListView lstview;
	ArrayList<GsThesis> thesislist;
	ThesisAdapter adapter;
	String x, thesis_id;

	String[] from = new String[] { "title", "researcher" };
	int[] to = new int[] { R.id.title, R.id.researcher };
	int rated = 0;
	double frated = 0;
	DBADapter db;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_collection,
				container, false);
		setHasOptionsMenu(true);

		db = new DBADapter(getActivity());
		thesislist = new ArrayList<GsThesis>();
		Parse.initialize(getActivity(), APPLICATION_ID, CLIENT_KEY);
		lstview = (ListView) rootView.findViewById(R.id.listView1);
		adapter = new ThesisAdapter(getActivity(), R.layout.row_theses,
				thesislist);
		thesislist.clear();
		lstview.setAdapter(adapter);
		ConnectionDetector cd = new ConnectionDetector(getActivity());
		Boolean isInternetPresent = cd.isConnectingToInternet();
		populatelist();
		// check for Internet status
		if (isInternetPresent) {
			updateThesis();
			Toast.makeText(getActivity(), "Thesis Updated!", Toast.LENGTH_SHORT)
					.show();
		}

		lstview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String tid = thesislist.get(position).getId();
				String title = thesislist.get(position).getTitle();
				String researcher = thesislist.get(position).getResearcher();
				String adviser = thesislist.get(position).getAdviser();
				String year = thesislist.get(position).getYear();
				String abs = thesislist.get(position).getAbs();

				Intent intent = new Intent(getActivity(), Abstracts.class);
				intent.putExtra("id", tid);
				intent.putExtra("title", title);
				intent.putExtra("researcher", researcher);
				intent.putExtra("adviser", adviser);
				intent.putExtra("year", year);
				intent.putExtra("abs", abs);
				startActivity(intent);

				getActivity().overridePendingTransition(R.anim.slide_down,
						R.anim.slide_up);
			}

		});

		return rootView;
	}

	public void populatelist() {
		List<GsThesis> list = db.getThesisList();
		for (int i = 0; i < list.size(); i++) {
			GsThesis thesis = new GsThesis();
			thesis.setTitle(list.get(i).title.toString());
			thesis.setResearcher(list.get(i).researcher.toString());
			thesis.setAdviser(list.get(i).adviser.toString());
			thesis.setYear(list.get(i).year.toString());
			thesis.setAbs(list.get(i).abs.toString());
			thesislist.add(thesis);

			Collections.sort(thesislist, new Comparator<GsThesis>() {
				@Override
				public int compare(GsThesis lhs, GsThesis rhs) {
					return lhs.getTitle().compareTo(rhs.getTitle());
				}

			});
		}
		adapter.notifyDataSetChanged();
	}

	public void updateThesis() {
		db.DropTable();
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Thesis");
		query.addAscendingOrder("title");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> listahan,
					com.parse.ParseException arg1) {
				for (int i = 0; i < listahan.size(); i++) {
					Date today = listahan.get(i).getDate("year");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
					String year = formatter.format(today);
					db.addThesis(new GsThesis(listahan.get(i).getObjectId(),
							listahan.get(i).getString("title"), listahan.get(i)
									.getString("researcher"), listahan.get(i)
									.getString("adviser"), year, listahan
									.get(i).getString("abstract")));

					x = listahan.get(i).getObjectId();
					
					List<GsRate> listThesisId = db.findThesisId(x);
					Toast.makeText(getActivity(), listThesisId.size() + "", Toast.LENGTH_SHORT)
					.show();
					if(listThesisId.size() == 0) {
						updateRate(x);
					} else {
						
					}
				}

			}

		});

	}

	public void updateRate(final String x) {
		
		ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("Rating");
		query1.whereNotEqualTo("thesis_id", x);
		query1.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> rate, ParseException arg1) {
				frated = 0;
				rated = 0;
				for (int c = 0; c < rate.size(); c++) {
					rated = rated + rate.get(c).getInt("rate");
				}
				frated = rated / rate.size();
				Toast.makeText(getActivity(), frated + "", Toast.LENGTH_LONG)
						.show();
			
					db.addRate(new GsRate(rate.get(0).getString("thesis_id"),
							frated + ""));
			
			
				
			}
		});
		
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search_view, menu);
		final SearchView searchView;
		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getActivity()
				.getSystemService(Context.SEARCH_SERVICE);
		searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getActivity().getComponentName()));
		searchView.setQueryHint("Search by Title/Researcher");
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String query) {
				thesislist.clear();
				List<GsThesis> listTitle = db.findTitle(query);

				if (listTitle.size() == 0) {
					Toast.makeText(getActivity(),
							"You enter a word doesnt exist!", Toast.LENGTH_SHORT)
							.show();
					populatelist();
				} else if (listTitle.size() > 0) {
					for (int i = 0; i < listTitle.size(); i++) {
						GsThesis thesis = new GsThesis();
						thesis.setId(listTitle.get(i).id.toString());
						thesis.setTitle(listTitle.get(i).title.toString());
						thesis.setResearcher(listTitle.get(i).researcher
								.toString());
						thesis.setAdviser(listTitle.get(i).adviser.toString());
						thesis.setYear(listTitle.get(i).year.toString());
						thesis.setAbs(listTitle.get(i).abs.toString());
						thesislist.add(thesis);

						Collections.sort(thesislist,
								new Comparator<GsThesis>() {
									@Override
									public int compare(GsThesis lhs,
											GsThesis rhs) {
										return lhs.getTitle().compareTo(
												rhs.getTitle());
									}

								});
					}

				}  else if (query == null || query == "") {
					Toast.makeText(getActivity(),
							"Please Enter any keyword(s)!", Toast.LENGTH_LONG)
							.show();
					populatelist();
				}
				adapter.notifyDataSetChanged();

				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {

				return false;

			}

		});

	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_search:
			// search action
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		getActivity().overridePendingTransition(R.anim.slide_down,
				R.anim.slide_up);
	}
}

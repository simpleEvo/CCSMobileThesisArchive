package edu.ucuccs.ccsmobilethesisarchive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CollectionFragment extends Fragment {
	public CollectionFragment() {
	}
	final String APPLICATION_ID = "BAlnxVkeOecfO5yzZVpjokXpXqpJJpyhB8Y1ircf";
	final String CLIENT_KEY = "aCd3DFv5bgHscrNZ7vku9B6y5JW051XFxvFNK1rT";
	ListView lstview;
	ArrayList<GsThesis> thesislist;
	SimpleAdapter adapter;
	 
	String[] from = new String[] { "title", "researcher" };
	int[] to = new int[] { R.id.title, R.id.researcher };
	
	
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

		lstview.setAdapter(adapter);
		
		populatelist();
		ConnectionDetector cd = new ConnectionDetector(getActivity());
		Boolean isInternetPresent = cd.isConnectingToInternet();
		 
        // check for Internet status
        if (isInternetPresent) {
        	updateThesis();
            Toast.makeText(getActivity(), "Thesis Updated!", Toast.LENGTH_SHORT).show();
        } 	
	   
		
		lstview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String title = thesislist.get(position).getTitle();
				String researcher = thesislist.get(position).getResearcher();
				String adviser = thesislist.get(position).getAdviser();
				String year = thesislist.get(position).getYear();
				String abs = thesislist.get(position).getAbs();

				Intent intent = new Intent(getActivity(), Abstracts.class);
				intent.putExtra("title", title);
				intent.putExtra("researcher", researcher);
				intent.putExtra("adviser", adviser);
				intent.putExtra("year", year);
				intent.putExtra("abs", abs);
				startActivity(intent);

				getActivity().overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
			}

		});
		
		return rootView;

	}
	public void populatelist() {
		List<GsThesis> list = db.getThesisList();
		List<HashMap<String, String>> listahan = new ArrayList<HashMap<String, String>>();
		for (GsThesis f : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("title", f.getTitle());
			map.put("researcher", f.getResearcher());
			listahan.add(map);
		}

		adapter = new SimpleAdapter(getActivity(), listahan, R.layout.row_theses, from, to);
		lstview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	public void updateThesis(){
		db.DropTable();
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Thesis");
		query.findInBackground(new FindCallback<ParseObject>(){
			
			@Override
			public void done(List<ParseObject> listahan,
					com.parse.ParseException arg1) {
				for(int i =0; i< listahan.size(); i++){
					 Date today = listahan.get(i).getDate("year");
					 SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
					 String year = formatter.format(today);
					
					db.addThesis(new GsThesis(listahan.get(i).getString("title"), listahan.
							get(i).getString("researcher"), listahan.
							get(i).getString("adviser"), year,
							listahan.get(i).getString("abstract")));
				}
				
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
				String ss = searchView.getQuery().toString();
				
			    	
		    	  
		    		   Toast.makeText(getActivity(), ss+"", Toast.LENGTH_SHORT).show();
		                 
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				Toast.makeText(getActivity(), "hellow", Toast.LENGTH_SHORT).show();
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
		getActivity().overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
	}
}

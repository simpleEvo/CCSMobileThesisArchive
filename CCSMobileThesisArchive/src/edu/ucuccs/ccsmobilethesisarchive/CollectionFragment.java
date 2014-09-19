package edu.ucuccs.ccsmobilethesisarchive;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
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

public class CollectionFragment extends Fragment {
	public CollectionFragment() { }
	ListView lstview;
	ArrayList<GsThesis> thesislist;
	ThesisAdapter adapter;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_collection, container, false);
		setHasOptionsMenu(true);
		
		thesislist = new ArrayList<GsThesis>();
		new JSONAsyncTask().execute("http://ucuccsthesis.site11.com/ucuccsthesis.json");
		
		lstview = (ListView) rootView.findViewById(R.id.listView1);
		adapter = new ThesisAdapter(getActivity(), R.layout.row_theses, thesislist);
		lstview.setAdapter(adapter);
		lstview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
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
			}
			
		});
	
		return rootView;
	
	}
	
	class JSONAsyncTask extends AsyncTask<String, Void, String> {

		ProgressDialog dialog;

		@Override
		protected String doInBackground(String... urls) {

			try {
				HttpGet httppost = new HttpGet(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);

				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity, "UTF-8");

				JSONObject collectionObject = new JSONObject(data);
				JSONArray thesisarray = collectionObject.getJSONArray("thesis");
				
				for (int i = 0; i < thesisarray.length(); i++) {
					JSONObject object = thesisarray.getJSONObject(i);

					GsThesis thesis = new GsThesis();

					thesis.setTitle(object.getString("title"));
					thesis.setResearcher(object.getString("researcher"));
					thesis.setAdviser(object.getString("adviser"));
					thesis.setYear(object.getString("year"));
					thesis.setAbs(object.getString("abstracts"));
					

					thesislist.add(thesis);

				}

			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setTitle("Loading");
			dialog.setMessage("Please wait...");
			dialog.show();
			dialog.setCancelable(false);
		}

		protected void onPostExecute(String result) {
			dialog.cancel();
			adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search_view, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
		searchView.setQueryHint("Search by Title/Researcher");

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
}

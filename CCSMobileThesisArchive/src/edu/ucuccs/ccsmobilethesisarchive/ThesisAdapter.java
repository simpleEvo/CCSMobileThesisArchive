package edu.ucuccs.ccsmobilethesisarchive;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ThesisAdapter extends ArrayAdapter<GsThesis> {

	ArrayList<GsThesis> booklist;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;

	public ThesisAdapter(Context context, int resource, ArrayList<GsThesis> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		booklist = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder.title = (TextView) v.findViewById(R.id.title);
			holder.reseacher = (TextView) v.findViewById(R.id.reseacher);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		holder.title.setText(booklist.get(position).getTitle());
		holder.reseacher.setText(booklist.get(position).getResearcher());

		return v;

	}

	static class ViewHolder {
		public TextView title;
		public TextView reseacher;

	}
}

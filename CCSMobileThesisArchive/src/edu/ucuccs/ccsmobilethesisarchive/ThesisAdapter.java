package edu.ucuccs.ccsmobilethesisarchive;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ThesisAdapter extends ArrayAdapter<GsThesis> {

	ArrayList<GsThesis> thesislist;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;

	public ThesisAdapter(Context context, int resource, ArrayList<GsThesis> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		thesislist = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder.title = (TextView) v.findViewById(R.id.title);
			holder.reseacher = (TextView) v.findViewById(R.id.researcher);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		holder.title.setText(thesislist.get(position).getTitle());
		holder.reseacher.setText(thesislist.get(position).getAbs());

		return v;

	}

	static class ViewHolder {
		public TextView title;
		public TextView reseacher;

	}
}

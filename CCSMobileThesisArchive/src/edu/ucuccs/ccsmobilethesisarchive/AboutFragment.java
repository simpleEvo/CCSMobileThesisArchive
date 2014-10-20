package edu.ucuccs.ccsmobilethesisarchive;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {
	TextView about,contact;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_about, container,
				false);
		contact = (TextView) rootView.findViewById(R.id.txtC);
		contact.setText("One San Vicente West Urdaneta City 2428" + '\n' + "Tel.No. (075) 568-7612 / (075) 568-2475" + '\n' + "Email: info@ucu.edu.ph");
		about = (TextView) rootView.findViewById(R.id.txtA);
		about.setText("Hi, students if you want to see the full manuscript just visit urdaneta city university a university where character is utmost. this is the address of the university San vicente West Urdaneta city Beside of Urdaneta NAtional HighSchool. this is rules and Requirements upon Entering the Urdaneta City Uniuversity:" + '\n' + '\n' + "After entering the gate you must ask the guard  a visiting pass so that you will have a permission on entering the university. second you must have a letter from your school for the visiting of the library of the university. if you complete all the requirements then your free  to go in our university to see all the study of our fellow students");
		
		
		return rootView;
		
		
	}
}

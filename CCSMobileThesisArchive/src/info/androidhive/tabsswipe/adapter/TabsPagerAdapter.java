package info.androidhive.tabsswipe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import edu.ucuccs.ccsmobilethesisarchive.AboutFragment;
import edu.ucuccs.ccsmobilethesisarchive.CollectionFragment;
import edu.ucuccs.ccsmobilethesisarchive.FAQSFragment;
import edu.ucuccs.ccsmobilethesisarchive.HomeFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new HomeFragment();
		case 1:
			return new CollectionFragment();
		case 2:
			return new AboutFragment();
		case 3:
			return new FAQSFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}

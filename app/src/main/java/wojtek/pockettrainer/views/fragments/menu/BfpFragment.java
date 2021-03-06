package wojtek.pockettrainer.views.fragments.menu;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.views.fragments.BfpCalculatorFragment;
import wojtek.pockettrainer.views.fragments.BfpResultFragment;
import wojtek.pockettrainer.views.fragments.BmiCalculatorFragment;
import wojtek.pockettrainer.views.fragments.BmiResultFragment;

/**
 * @author Wojtek Kolendo
 * @date 10.09.2016
 */
public class BfpFragment extends Fragment {

	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapterViewPager;
	private TabLayout mTabLayout;

	private static final int NUM_ITEMS = 2;

	public static BfpFragment newInstance() {
		return new BfpFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {;
		View view =  inflater.inflate(R.layout.fragment_bfp, container, false);
		mViewPager = (ViewPager) view.findViewById(R.id.bfp_view_pager);
		mAdapterViewPager = new BfpAdapter(getChildFragmentManager());
		mViewPager.setAdapter(mAdapterViewPager);
		mTabLayout = (TabLayout) view.findViewById(R.id.bfp_tabs);

		mTabLayout.setupWithViewPager(mViewPager);
		return view;
	}

	public void switchFragment(int page, double result) {
		BfpResultFragment fragment = (BfpResultFragment) mAdapterViewPager.getItem(1);
		fragment.setResult(result);
		mViewPager.setCurrentItem(page, true);
	}

	public static class BfpAdapter extends FragmentPagerAdapter {

		private BfpResultFragment mRangesFragment;

		public BfpAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
			mRangesFragment = mRangesFragment.newInstance();
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case 0:
					return BfpCalculatorFragment.newInstance();
				case 1:
					return mRangesFragment;
				default:
					return null;
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "Calculate";
				case 1:
					return "Result";
			}
			return null;
		}
	}
}
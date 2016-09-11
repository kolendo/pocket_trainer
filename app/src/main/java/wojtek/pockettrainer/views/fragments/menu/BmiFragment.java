package wojtek.pockettrainer.views.fragments.menu;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humandevice.android.v4.mvpframework.PresenterFragment;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.presenters.BmiPresenter;
import wojtek.pockettrainer.presenters.impl.BmiPresenterImpl;
import wojtek.pockettrainer.views.BmiView;
import wojtek.pockettrainer.views.fragments.BmiCalculatorFragment;
import wojtek.pockettrainer.views.fragments.BmiLogFragment;
import wojtek.pockettrainer.views.fragments.BmiRangesFragment;

/**
 * @author Wojtek Kolendo
 * @date 10.09.2016
 */
public class BmiFragment extends PresenterFragment<BmiView, BmiPresenter> implements BmiView {

	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapterViewPager;
	private TabLayout mTabLayout;

	private static final int NUM_ITEMS = 2;

	public static class BmiAdapter extends FragmentPagerAdapter {

		private BmiRangesFragment mRangesFragment;

		public BmiAdapter(FragmentManager fragmentManager) {
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
					return BmiCalculatorFragment.newInstance();
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

	public static BmiFragment newInstance() {
		return new BmiFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bmi, container, false);
	}

	@Override
	public Class<? extends BmiPresenter> getPresenterClass() {
		return BmiPresenterImpl.class;
	}

	@Override
	protected void initView(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.bmi_view_pager);
		mAdapterViewPager = new BmiAdapter(getChildFragmentManager());
		mViewPager.setAdapter(mAdapterViewPager);
		mTabLayout = (TabLayout) view.findViewById(R.id.tabs);

		mTabLayout.setupWithViewPager(mViewPager);
	}

	public void switchFragment(int page, double result) {
		BmiRangesFragment fragment = (BmiRangesFragment) mAdapterViewPager.getItem(1);
		fragment.setResult(result);
		mViewPager.setCurrentItem(page, true);
	}
}
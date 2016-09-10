package wojtek.pockettrainer.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humandevice.android.v4.mvpframework.PresenterFragment;

import wojtek.pockettrainer.views.BmiRangesView;
import wojtek.pockettrainer.presenters.BmiRangesPresenter;
import wojtek.pockettrainer.presenters.impl.BmiRangesPresenterImpl;
import wojtek.pockettrainer.R;

public class BmiRangesFragment extends PresenterFragment<BmiRangesView, BmiRangesPresenter> implements BmiRangesView {

	public static BmiRangesFragment newInstance() {
		return new BmiRangesFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bmi_ranges, container, false);
	}

	@Override
	public Class<? extends BmiRangesPresenter> getPresenterClass() {
		return BmiRangesPresenterImpl.class;
	}

	@Override
	protected void initView(View view) {

	}
}
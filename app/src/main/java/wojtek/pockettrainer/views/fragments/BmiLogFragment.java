package wojtek.pockettrainer.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humandevice.android.v4.mvpframework.PresenterFragment;

import wojtek.pockettrainer.views.BmiLogView;
import wojtek.pockettrainer.presenters.BmiLogPresenter;
import wojtek.pockettrainer.presenters.impl.BmiLogPresenterImpl;
import wojtek.pockettrainer.R;

public class BmiLogFragment extends PresenterFragment<BmiLogView, BmiLogPresenter> implements BmiLogView {

	public static BmiLogFragment newInstance() {
		return new BmiLogFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bmi_log, container, false);
	}

	@Override
	public Class<? extends BmiLogPresenter> getPresenterClass() {
		return BmiLogPresenterImpl.class;
	}

	@Override
	protected void initView(View view) {

	}
}
package wojtek.pockettrainer.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humandevice.android.v4.mvpframework.PresenterFragment;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.presenters.BmiCalculatorPresenter;
import wojtek.pockettrainer.presenters.impl.BmiCalculatorPresenterImpl;
import wojtek.pockettrainer.views.BmiCalculatorView;


/**
 * @author Wojtek Kolendo
 * @date 04.09.2016
 */
public class BmiCalculatorFragment extends PresenterFragment<BmiCalculatorView, BmiCalculatorPresenter> implements BmiCalculatorView {

	public static BmiCalculatorFragment newInstance() {
		return new BmiCalculatorFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bmi_calculator, container, false);
	}

	@Override
	public Class<? extends BmiCalculatorPresenter> getPresenterClass() {
		return BmiCalculatorPresenterImpl.class;
	}

	@Override
	protected void initView(View view) {

	}
}
package wojtek.pockettrainer.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humandevice.android.v4.mvpframework.PresenterFragment;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.presenters.BfpCalculatorPresenter;
import wojtek.pockettrainer.presenters.impl.BfpCalculatorPresenterImpl;
import wojtek.pockettrainer.views.BfpCalculatorView;


/**
 * TODO Dokumentacja
 *
 * @author Wojtek Kolendo
 * @date 04.09.2016
 */
public class BfpCalculatorFragment extends PresenterFragment<BfpCalculatorView, BfpCalculatorPresenter> implements BfpCalculatorView {

	public static BfpCalculatorFragment newInstance() {
		return new BfpCalculatorFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bfp_calculator, container, false);
	}

	@Override
	public Class<? extends BfpCalculatorPresenter> getPresenterClass() {
		return BfpCalculatorPresenterImpl.class;
	}

	@Override
	protected void initView(View view) {

	}
}
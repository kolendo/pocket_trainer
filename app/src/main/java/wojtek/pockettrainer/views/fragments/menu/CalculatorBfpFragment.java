package wojtek.pockettrainer.views.fragments.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humandevice.android.v4.mvpframework.PresenterFragment;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.presenters.CalculatorBfpPresenter;
import wojtek.pockettrainer.presenters.impl.CalculatorBfpPresenterImpl;
import wojtek.pockettrainer.views.CalculatorBfpView;


/**
 * TODO Dokumentacja
 *
 * @author Wojtek Kolendo
 * @date 04.09.2016
 */
public class CalculatorBfpFragment extends PresenterFragment<CalculatorBfpView, CalculatorBfpPresenter> implements CalculatorBfpView {

	public static CalculatorBfpFragment newInstance() {
		return new CalculatorBfpFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_calculator_bfp, container, false);
	}

	@Override
	public Class<? extends CalculatorBfpPresenter> getPresenterClass() {
		return CalculatorBfpPresenterImpl.class;
	}

	@Override
	protected void initView(View view) {

	}
}
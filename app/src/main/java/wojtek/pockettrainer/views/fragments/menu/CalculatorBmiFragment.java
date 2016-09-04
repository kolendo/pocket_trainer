package wojtek.pockettrainer.views.fragments.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humandevice.android.v4.mvpframework.PresenterFragment;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.presenters.CalculatorBmiPresenter;
import wojtek.pockettrainer.presenters.impl.CalculatorBmiPresenterImpl;
import wojtek.pockettrainer.views.CalculatorBmiView;


/**
 * TODO Dokumentacja
 *
 * @author Wojtek Kolendo
 * @date 04.09.2016
 */
public class CalculatorBmiFragment extends PresenterFragment<CalculatorBmiView, CalculatorBmiPresenter> implements CalculatorBmiView {

	public static CalculatorBmiFragment newInstance() {
		return new CalculatorBmiFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_calculator_bmi, container, false);
	}

	@Override
	public Class<? extends CalculatorBmiPresenter> getPresenterClass() {
		return CalculatorBmiPresenterImpl.class;
	}

	@Override
	protected void initView(View view) {

	}
}
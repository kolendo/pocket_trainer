package wojtek.pockettrainer.presenters.impl;

import com.humandevice.android.mvpframework.BasicPresenter;

import wojtek.pockettrainer.presenters.BmiCalculatorPresenter;
import wojtek.pockettrainer.views.BmiCalculatorView;


/**
 * @author Wojtek Kolendo
 * @date 04.09.2016
 */
public class BmiCalculatorPresenterImpl extends BasicPresenter<BmiCalculatorView> implements BmiCalculatorPresenter {

	@Override
	public void calculateBmi(boolean metric, boolean male, String weight, String height) {
		if (mView != null) {
			double result;
			if (metric) {
				result = metricBmi(Double.parseDouble(weight), (Double.parseDouble(height)/100));
			} else {
				result = imperialBmi(Double.parseDouble(weight), (Double.parseDouble(height))*12);
			}
			mView.setResult(result);
		}
	}

	private double metricBmi(double weight, double height) {
		return (weight/(height*height));
	}

	private double imperialBmi(double weight, double height) {
		return ((weight*703)/(height*height));
	}

	@Override
	public double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}

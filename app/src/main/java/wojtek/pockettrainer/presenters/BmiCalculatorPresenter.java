package wojtek.pockettrainer.presenters;

import com.humandevice.android.mvpframework.Presenter;

import wojtek.pockettrainer.views.BmiCalculatorView;

/**
 * TODO Dokumentacja
 *
 * @author Wojtek Kolendo
 * @date 04.09.2016
 */
public interface BmiCalculatorPresenter extends Presenter<BmiCalculatorView> {

	void calculateBmi(boolean metric, boolean male, String weight, String height);

	double round(double value, int places);

}
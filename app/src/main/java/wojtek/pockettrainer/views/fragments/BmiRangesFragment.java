package wojtek.pockettrainer.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.humandevice.android.v4.mvpframework.PresenterFragment;

import wojtek.pockettrainer.views.BmiRangesView;
import wojtek.pockettrainer.presenters.BmiRangesPresenter;
import wojtek.pockettrainer.presenters.impl.BmiRangesPresenterImpl;
import wojtek.pockettrainer.R;
import wojtek.pockettrainer.views.fragments.menu.BmiFragment;

/**
 * @author Wojtek Kolendo
 * @date 10.09.2016
 */
public class BmiRangesFragment extends PresenterFragment<BmiRangesView, BmiRangesPresenter> implements BmiRangesView {

	private TextView mResultTextView;
	private double mResult;


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
		mResultTextView = (TextView) view.findViewById(R.id.bmi_result);
	}

	public void setResult(double result) {
		mResultTextView.setText(String.valueOf(result));
		mResult = result;
	}
}
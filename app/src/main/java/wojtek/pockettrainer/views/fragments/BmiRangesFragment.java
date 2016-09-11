package wojtek.pockettrainer.views.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
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

	private CardView mResultCardView;
	private TextView mResultTextView;
	private TextView mResultTitleTextView, mResultInfoTextView;
	private TextView mSeverelyNumTextView, mUnderweightNumTextView, mNormalNumTextView, mOverweightNumTextView, mObesityNumTextView;
	private TextView mSeverelyTextView, mUnderweightTextView, mNormalTextView, mOverweightTextView, mObesityTextView;


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
		mResultCardView = (CardView) view.findViewById(R.id.bmi_result_info_card);
		mResultTextView = (TextView) view.findViewById(R.id.bmi_result);
		mResultTitleTextView = (TextView) view.findViewById(R.id.bmi_result_title);
		mResultInfoTextView = (TextView) view.findViewById(R.id.bmi_result_info);

		mSeverelyNumTextView = (TextView) view.findViewById(R.id.bmi_severely_underweight_num);
		mSeverelyTextView = (TextView) view.findViewById(R.id.bmi_severely_underweight);

		mUnderweightNumTextView = (TextView) view.findViewById(R.id.bmi_severely_underweight_num);
		mUnderweightTextView = (TextView) view.findViewById(R.id.bmi_severely_underweight);

		mNormalNumTextView = (TextView) view.findViewById(R.id.bmi_normal_num);
		mNormalTextView = (TextView) view.findViewById(R.id.bmi_normal);

		mOverweightNumTextView = (TextView) view.findViewById(R.id.bmi_overweight_num);
		mOverweightTextView = (TextView) view.findViewById(R.id.bmi_overweight);

		mObesityNumTextView = (TextView) view.findViewById(R.id.bmi_obesity_num);
		mObesityTextView = (TextView) view.findViewById(R.id.bmi_obesity);
	}

	public void setResult(double result) {
		mResultTextView.setText(String.valueOf(result));
		mResultCardView.setVisibility(View.VISIBLE);

		if (result < 17) {
			mSeverelyNumTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mSeverelyTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mResultTitleTextView.setText(R.string.bmi_result_title_oops);
			mResultInfoTextView.setText(R.string.bmi_underweight_info);
		} else if (result < 18.5) {
			mUnderweightNumTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mUnderweightTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mResultTitleTextView.setText(R.string.bmi_result_title_good);
			mResultInfoTextView.setText(R.string.bmi_underweight_mild_info);
		} else if (result < 25) {
			mNormalNumTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mNormalTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mResultTitleTextView.setText(R.string.bmi_result_title_great);
			mResultInfoTextView.setText(R.string.bmi_normal_info);
		} else if (result < 30) {
			mOverweightNumTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mOverweightTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mResultTitleTextView.setText(R.string.bmi_result_title_good);
			mResultInfoTextView.setText(R.string.bmi_overweight_info);
		} else {
			mObesityNumTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mObesityTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mResultTitleTextView.setText(R.string.bmi_result_title_oops);
			mResultInfoTextView.setText(R.string.bmi_obesity_info);
		}
	}
}
package wojtek.pockettrainer.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import wojtek.pockettrainer.R;

/**
 * @author Wojtek Kolendo
 * @date 10.09.2016
 */
public class BmiResultFragment extends Fragment {

	private CardView mResultCardView;
	private TextView mResultTextView;
	private TextView mResultTitleTextView, mResultInfoTextView;
	private TextView mSeverelyNumTextView, mUnderweightNumTextView, mNormalNumTextView, mOverweightNumTextView, mObesityNumTextView;
	private TextView mSeverelyTextView, mUnderweightTextView, mNormalTextView, mOverweightTextView, mObesityTextView;


	public static BmiResultFragment newInstance() {
		return new BmiResultFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bmi_result, container, false);
		mResultCardView = (CardView) view.findViewById(R.id.bmi_result_info_card);
		mResultTextView = (TextView) view.findViewById(R.id.bmi_result);
		mResultTitleTextView = (TextView) view.findViewById(R.id.bmi_result_title);
		mResultInfoTextView = (TextView) view.findViewById(R.id.bmi_result_info);

		mSeverelyNumTextView = (TextView) view.findViewById(R.id.bmi_severely_underweight_num);
		mSeverelyTextView = (TextView) view.findViewById(R.id.bmi_severely_underweight);

		mUnderweightNumTextView = (TextView) view.findViewById(R.id.bmi_underweight_num);
		mUnderweightTextView = (TextView) view.findViewById(R.id.bmi_underweight);

		mNormalNumTextView = (TextView) view.findViewById(R.id.bmi_normal_num);
		mNormalTextView = (TextView) view.findViewById(R.id.bmi_normal);

		mOverweightNumTextView = (TextView) view.findViewById(R.id.bmi_overweight_num);
		mOverweightTextView = (TextView) view.findViewById(R.id.bmi_overweight);

		mObesityNumTextView = (TextView) view.findViewById(R.id.bmi_obesity_num);
		mObesityTextView = (TextView) view.findViewById(R.id.bmi_obesity);
		return view;
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
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
public class BfpResultFragment extends Fragment {

	private CardView mResultCardView;
	private TextView mResultTextView;
	private TextView mResultTitleTextView, mResultInfoTextView;
	private TextView mSeverelyNumTextView, mUnderweightNumTextView, mNormalNumTextView, mOverweightNumTextView, mObesityNumTextView;
	private TextView mSeverelyTextView, mUnderweightTextView, mNormalTextView, mOverweightTextView, mObesityTextView;


	public static BfpResultFragment newInstance() {
		return new BfpResultFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bfp_result, container, false);
		mResultCardView = (CardView) view.findViewById(R.id.bfp_result_info_card);
		mResultTextView = (TextView) view.findViewById(R.id.bfp_result);
		mResultTitleTextView = (TextView) view.findViewById(R.id.bfp_result_title);
		mResultInfoTextView = (TextView) view.findViewById(R.id.bfp_result_info);

		mSeverelyNumTextView = (TextView) view.findViewById(R.id.bfp_severely_underweight_num);
		mSeverelyTextView = (TextView) view.findViewById(R.id.bfp_severely_underweight);

		mUnderweightNumTextView = (TextView) view.findViewById(R.id.bfp_underweight_num);
		mUnderweightTextView = (TextView) view.findViewById(R.id.bfp_underweight);

		mNormalNumTextView = (TextView) view.findViewById(R.id.bfp_normal_num);
		mNormalTextView = (TextView) view.findViewById(R.id.bfp_normal);

		mOverweightNumTextView = (TextView) view.findViewById(R.id.bfp_overweight_num);
		mOverweightTextView = (TextView) view.findViewById(R.id.bfp_overweight);

		mObesityNumTextView = (TextView) view.findViewById(R.id.bfp_obesity_num);
		mObesityTextView = (TextView) view.findViewById(R.id.bfp_obesity);
		return view;
	}

	public void setResult(double result) {
		mResultTextView.setText(String.valueOf(result));
		mResultCardView.setVisibility(View.VISIBLE);

		if (result < 17) {
			mSeverelyNumTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mSeverelyTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mResultTitleTextView.setText(R.string.bfp_result_title_oops);
			mResultInfoTextView.setText(R.string.bfp_underweight_info);
		} else if (result < 18.5) {
			mUnderweightNumTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mUnderweightTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mResultTitleTextView.setText(R.string.bfp_result_title_good);
			mResultInfoTextView.setText(R.string.bfp_underweight_mild_info);
		} else if (result < 25) {
			mNormalNumTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mNormalTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mResultTitleTextView.setText(R.string.bfp_result_title_great);
			mResultInfoTextView.setText(R.string.bfp_normal_info);
		} else if (result < 30) {
			mOverweightNumTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mOverweightTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mResultTitleTextView.setText(R.string.bfp_result_title_good);
			mResultInfoTextView.setText(R.string.bfp_overweight_info);
		} else {
			mObesityNumTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mObesityTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
			mResultTitleTextView.setText(R.string.bfp_result_title_oops);
			mResultInfoTextView.setText(R.string.bfp_obesity_info);
		}
	}
}
package wojtek.pockettrainer.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


import wojtek.pockettrainer.R;
import wojtek.pockettrainer.views.fragments.menu.BfpFragment;


/**
 * @author Wojtek Kolendo
 * @date 04.09.2016
 */
public class BfpCalculatorFragment extends Fragment {


	private RadioGroup mSystemRadioGroup, mSexRadioGroup;
	private Button mCalculateButton;
	private EditText mWeightEditText, mHeightEditText, mAgeEditText;

	private boolean mMetric, mMale;
	BfpFragment mParentFragment;


	public static BfpCalculatorFragment newInstance() {
		return new BfpCalculatorFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mParentFragment = (BfpFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_frame);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bfp_calculator, container, false);
		mWeightEditText = (EditText) view.findViewById(R.id.bfp_weight);
		mHeightEditText = (EditText) view.findViewById(R.id.bfp_height);
		mAgeEditText = (EditText) view.findViewById(R.id.bfp_age);
		mCalculateButton = (Button) view.findViewById(R.id.bfp_calculate);
		mSystemRadioGroup = (RadioGroup) view.findViewById(R.id.bfp_units);
		mSexRadioGroup = (RadioGroup) view.findViewById(R.id.bfp_sex);

		mSystemRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.bfp_metric) {
					mMetric = true;
				} else if(checkedId == R.id.bfp_imperial) {
					mMetric = false;
				}
			}
		});
		mSexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.bfp_male) {
					mMale = true;
				} else if(checkedId == R.id.bfp_female) {
					mMale = false;
				}
			}
		});
		mCalculateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validateData()) {
					calculateBfp(mMetric, mMale, mWeightEditText.getText().toString(),
							mHeightEditText.getText().toString(), mAgeEditText.getText().toString());
				}
			}
		});
		return view;
	}

	private boolean validateData() {
		if (mSystemRadioGroup.getCheckedRadioButtonId() == -1) {
			Toast.makeText(getContext(), R.string.validate_calculator_units, Toast.LENGTH_SHORT).show();
			return false;
		}
		if (mSexRadioGroup.getCheckedRadioButtonId() == -1) {
			Toast.makeText(getContext(), R.string.validate_calculator_sex, Toast.LENGTH_SHORT).show();
			return false;
		}
		if (TextUtils.isEmpty(mWeightEditText.getText().toString())) {
			Toast.makeText(getContext(), R.string.validate_calculator_weight, Toast.LENGTH_SHORT).show();
			return false;
		}
		if (TextUtils.isEmpty(mHeightEditText.getText().toString())) {
			Toast.makeText(getContext(), R.string.validate_calculator_height, Toast.LENGTH_SHORT).show();
			return false;
		}
		if (TextUtils.isEmpty(mAgeEditText.getText().toString())) {
			Toast.makeText(getContext(), R.string.validate_calculator_age, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void setResult(double result) {
		mParentFragment.switchFragment(1, round(result, 2));
	}

	private void calculateBfp(boolean metric, boolean male, String weight, String height, String age) {
		double result;
		double bmiResult = calculateBmi(metric, weight, height);
		if (male) {
			result = ((1.2 * bmiResult) + (0.23 * Double.parseDouble(age)) - 10.8 - 5.4);
		} else {
			result = ((1.2 * bmiResult) + (0.23 * Double.parseDouble(age)) - 5.4);
		}
		setResult(result);
	}

	private double calculateBmi(boolean metric, String weight, String height) {
		if (metric) {
			return metricBmi(Double.parseDouble(weight), (Double.parseDouble(height) / 100));
		} else {
			return imperialBmi(Double.parseDouble(weight), (Double.parseDouble(height)) * 12);
		}
	}

	private double metricBmi(double weight, double height) {
		return (weight/(height*height));
	}

	private double imperialBmi(double weight, double height) {
		return ((weight*703)/(height*height));
	}

	private double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}
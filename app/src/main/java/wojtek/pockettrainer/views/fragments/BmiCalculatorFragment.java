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
import wojtek.pockettrainer.views.fragments.menu.BmiFragment;


/**
 * @author Wojtek Kolendo
 * @date 04.09.2016
 */
public class BmiCalculatorFragment extends Fragment {

	private RadioGroup mSystemRadioGroup, mSexRadioGroup;
	private Button mCalculateButton;
	private EditText mWeightEditText, mHeightEditText;

	private boolean mMetric, mMale;
	BmiFragment mParentFragment;


	public static BmiCalculatorFragment newInstance() {
		return new BmiCalculatorFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mParentFragment = (BmiFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_frame);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bmi_calculator, container, false);
		mWeightEditText = (EditText) view.findViewById(R.id.bmi_weight);
		mHeightEditText = (EditText) view.findViewById(R.id.bmi_height);
		mCalculateButton = (Button) view.findViewById(R.id.bmi_calculate);
		mSystemRadioGroup = (RadioGroup) view.findViewById(R.id.bmi_units);
		mSexRadioGroup = (RadioGroup) view.findViewById(R.id.bmi_sex);

		mSystemRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.bmi_metric) {
					mMetric = true;
				} else if(checkedId == R.id.bmi_imperial) {
					mMetric = false;
				}
			}
		});
		mSexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.bmi_male) {
					mMale = true;
				} else if(checkedId == R.id.bmi_female) {
					mMale = false;
				}
			}
		});
		mCalculateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validateData()) {
					calculateBmi(mMetric, mMale, mWeightEditText.getText().toString(),
							mHeightEditText.getText().toString());
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
//		if (mSexRadioGroup.getCheckedRadioButtonId() == -1) {
//			Toast.makeText(getContext(), R.string.validate_calculator_sex, Toast.LENGTH_SHORT).show();
//			return false;
//		}
		if (TextUtils.isEmpty(mWeightEditText.getText().toString())) {
			Toast.makeText(getContext(), R.string.validate_calculator_weight, Toast.LENGTH_SHORT).show();
			return false;
		}
		if (TextUtils.isEmpty(mHeightEditText.getText().toString())) {
			Toast.makeText(getContext(), R.string.validate_calculator_height, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void setResult(double result) {
		mParentFragment.switchFragment(1, round(result, 2));
	}

	public void calculateBmi(boolean metric, boolean male, String weight, String height) {
		double result;
		if (metric) {
			result = metricBmi(Double.parseDouble(weight), (Double.parseDouble(height) / 100));
		} else {
			result = imperialBmi(Double.parseDouble(weight), (Double.parseDouble(height)) * 12);
		}
		setResult(result);
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
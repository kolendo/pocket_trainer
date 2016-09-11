package wojtek.pockettrainer.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.humandevice.android.v4.mvpframework.PresenterFragment;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.presenters.BmiCalculatorPresenter;
import wojtek.pockettrainer.presenters.impl.BmiCalculatorPresenterImpl;
import wojtek.pockettrainer.views.BmiCalculatorView;
import wojtek.pockettrainer.views.fragments.menu.BmiFragment;


/**
 * @author Wojtek Kolendo
 * @date 04.09.2016
 */
public class BmiCalculatorFragment extends PresenterFragment<BmiCalculatorView, BmiCalculatorPresenter> implements BmiCalculatorView {

	private RadioGroup mSystemRadioGroup, mSexRadioGroup;
	private Button mCalculateButton;
	private EditText mWeightEditText, mHeightEditText;

	private boolean mMetric, mMale;
	BmiFragment mParentFragment;


	public static BmiCalculatorFragment newInstance() {
		return new BmiCalculatorFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mParentFragment = (BmiFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_frame);
		return inflater.inflate(R.layout.fragment_bmi_calculator, container, false);
	}

	@Override
	public Class<? extends BmiCalculatorPresenter> getPresenterClass() {
		return BmiCalculatorPresenterImpl.class;
	}

	@Override
	protected void initView(View view) {
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
					getPresenter().calculateBmi(mMetric, mMale, mWeightEditText.getText().toString(),
							mHeightEditText.getText().toString());
				}
			}
		});
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

	@Override
	public void setResult(double result) {
		mParentFragment.switchFragment(1, getPresenter().round(result, 2));
	}


}
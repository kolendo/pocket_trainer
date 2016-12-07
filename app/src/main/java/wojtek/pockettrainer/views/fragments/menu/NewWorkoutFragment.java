package wojtek.pockettrainer.views.fragments.menu;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.models.enums.WorkoutType;
import wojtek.pockettrainer.R;
import wojtek.pockettrainer.repositories.preferences.SettingsPreferences;
import wojtek.pockettrainer.views.activities.MapsWorkoutActivity;
import wojtek.pockettrainer.views.adapters.WorkoutTypeAdapter;

import static wojtek.pockettrainer.models.enums.WorkoutType.CYCLING;
import static wojtek.pockettrainer.models.enums.WorkoutType.RUNNING;

/**
 * @author Wojtek Kolendo
 * @date 11.09.2016
 */
public class NewWorkoutFragment extends Fragment {

	private static final int REQUEST_PERMISSION_LOCATION = 10101;

	private ImageView mWorkoutTypeImageView;
	private Button mStartView;
	private SettingsPreferences mSettingsPreferences = new SettingsPreferences();
	private int mSelectedType = -1;

	public static NewWorkoutFragment newInstance() {
		return new NewWorkoutFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_new_workout, container, false);
		mWorkoutTypeImageView = (ImageView) view.findViewById(R.id.workout_type_icon);
		mStartView = (Button) view.findViewById(R.id.workout_start);
		Spinner workoutTypeSpinner = (Spinner) view.findViewById(R.id.workout_type);
		ArrayAdapter<WorkoutType> workoutTypeAdapter = new WorkoutTypeAdapter(getContext(), R.layout.spinner_workout_type);
		View accuracySwitchContent = view.findViewById(R.id.switch_accuracy_workout_content);
		Switch lessAccuracySwitch = (Switch) view.findViewById(R.id.switch_accuracy_workout);
		RadioGroup unitsRadioGroup = (RadioGroup) view.findViewById(R.id.units_group);

		lessAccuracySwitch.setChecked(mSettingsPreferences.isLessAccuracyEnabled());
		lessAccuracySwitch.setOnCheckedChangeListener(onCheckedAccuracySwitchListener);
		if (mSettingsPreferences.isMetricUnitsEnabled()) {
			unitsRadioGroup.check(R.id.units_metric);
		} else {
			unitsRadioGroup.check(R.id.units_imperial);
		}
		unitsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				if(i == R.id.units_metric) {
					mSettingsPreferences.setMetricUnitsEnabled(true);
				} else if(i == R.id.units_imperial) {
					mSettingsPreferences.setMetricUnitsEnabled(false);
				}
			}
		});
		workoutTypeSpinner.setAdapter(workoutTypeAdapter);
		workoutTypeSpinner.setOnItemSelectedListener(onSpinnerListener);
		accuracySwitchContent.setOnClickListener(onAccuracySwitchContentListener);
		mStartView.setOnClickListener(onStartListener);
		return view;
	}

	private View.OnClickListener onStartListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (checkGpsPermission()) {
					startMapActivity();
			} else {
				requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
			}
		}
	};

	private View.OnClickListener onAccuracySwitchContentListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			lessAccuracyInfoDialog();
		}
	};

	private CompoundButton.OnCheckedChangeListener onCheckedAccuracySwitchListener = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			mSettingsPreferences.setLessAccuracyEnabled(isChecked);
		}
	};

	private AdapterView.OnItemSelectedListener onSpinnerListener = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			switch ((int)id) {
				case 0:
					mWorkoutTypeImageView.setImageResource(CYCLING.getResourceImage());
					mSelectedType = 0;
					break;
				case 1:
					mWorkoutTypeImageView.setImageResource(RUNNING.getResourceImage());
					mSelectedType = 1;
					break;
			}
		}
		@Override
		public void onNothingSelected(AdapterView<?> parentView) {
		}
	};

	private boolean checkGpsPermission() {
		return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == REQUEST_PERMISSION_LOCATION) {
			if (checkGpsPermission()) {
				onStartListener.onClick(mStartView);
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	private void startMapActivity() {
		Intent intent = new Intent(getContext(), MapsWorkoutActivity.class);
		intent.putExtra(MapsWorkoutActivity.EXTRA_WORKOUT, mSelectedType);
		startActivity(intent);
	}


	private void lessAccuracyInfoDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
		alertDialogBuilder.setMessage(R.string.less_accuracy_msg)
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}
}
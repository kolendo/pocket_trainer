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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.models.enums.WorkoutType;
import wojtek.pockettrainer.R;
import wojtek.pockettrainer.views.activities.MapsWorkoutActivity;
import wojtek.pockettrainer.views.adapters.WorkoutTypeAdapter;

/**
 * @author Wojtek Kolendo
 * @date 11.09.2016
 */
public class NewWorkoutFragment extends Fragment {

	private static final int REQUEST_PERMISSION_LOCATION = 10101;

	Spinner mWorkoutTypeSpinner;
	ArrayAdapter<WorkoutType> mWorkoutTypeAdapter;
	ImageView mWorkoutTypeImageView;
	Button mStartView;
	View mAccuracySwitchContent;

	Workout mWorkout;

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
		mWorkoutTypeSpinner = (Spinner) view.findViewById(R.id.workout_type);
		mWorkoutTypeAdapter = new WorkoutTypeAdapter(getContext(), R.layout.spinner_workout_type);
		mWorkoutTypeImageView = (ImageView) view.findViewById(R.id.workout_type_icon);
		mStartView = (Button) view.findViewById(R.id.workout_start);
		mAccuracySwitchContent = view.findViewById(R.id.switch_accuracy_workout_content);
		mWorkout = new Workout();

		mWorkoutTypeSpinner.setAdapter(mWorkoutTypeAdapter);
		mWorkoutTypeSpinner.setOnItemSelectedListener(onSpinnerListener);

		mAccuracySwitchContent.setOnClickListener(onAccuracySwitchContentListener);
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

	private AdapterView.OnItemSelectedListener onSpinnerListener = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			switch ((int)id) {
				case 0:
					mWorkout.setWorkoutType(WorkoutType.CYCLING);
					break;
				case 1:
					mWorkout.setWorkoutType(WorkoutType.RUNNING);
					break;
			}
			mWorkoutTypeImageView.setImageResource(mWorkout.getWorkoutType().getResourceImage());
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
		mWorkout.setStartDate(System.currentTimeMillis());
		Intent intent = new Intent(getContext(), MapsWorkoutActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(MapsWorkoutActivity.EXTRA_WORKOUT, mWorkout);
		intent.putExtras(bundle);
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
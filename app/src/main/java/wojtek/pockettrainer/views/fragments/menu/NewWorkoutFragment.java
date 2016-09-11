package wojtek.pockettrainer.views.fragments.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.humandevice.android.v4.mvpframework.PresenterFragment;

import wojtek.pockettrainer.models.enums.WorkoutType;
import wojtek.pockettrainer.views.NewWorkoutView;
import wojtek.pockettrainer.presenters.NewWorkoutPresenter;
import wojtek.pockettrainer.presenters.impl.NewWorkoutPresenterImpl;
import wojtek.pockettrainer.R;
import wojtek.pockettrainer.views.adapters.WorkoutTypeAdapter;

/**
 * @author Wojtek Kolendo
 * @date 11.09.2016
 */
public class NewWorkoutFragment extends PresenterFragment<NewWorkoutView, NewWorkoutPresenter> implements NewWorkoutView {

	Spinner mWorkoutTypeSpinner;
	ArrayAdapter<WorkoutType> mWorkoutTypeAdapter;
	ImageView mWorkoutTypeImageView;
	View mStartView;

	public static NewWorkoutFragment newInstance() {
		return new NewWorkoutFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_new_workout, container, false);
	}

	@Override
	public Class<? extends NewWorkoutPresenter> getPresenterClass() {
		return NewWorkoutPresenterImpl.class;
	}

	@Override
	protected void initView(View view) {
		mWorkoutTypeSpinner = (Spinner) view.findViewById(R.id.workout_type);
		mWorkoutTypeAdapter = new WorkoutTypeAdapter(getContext(), R.layout.spinner_workout_type);
		mWorkoutTypeImageView = (ImageView) view.findViewById(R.id.workout_type_icon);
		mStartView = view.findViewById(R.id.workout_start);

		mWorkoutTypeSpinner.setAdapter(mWorkoutTypeAdapter);
		mWorkoutTypeSpinner.setOnItemSelectedListener(onSpinnerListener);

		mStartView.setOnClickListener(onStartListener);
	}

	private View.OnClickListener onStartListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getContext(), "Not implemented", Toast.LENGTH_SHORT).show();
		}
	};

	private AdapterView.OnItemSelectedListener onSpinnerListener = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			switch ((int)id) {
				case 0:
					mWorkoutTypeImageView.setImageResource(R.drawable.ic_directions_run_black_24dp);
					break;
				case 1:
					mWorkoutTypeImageView.setImageResource(R.drawable.ic_directions_bike_black_24dp);
					break;
			}
		}
		@Override
		public void onNothingSelected(AdapterView<?> parentView) {
		}
	};
}
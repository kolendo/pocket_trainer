package wojtek.pockettrainer.views.fragments.menu;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.models.enums.WorkoutType;
import wojtek.pockettrainer.views.activities.MainActivity;
import wojtek.pockettrainer.views.adapters.WorkoutsAdapter;
import wojtek.pockettrainer.views.adapters.items.WorkoutItem;
import wojtek.pockettrainer.views.adapters.listeners.OnItemLongClickListener;

/**
 * @author Wojtek Kolendo
 * @date 15.10.2016
 */

public class WorkoutsHistoryFragment extends Fragment implements View.OnClickListener {

	private RecyclerView mRecyclerView;
	private WorkoutsAdapter mAdapter;
	private ArrayList<WorkoutItem> mWorkoutsList;
	private WorkoutItem mSelectedItem;

	private AlertDialog mDialog;
	private FloatingActionButton mActionButton;

	public static WorkoutsHistoryFragment newInstance() {
		return new WorkoutsHistoryFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_workouts_history, container, false);
		mWorkoutsList = new ArrayList<>();

		mActionButton = (FloatingActionButton) view.findViewById(R.id.workouts_list_action);
		mActionButton.setOnClickListener(this);

		mRecyclerView = (RecyclerView) view.findViewById(R.id.workouts_list);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		mAdapter = new WorkoutsAdapter(new OnItemLongClickListener<WorkoutItem>() {
			@Override
			public void onItemLongClicked(WorkoutItem item) {
				onWorkoutClicked(item);
			}
		});
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(layoutManager);

		setWorkouts();
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.workouts_list_action:
				onActionClicked();
				break;
		}
	}

	public void setWorkouts() {
		// TODO: 15.10.2016 pobieranie z bazy
		ArrayList<Workout> workoutsList = new ArrayList<>();
		Workout workout_c = new Workout();
		workout_c.setDistance(43.65);
		workout_c.setWorkoutType(WorkoutType.CYCLING);
		workout_c.setStartDate(System.currentTimeMillis());
		workoutsList.add(workout_c);
		workout_c = new Workout();
		workout_c.setDistance(43.65);
		workout_c.setWorkoutType(WorkoutType.CYCLING);
		workout_c.setStartDate(System.currentTimeMillis());
		workoutsList.add(workout_c);
		workout_c = new Workout();
		workout_c.setDistance(43.65);
		workout_c.setWorkoutType(WorkoutType.RUNNING);
		workout_c.setStartDate(System.currentTimeMillis());
		workoutsList.add(workout_c);
		workout_c = new Workout();
		workout_c.setDistance(43.65);
		workout_c.setWorkoutType(WorkoutType.CYCLING);
		workout_c.setStartDate(System.currentTimeMillis());
		workoutsList.add(workout_c);

		for (Workout workout : workoutsList) {
			mWorkoutsList.add(new WorkoutItem(workout));
		}

		mAdapter.setWorkoutsList(mWorkoutsList);
		mAdapter.notifyDataSetChanged();
	}

	public void onWorkoutClicked(WorkoutItem item) {
		if (item.isSelected()) {
			item.setSelected(false, true);
			mAdapter.notifyItemChanged(mWorkoutsList.indexOf(item));
			showAddButton();
			mSelectedItem = null;
		} else {
			for (WorkoutItem cardItem : mWorkoutsList) {
				if (cardItem.isSelected()) {
					cardItem.setSelected(false, true);
					mAdapter.notifyItemChanged(mWorkoutsList.indexOf(cardItem));
					break;
				}
			}
			item.setSelected(true, true);
			mAdapter.notifyItemChanged(mWorkoutsList.indexOf(item));
			if (mSelectedItem == null)
				showDeleteButton();
			mSelectedItem = item;
		}
	}

	public void onActionClicked() {
		if (mSelectedItem != null && mSelectedItem.isSelected()) {
			showConfirmDeleteDialog(mSelectedItem);
		} else {
			(getActivity()).setTitle(R.string.workout);
			((MainActivity)getActivity()).changeFragment(NewWorkoutFragment.newInstance(), true);
		}
	}

	public void showAddButton() {
		mActionButton.hide(new FloatingActionButton.OnVisibilityChangedListener() {
			@Override
			public void onHidden(FloatingActionButton fab) {
				mActionButton.setImageResource(R.drawable.ic_add_white_24dp);
				mActionButton.show();
			}
		});
	}

	public void showDeleteButton() {
		mActionButton.hide(new FloatingActionButton.OnVisibilityChangedListener() {
			@Override
			public void onHidden(FloatingActionButton fab) {
				mActionButton.setImageResource(R.drawable.ic_delete_white_24dp);
				mActionButton.show();
			}
		});
	}

	public void showConfirmDeleteDialog(final WorkoutItem item) {
		mDialog = new AlertDialog.Builder(getContext(), R.style.TrainerTheme_Dialog)
				.setTitle(R.string.delete_workout_title)
				.setMessage(R.string.delete_workout_msg)
				.setCancelable(true)
				.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						deleteWorkout(item);
					}
				})
				.setNegativeButton(R.string.cancel, null)
				.create();
		mDialog.show();
	}

	public void deleteWorkout(final WorkoutItem item) {
		mWorkoutsList.remove(item);
//		mAdapter.notifyItemRemoved();
	}
}

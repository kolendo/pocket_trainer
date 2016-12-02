package wojtek.pockettrainer.views.fragments.menu;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.TrainerApplication;
import wojtek.pockettrainer.models.JoinWorkoutWithDistances;
import wojtek.pockettrainer.models.JoinWorkoutWithDistancesDao;
import wojtek.pockettrainer.models.JoinWorkoutWithPositions;
import wojtek.pockettrainer.models.JoinWorkoutWithPositionsDao;
import wojtek.pockettrainer.models.JoinWorkoutWithSpeeds;
import wojtek.pockettrainer.models.JoinWorkoutWithSpeedsDao;
import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.models.WorkoutDao;
import wojtek.pockettrainer.models.enums.WorkoutType;
import wojtek.pockettrainer.views.activities.MainActivity;
import wojtek.pockettrainer.views.activities.WorkoutDetailsActivity;
import wojtek.pockettrainer.views.adapters.WorkoutsAdapter;
import wojtek.pockettrainer.views.adapters.items.WorkoutItem;
import wojtek.pockettrainer.views.adapters.listeners.OnItemClickListener;
import wojtek.pockettrainer.views.adapters.listeners.OnItemLongClickListener;
import wojtek.pockettrainer.views.interfaces.MainActivityListener;

/**
 * @author Wojtek Kolendo
 * @date 15.10.2016
 */

public class WorkoutsHistoryFragment extends Fragment implements View.OnClickListener {

	MainActivityListener mMainActivityListener;

	private RecyclerView mRecyclerView;
	private WorkoutsAdapter mAdapter;
	private ArrayList<WorkoutItem> mWorkoutsList;
	private WorkoutItem mSelectedItem;
	private WorkoutDao mWorkoutDao;

	private AlertDialog mDialog;
	private FloatingActionButton mActionButton;

	public static WorkoutsHistoryFragment newInstance() {
		return new WorkoutsHistoryFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mWorkoutDao = TrainerApplication.getDaoSession().getWorkoutDao();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_workouts_history, container, false);
		mWorkoutsList = new ArrayList<>();

		mActionButton = (FloatingActionButton) view.findViewById(R.id.workouts_list_action);
		mActionButton.setOnClickListener(this);

		mRecyclerView = (RecyclerView) view.findViewById(R.id.workouts_list);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		mAdapter = new WorkoutsAdapter(
				new OnItemLongClickListener<WorkoutItem>() {
					@Override
					public void onItemLongClicked(WorkoutItem item) {
						onWorkoutClicked(item);
					}
				},
				new OnItemClickListener<Long>() {
					@Override
					public void onItemClicked(Long id) {
						openWorkoutDetailsActivity(id);
					}
				});
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(layoutManager);

		setWorkouts();
		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			mMainActivityListener = (MainActivityListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() + " must implement MainActivityListener");
		}
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
		List<Workout> workoutsList = mWorkoutDao.loadAll();

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
			getActivity().setTitle(R.string.workout);
			mMainActivityListener.setDrawerItemChecked(R.id.nav_workout);
			mMainActivityListener.changeFragment(NewWorkoutFragment.newInstance(), true);
		}
	}

	public void showAddButton() {
		mActionButton.hide(new FloatingActionButton.OnVisibilityChangedListener() {
			@Override
			public void onHidden(FloatingActionButton fab) {
				mActionButton.setImageResource(R.drawable.ic_add_white_24dp);
				mActionButton.show();
				mSelectedItem = null;
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
//		deleting workout
		mWorkoutDao.deleteByKey(item.getObject().getId());
//		deleting locations list
		QueryBuilder queryBuilder = TrainerApplication.getDaoSession().getJoinWorkoutWithPositionsDao().queryBuilder();
		queryBuilder.where(JoinWorkoutWithPositionsDao.Properties.WorkoutId.eq(item.getObject().getId()));
		List<JoinWorkoutWithPositions> joinWorkoutWithPositionsList = queryBuilder.list();
		for (JoinWorkoutWithPositions itemJoin : joinWorkoutWithPositionsList) {
			TrainerApplication.getDaoSession().getPositionDao().deleteByKey(itemJoin.getPositionId());
			TrainerApplication.getDaoSession().getJoinWorkoutWithPositionsDao().deleteByKey(itemJoin.getId());
		}
//		deleting speeds list
		queryBuilder = TrainerApplication.getDaoSession().getJoinWorkoutWithSpeedsDao().queryBuilder();
		queryBuilder.where(JoinWorkoutWithSpeedsDao.Properties.WorkoutId.eq(item.getObject().getId()));
		List<JoinWorkoutWithSpeeds> joinWorkoutWithSpeedsList = queryBuilder.list();
		for (JoinWorkoutWithSpeeds itemJoin : joinWorkoutWithSpeedsList) {
			TrainerApplication.getDaoSession().getSpeedDao().deleteByKey(itemJoin.getSpeedId());
			TrainerApplication.getDaoSession().getJoinWorkoutWithSpeedsDao().deleteByKey(itemJoin.getId());
		}
//		deleting distances list
		queryBuilder = TrainerApplication.getDaoSession().getJoinWorkoutWithDistancesDao().queryBuilder();
		queryBuilder.where(JoinWorkoutWithDistancesDao.Properties.WorkoutId.eq(item.getObject().getId()));
		List<JoinWorkoutWithDistances> joinWorkoutWithDistancesList = queryBuilder.list();
		for (JoinWorkoutWithDistances itemJoin : joinWorkoutWithDistancesList) {
			TrainerApplication.getDaoSession().getDistanceDao().deleteByKey(itemJoin.getDistanceId());
			TrainerApplication.getDaoSession().getJoinWorkoutWithDistancesDao().deleteByKey(itemJoin.getId());
		}

		int index = mWorkoutsList.indexOf(item);
		if (index >= 0) {
			mWorkoutsList.remove(index);
			mAdapter.notifyItemRemoved(index);
		}
		showAddButton();
	}

	public void openWorkoutDetailsActivity(long workoutId) {
		Intent intent = new Intent(getActivity(), WorkoutDetailsActivity.class);
		intent.putExtra(WorkoutDetailsActivity.EXTRA_WORKOUT_DETAILS, workoutId);
		startActivity(intent);
	}
}

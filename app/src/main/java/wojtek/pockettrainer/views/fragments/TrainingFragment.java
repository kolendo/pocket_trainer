package wojtek.pockettrainer.views.fragments;


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
import android.widget.Toast;

import java.util.ArrayList;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.Training;
import wojtek.pockettrainer.models.TrainingActivity;
import wojtek.pockettrainer.views.adapters.TrainingActivitiesAdapter;
import wojtek.pockettrainer.views.adapters.items.TrainingActivityItem;
import wojtek.pockettrainer.views.adapters.listeners.OnItemClickListener;
import wojtek.pockettrainer.views.adapters.listeners.OnItemLongClickListener;

/**
 * @author Wojtek Kolendo
 * @date 18.11.2016
 */

public class TrainingFragment extends Fragment implements View.OnClickListener {

	public static final String EXTRA_TRAINING = "EXTRA_TRAINING";

	private RecyclerView mRecyclerView;
	private TrainingActivitiesAdapter mAdapter;
	private ArrayList<TrainingActivityItem> mActivitiesList;
	private TrainingActivityItem mSelectedItem;
	private Training mTraining;

	private AlertDialog mDialog;
	private FloatingActionButton mActionButton;

	public static TrainingFragment newInstance(Training training) {
		TrainingFragment fragment = new TrainingFragment();
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TRAINING, training);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTraining = (Training) getArguments().getSerializable(EXTRA_TRAINING);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_training, container, false);
		mActivitiesList = new ArrayList<>();

		mActionButton = (FloatingActionButton) view.findViewById(R.id.training_action);
		mActionButton.setOnClickListener(this);

		mRecyclerView = (RecyclerView) view.findViewById(R.id.training_activities_list);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		mAdapter = new TrainingActivitiesAdapter(
				new OnItemLongClickListener<TrainingActivityItem>() {
					@Override
					public void onItemLongClicked(TrainingActivityItem item) {
						onTrainingClicked(item);
					}
				},
				new OnItemClickListener<TrainingActivityItem>() {
					@Override
					public void onItemClicked(TrainingActivityItem item) {
						openActivityActivity(item.getObject());
					}
				});
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(layoutManager);

		setTrainings();
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
			case R.id.trainings_list_action:
				onActionClicked();
				break;
		}
	}

	public void setTrainings() {
		// TODO: 15.10.2016 pobieranie z bazy

		TrainingActivity training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		TrainingActivity training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		training_a = new TrainingActivity();
		training_a.setSets(4);
		training_a.setDescription("Straight lifting");
		mTraining.getTrainingActivities().add(training_a);

		training_b = new TrainingActivity();
		training_b.setTime(123456);
		training_b.setDescription("Running");
		mTraining.getTrainingActivities().add(training_b);

		for (TrainingActivity training : mTraining.getTrainingActivities()) {
			mActivitiesList.add(new TrainingActivityItem(training));
		}

		mAdapter.setActivitiesList(mActivitiesList);
		mAdapter.notifyDataSetChanged();
	}

	public void onTrainingClicked(TrainingActivityItem item) {
		if (item.isSelected()) {
			item.setSelected(false, true);
			mAdapter.notifyItemChanged(mActivitiesList.indexOf(item));
			showAddButton();
			mSelectedItem = null;
		} else {
			for (TrainingActivityItem cardItem : mActivitiesList) {
				if (cardItem.isSelected()) {
					cardItem.setSelected(false, true);
					mAdapter.notifyItemChanged(mActivitiesList.indexOf(cardItem));
					break;
				}
			}
			item.setSelected(true, true);
			mAdapter.notifyItemChanged(mActivitiesList.indexOf(item));
			if (mSelectedItem == null)
				showDeleteButton();
			mSelectedItem = item;
		}
	}

	public void onActionClicked() {
		if (mSelectedItem != null && mSelectedItem.isSelected()) {
			showConfirmDeleteDialog(mSelectedItem);
		} else {
			Toast.makeText(getContext(), "not implemented", Toast.LENGTH_SHORT).show();
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

	public void showConfirmDeleteDialog(final TrainingActivityItem item) {
		mDialog = new AlertDialog.Builder(getContext(), R.style.TrainerTheme_Dialog)
				.setTitle(R.string.delete_training_title)
				.setMessage(R.string.delete_training_msg)
				.setCancelable(true)
				.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						deleteTraining(item);
					}
				})
				.setNegativeButton(R.string.cancel, null)
				.create();
		mDialog.show();
	}

	public void deleteTraining(final TrainingActivityItem item) {
//		mTrainingsList.remove(item);
//		mAdapter.notifyItemRemoved();
	}

	public void openActivityActivity(TrainingActivity trainingActivity) {
//		Intent intent = new Intent(getActivity(), TrainingActivity.class);
//		intent.putExtra(wojtek.pockettrainer.views.activities.TrainingActivity.EXTRA_TRAINING, trainingActivity);
//		startActivity(intent);
	}
}

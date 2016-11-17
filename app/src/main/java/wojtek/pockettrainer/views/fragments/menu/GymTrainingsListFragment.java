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
import android.widget.Toast;

import java.util.ArrayList;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.Training;
import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.models.enums.WorkoutType;
import wojtek.pockettrainer.views.activities.MainActivity;
import wojtek.pockettrainer.views.adapters.TrainingsAdapter;
import wojtek.pockettrainer.views.adapters.items.TrainingItem;
import wojtek.pockettrainer.views.adapters.listeners.OnItemLongClickListener;

/**
 * @author Wojtek Kolendo
 * @date 17.11.2016
 */

public class GymTrainingsListFragment extends Fragment implements View.OnClickListener {

	private RecyclerView mRecyclerView;
	private TrainingsAdapter mAdapter;
	private ArrayList<TrainingItem> mTrainingsList;
	private TrainingItem mSelectedItem;

	private AlertDialog mDialog;
	private FloatingActionButton mActionButton;

	public static GymTrainingsListFragment newInstance() {
		return new GymTrainingsListFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_trainings_list, container, false);
		mTrainingsList = new ArrayList<>();

		mActionButton = (FloatingActionButton) view.findViewById(R.id.trainings_list_action);
		mActionButton.setOnClickListener(this);

		mRecyclerView = (RecyclerView) view.findViewById(R.id.trainings_list);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		mAdapter = new TrainingsAdapter(new OnItemLongClickListener<TrainingItem>() {
			@Override
			public void onItemLongClicked(TrainingItem item) {
				onTrainingClicked(item);
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
		ArrayList<Training> trainingsList = new ArrayList<>();

		Training training_a = new Training();
		training_a.setId(0);
		training_a.setDescription("Sunday chest lifting");
		trainingsList.add(training_a);

		Training training_b = new Training();
		training_b.setId(1);
		training_b.setDescription("Friday running night");
		trainingsList.add(training_b);

		for (Training training : trainingsList) {
			mTrainingsList.add(new TrainingItem(training));
		}

		mAdapter.setTrainingsList(mTrainingsList);
		mAdapter.notifyDataSetChanged();
	}

	public void onTrainingClicked(TrainingItem item) {
		if (item.isSelected()) {
			item.setSelected(false, true);
			mAdapter.notifyItemChanged(mTrainingsList.indexOf(item));
			showAddButton();
			mSelectedItem = null;
		} else {
			for (TrainingItem cardItem : mTrainingsList) {
				if (cardItem.isSelected()) {
					cardItem.setSelected(false, true);
					mAdapter.notifyItemChanged(mTrainingsList.indexOf(cardItem));
					break;
				}
			}
			item.setSelected(true, true);
			mAdapter.notifyItemChanged(mTrainingsList.indexOf(item));
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

	public void showConfirmDeleteDialog(final TrainingItem item) {
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

	public void deleteTraining(final TrainingItem item) {
//		mTrainingsList.remove(item);
//		mAdapter.notifyItemRemoved();
	}
}

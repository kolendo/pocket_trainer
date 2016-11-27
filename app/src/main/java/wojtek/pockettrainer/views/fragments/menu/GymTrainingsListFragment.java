package wojtek.pockettrainer.views.fragments.menu;


import android.app.Dialog;
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
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rafalzajfert.androidlogger.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.TrainerApplication;
import wojtek.pockettrainer.models.JoinTrainingsWithTrainingActivities;
import wojtek.pockettrainer.models.JoinTrainingsWithTrainingActivitiesDao;
import wojtek.pockettrainer.models.Training;
import wojtek.pockettrainer.models.TrainingDao;
import wojtek.pockettrainer.views.activities.TrainingActivity;
import wojtek.pockettrainer.views.adapters.TrainingsAdapter;
import wojtek.pockettrainer.views.adapters.items.TrainingItem;
import wojtek.pockettrainer.views.adapters.listeners.OnItemClickListener;
import wojtek.pockettrainer.views.adapters.listeners.OnItemLongClickListener;

/**
 * @author Wojtek Kolendo
 * @date 17.11.2016
 */

public class GymTrainingsListFragment extends Fragment implements View.OnClickListener {

	private RecyclerView mRecyclerView;
	private TrainingsAdapter mAdapter;
	private ArrayList<TrainingItem> mTrainingsList;
	private TrainingItem mSelectedItem, mUpdatedItem;
	private TrainingDao mTrainingDao;

	private AlertDialog mDialog;
	private FloatingActionButton mActionButton;

	public static GymTrainingsListFragment newInstance() {
		return new GymTrainingsListFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTrainingDao = TrainerApplication.getDaoSession().getTrainingDao();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_trainings_list, container, false);
		mTrainingsList = new ArrayList<>();

		mActionButton = (FloatingActionButton) view.findViewById(R.id.trainings_list_action);
		mActionButton.setOnClickListener(this);

		mRecyclerView = (RecyclerView) view.findViewById(R.id.trainings_list);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		mAdapter = new TrainingsAdapter(
				new OnItemLongClickListener<TrainingItem>() {
					@Override
					public void onItemLongClicked(TrainingItem item) {
						onTrainingClicked(item);
					}
				},
				new OnItemClickListener<TrainingItem>() {
					@Override
					public void onItemClicked(TrainingItem item) {
						mUpdatedItem = item;
						openTrainingActivity(item.getObject());
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
	public void onResume() {
		super.onResume();
		if (mUpdatedItem != null) {
			int index = mTrainingsList.indexOf(mUpdatedItem);
			mTrainingsList.get(index).getObject().resetTrainingActivities();
			mAdapter.notifyItemChanged(index);
			mUpdatedItem = null;
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
		List<Training> trainingsList = mTrainingDao.loadAll();

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
			showNewTrainingDialog();
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
		mTrainingDao.deleteByKey(item.getObject().getId());
		QueryBuilder queryBuilder = TrainerApplication.getDaoSession().getJoinTrainingsWithTrainingActivitiesDao().queryBuilder();
		queryBuilder.where(JoinTrainingsWithTrainingActivitiesDao.Properties.TrainingId.eq(item.getObject().getId()));
		List<JoinTrainingsWithTrainingActivities> joinTrainingsWithTrainingActivitiesList = queryBuilder.list();
		for (JoinTrainingsWithTrainingActivities itemJoin : joinTrainingsWithTrainingActivitiesList) {
			TrainerApplication.getDaoSession().getTrainingActivityDao().deleteByKey(itemJoin.getTrainingActivityId());
			TrainerApplication.getDaoSession().getJoinTrainingsWithTrainingActivitiesDao().deleteByKey(itemJoin.getId());
		}

		int index = mTrainingsList.indexOf(item);
		if (index >= 0) {
			mTrainingsList.remove(index);
			mAdapter.notifyItemRemoved(index);
		}
		showAddButton();
	}

	public void addNewTraining(Training training) {
		mTrainingDao.insert(training);
		mTrainingsList.add(new TrainingItem(training));
		mAdapter.notifyItemInserted(mTrainingsList.size() - 1);
	}

	public void showNewTrainingDialog() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.TrainerTheme_Dialog);
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View dialogView = inflater.inflate(R.layout.dialog_new_training, null);
		final EditText trainingTitleEditText = (EditText) dialogView.findViewById(R.id.training_edit_title);
		builder.setView(dialogView)
				.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Training training = new Training(null, trainingTitleEditText.getText().toString(), false);
						addNewTraining(training);
					}
				})
				.setNegativeButton(R.string.cancel, null);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.show();
	}

	public void openTrainingActivity(Training training) {
		Intent intent = new Intent(getActivity(), TrainingActivity.class);
		intent.putExtra(TrainingActivity.EXTRA_TRAINING, training.getId());
		startActivity(intent);
	}
}

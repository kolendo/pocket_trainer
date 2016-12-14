package wojtek.pockettrainer.views.fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rafalzajfert.androidlogger.Logger;
import com.shawnlin.numberpicker.NumberPicker;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.TrainerApplication;
import wojtek.pockettrainer.models.JoinTrainingsWithTrainingActivities;
import wojtek.pockettrainer.models.JoinTrainingsWithTrainingActivitiesDao;
import wojtek.pockettrainer.models.Training;
import wojtek.pockettrainer.models.TrainingActivity;
import wojtek.pockettrainer.models.TrainingActivityDao;
import wojtek.pockettrainer.models.TrainingDao;
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
	private JoinTrainingsWithTrainingActivitiesDao mJoinTrainingsWithTrainingActivitiesDao;
	private TrainingActivityDao mTrainingActivityDao;

	private AlertDialog mDialog;
	private FloatingActionButton mActionButton;

	public static TrainingFragment newInstance(long trainingId) {
		TrainingFragment fragment = new TrainingFragment();
		Bundle args = new Bundle();
		args.putLong(EXTRA_TRAINING, trainingId);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		long trainingId = getArguments().getLong(EXTRA_TRAINING);
		getTrainingFromDao(trainingId);
	}

	private void getTrainingFromDao(long id) {
		TrainingDao trainingDao = TrainerApplication.getDaoSession().getTrainingDao();
		mJoinTrainingsWithTrainingActivitiesDao = TrainerApplication.getDaoSession().getJoinTrainingsWithTrainingActivitiesDao();
		mTrainingActivityDao = TrainerApplication.getDaoSession().getTrainingActivityDao();

		QueryBuilder queryBuilder = trainingDao.queryBuilder().where(TrainingDao.Properties.Id.eq(id));

		Logger.error(mJoinTrainingsWithTrainingActivitiesDao.loadAll().size());
		mTraining = (Training) queryBuilder.unique();
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
						onTrainingLongClicked(item);
					}
				},
				new OnItemClickListener<TrainingActivityItem>() {
					@Override
					public void onItemClicked(TrainingActivityItem item) {
						// TODO: 27.11.2016 edit
					}
				});
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(layoutManager);
		setTrainingActivities();
		return view;
	}

	public void setTrainingActivities() {
		Logger.error(mTraining.getId(), mTraining.getTrainingActivities().size());
		if (mTraining != null) {
			for (TrainingActivity training : mTraining.getTrainingActivities()) {
				mActivitiesList.add(new TrainingActivityItem(training));
			}

			mAdapter.setActivitiesList(mActivitiesList);
			mAdapter.notifyDataSetChanged();
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
			case R.id.training_action:
				onActionClicked();
				break;
		}
	}

	public void onTrainingLongClicked(TrainingActivityItem item) {
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
			showNewTrainingActivityDialog();
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
		QueryBuilder queryBuilder = mJoinTrainingsWithTrainingActivitiesDao.queryBuilder();
		queryBuilder.and(JoinTrainingsWithTrainingActivitiesDao.Properties.TrainingId.eq(mTraining.getId()),
				JoinTrainingsWithTrainingActivitiesDao.Properties.TrainingActivityId.eq(item.getObject().getId()));
		JoinTrainingsWithTrainingActivities joinTrainingsWithTrainingActivities = (JoinTrainingsWithTrainingActivities) queryBuilder.list().get(0);
		int index = mActivitiesList.indexOf(item);

		if (joinTrainingsWithTrainingActivities != null && index >= 0) {
			mTrainingActivityDao.deleteByKey(item.getObject().getId());
			mJoinTrainingsWithTrainingActivitiesDao.deleteByKey(joinTrainingsWithTrainingActivities.getId());
			mActivitiesList.remove(index);
			mAdapter.notifyItemRemoved(index);
		}
		showAddButton();
	}

	public void addNewTrainingActivity(TrainingActivity trainingActivity) {
		mTrainingActivityDao.insert(trainingActivity);
		mJoinTrainingsWithTrainingActivitiesDao.insert(new JoinTrainingsWithTrainingActivities(null, mTraining.getId(), trainingActivity.getId()));
		mActivitiesList.add(new TrainingActivityItem(trainingActivity));
		mAdapter.notifyItemInserted(mActivitiesList.size() - 1);
	}


	public void showNewTrainingActivityDialog() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.TrainerTheme_Dialog);
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View dialogView = inflater.inflate(R.layout.dialog_new_training_activity, null);
		final EditText trainingActivityTitleEditText = (EditText) dialogView.findViewById(R.id.training_activity_edit_title);
		final EditText trainingActivityDescriptionEditText = (EditText) dialogView.findViewById(R.id.training_activity_edit_description);
		final RadioGroup typeRadioGroup = (RadioGroup) dialogView.findViewById(R.id.training_activity_radio_group);
		final NumberPicker numberPickerSets = (NumberPicker) dialogView.findViewById(R.id.number_picker_sets);
		final NumberPicker numberPickerTimeMin = (NumberPicker) dialogView.findViewById(R.id.number_picker_time_min);
		final NumberPicker numberPickerTimeSec = (NumberPicker) dialogView.findViewById(R.id.number_picker_time_sec);
		final View numberPickerTimeFrame = dialogView.findViewById(R.id.number_picker_time_frame);

		typeRadioGroup.check(R.id.training_activity_radio_sets);
		numberPickerTimeFrame.setVisibility(View.INVISIBLE);
		numberPickerSets.setVisibility(View.VISIBLE);

		typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				if(i == R.id.training_activity_radio_sets) {
					numberPickerTimeFrame.setVisibility(View.INVISIBLE);
					numberPickerSets.setVisibility(View.VISIBLE);
				} else if(i == R.id.training_activity_radio_time) {
					numberPickerSets.setVisibility(View.INVISIBLE);
					numberPickerTimeFrame.setVisibility(View.VISIBLE);
				}
			}
		});

		builder.setView(dialogView)
				.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						String title = trainingActivityTitleEditText.getText().toString();
						String description = trainingActivityDescriptionEditText.getText().toString();
						if (typeRadioGroup.getCheckedRadioButtonId() == R.id.training_activity_radio_sets) {
							addNewTrainingActivity(new TrainingActivity(null, title, description, numberPickerSets.getValue(), 0));
						} else {
							long milis = TimeUnit.MINUTES.toMillis((long) numberPickerTimeMin.getValue()) + TimeUnit.SECONDS.toMillis((long) numberPickerTimeSec.getValue());
							addNewTrainingActivity(new TrainingActivity(null, title, description, 0, milis));
						}
					}
				})
				.setNegativeButton(R.string.cancel, null);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.show();
	}
}

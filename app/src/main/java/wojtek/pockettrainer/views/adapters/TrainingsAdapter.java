package wojtek.pockettrainer.views.adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.TrainerApplication;
import wojtek.pockettrainer.models.Training;
import wojtek.pockettrainer.views.adapters.items.TrainingItem;
import wojtek.pockettrainer.views.adapters.listeners.OnItemClickListener;
import wojtek.pockettrainer.views.adapters.listeners.OnItemLongClickListener;

/**
 * @author Wojtek Kolendo
 * @date 17.11.2016
 */

public class TrainingsAdapter extends RecyclerView.Adapter<TrainingsAdapter.MyViewHolder> {

	public static final int COLOR_GREY_200 = ContextCompat.getColor(TrainerApplication.getContext(), R.color.grey_200);
	public static final int COLOR_TRANSPARENT = ContextCompat.getColor(TrainerApplication.getContext(), R.color.transparent);

	private ArrayList<TrainingItem> mTrainings;
	private OnItemLongClickListener<TrainingItem> mItemLongClickListener;
	private OnItemClickListener<TrainingItem> mItemClickListener;


	public TrainingsAdapter(OnItemLongClickListener<TrainingItem> itemLongClickListener, OnItemClickListener<TrainingItem> itemClickListener) {
		mItemLongClickListener = itemLongClickListener;
		mItemClickListener = itemClickListener;
		mTrainings = new ArrayList<>();
	}

	@Override
	public TrainingsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false));
	}

	@Override
	public void onBindViewHolder(TrainingsAdapter.MyViewHolder holder, int position) {
		TrainingItem trainingItem = mTrainings.get(position);
		holder.setTraining(trainingItem.getObject());
		holder.setSelected(trainingItem.isSelected());
	}

	@Override
	public int getItemCount() {
		return mTrainings.size();
	}

	public void setTrainingsList(ArrayList<TrainingItem> cardsList) {
		mTrainings = cardsList;
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		private ImageView mStarImageView;
		private TextView mDescriptionTextView, mActivitiesTextView;
		private View mMoreView;

		public MyViewHolder(View itemView) {
			super(itemView);
			mStarImageView = (ImageView) itemView.findViewById(R.id.item_training_fav);
			mDescriptionTextView = (TextView) itemView.findViewById(R.id.item_training_description);
			mActivitiesTextView = (TextView) itemView.findViewById(R.id.item_training_activities);
			mMoreView = itemView.findViewById(R.id.item_training_more);

			itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					if (mItemLongClickListener != null) {
						int position = getLayoutPosition();
						mItemLongClickListener.onItemLongClicked(mTrainings.get(position));
					}
					return true;
				}
			});

			mStarImageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = getLayoutPosition();
					if (!mTrainings.get(position).getObject().getFavourite()) {
						mTrainings.get(position).getObject().setFavourite(true);
						mStarImageView.setImageResource(R.drawable.ic_star_border_gray_24dp);
					} else {
						mTrainings.get(position).getObject().setFavourite(false);
						mStarImageView.setImageResource(R.drawable.ic_star_accent_24dp);
					}
				}
			});
			mStarImageView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					if (mItemLongClickListener != null) {
						int position = getLayoutPosition();
						mItemLongClickListener.onItemLongClicked(mTrainings.get(position));
					}
					return true;
				}
			});

			mMoreView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mItemClickListener != null) {
						int position = getLayoutPosition();
						mItemClickListener.onItemClicked(mTrainings.get(position));
					}
				}
			});
			mMoreView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					if (mItemLongClickListener != null) {
						int position = getLayoutPosition();
						mItemLongClickListener.onItemLongClicked(mTrainings.get(position));
					}
					return true;
				}
			});
		}

		public void setTraining(@NonNull Training training) {
			mDescriptionTextView.setText(training.getTitle());
			int activities = training.getTrainingActivities().size();
			if (activities > 1) {
				mActivitiesTextView.setText(String.format(TrainerApplication.getContext().getString(R.string.training_activities_num), activities));
			} else if (activities == 1) {
				mActivitiesTextView.setText(R.string.training_activities_one);
			} else {
				mActivitiesTextView.setText(R.string.training_activities_zero);
			}
		}

		public void setSelected(boolean selected) {
			if (selected) {
				itemView.setBackgroundColor(COLOR_GREY_200);
			} else {
				itemView.setBackgroundColor(COLOR_TRANSPARENT);
			}
		}
	}
}
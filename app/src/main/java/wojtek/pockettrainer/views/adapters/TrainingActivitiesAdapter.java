package wojtek.pockettrainer.views.adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rafalzajfert.androidlogger.Logger;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.TrainerApplication;
import wojtek.pockettrainer.models.TrainingActivity;
import wojtek.pockettrainer.views.adapters.items.TrainingActivityItem;
import wojtek.pockettrainer.views.adapters.listeners.OnItemClickListener;
import wojtek.pockettrainer.views.adapters.listeners.OnItemLongClickListener;

/**
 * @author Wojtek Kolendo
 * @date 18.11.2016
 */

public class TrainingActivitiesAdapter extends RecyclerView.Adapter<TrainingActivitiesAdapter.MyViewHolder> {

	public static final int COLOR_GREY_200 = ContextCompat.getColor(TrainerApplication.getContext(), R.color.grey_200);
	public static final int COLOR_TRANSPARENT = ContextCompat.getColor(TrainerApplication.getContext(), R.color.transparent);

	private ArrayList<TrainingActivityItem> mActivities;
	private OnItemLongClickListener<TrainingActivityItem> mItemLongClickListener;
	private OnItemClickListener<TrainingActivityItem> mItemClickListener;


	public TrainingActivitiesAdapter(OnItemLongClickListener<TrainingActivityItem> itemLongClickListener, OnItemClickListener<TrainingActivityItem> itemClickListener) {
		mItemLongClickListener = itemLongClickListener;
		mItemClickListener = itemClickListener;
		mActivities = new ArrayList<>();
	}

	@Override
	public TrainingActivitiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training_activity, parent, false));
	}

	@Override
	public void onBindViewHolder(TrainingActivitiesAdapter.MyViewHolder holder, int position) {
		TrainingActivityItem trainingActivity = mActivities.get(position);
		holder.setActivity(trainingActivity.getObject());
		holder.setSelected(trainingActivity.isSelected());
	}

	@Override
	public int getItemCount() {
		return mActivities.size();
	}

	public void setActivitiesList(ArrayList<TrainingActivityItem> cardsList) {
		mActivities = cardsList;
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		private TextView mDescriptionTextView, mSubDescriptionTextView;

		public MyViewHolder(View itemView) {
			super(itemView);
			mDescriptionTextView = (TextView) itemView.findViewById(R.id.item_activity_description);
			mSubDescriptionTextView = (TextView) itemView.findViewById(R.id.item_activity_subdescription);

			itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					if (mItemLongClickListener != null) {
						int position = getLayoutPosition();
						mItemLongClickListener.onItemLongClicked(mActivities.get(position));
					}
					return true;
				}
			});

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mItemClickListener != null) {
						int position = getLayoutPosition();
						mItemClickListener.onItemClicked(mActivities.get(position));
					}
				}
			});
		}

		public void setActivity(@NonNull TrainingActivity trainingActivity) {
			mDescriptionTextView.setText(trainingActivity.getTitle());
			int sets = trainingActivity.getSets();
			long time = trainingActivity.getTime();

			if (sets == 1) {
				mSubDescriptionTextView.setText(R.string.activity_set);
			} else if (sets > 1) {
				mSubDescriptionTextView.setText(String.format(TrainerApplication.getContext().getString(R.string.activity_sets), sets));
			} else if (time > 0) {
				mSubDescriptionTextView.setText(getDurationBreakdown(time));
			}
		}

		public void setSelected(boolean selected) {
			if (selected) {
				itemView.setBackgroundColor(COLOR_GREY_200);
			} else {
				itemView.setBackgroundColor(COLOR_TRANSPARENT);
			}
		}

		private String getDurationBreakdown(long millis)
		{
			long hours = TimeUnit.MILLISECONDS.toHours(millis);
			millis -= TimeUnit.HOURS.toMillis(hours);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
			millis -= TimeUnit.MINUTES.toMillis(minutes);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

			StringBuilder sb = new StringBuilder(64);
			if (hours > 0) {
				sb.append(hours);
				if (hours > 1) {
					sb.append(String.format(TrainerApplication.getContext().getString(R.string.activity_hours), hours));
				} else {
					sb.append(String.format(TrainerApplication.getContext().getString(R.string.activity_hour), hours));
				}
			}
			if (minutes > 0) {
				sb.append(minutes);
				if (minutes > 1) {
					sb.append(String.format(TrainerApplication.getContext().getString(R.string.activity_minutes), minutes));
				} else {
					sb.append(String.format(TrainerApplication.getContext().getString(R.string.activity_minute), minutes));
				}
			}
			if (seconds > 0) {
				sb.append(seconds);
				if (seconds > 1) {
					sb.append(String.format(TrainerApplication.getContext().getString(R.string.activity_seconds), seconds));
				} else {
					sb.append(String.format(TrainerApplication.getContext().getString(R.string.activity_second), seconds));
				}
			}
			return(sb.toString());
		}
	}
}
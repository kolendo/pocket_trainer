package wojtek.pockettrainer.views.adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.TrainerApplication;
import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.views.adapters.items.WorkoutItem;
import wojtek.pockettrainer.views.adapters.listeners.OnItemLongClickListener;

/**
 * @author Wojtek Kolendo
 * @date 15.10.2016
 */

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.MyViewHolder> {

	public static final int COLOR_GREY_200 = ContextCompat.getColor(TrainerApplication.getContext(), R.color.grey_200);
	public static final int COLOR_TRANSPARENT = ContextCompat.getColor(TrainerApplication.getContext(), R.color.transparent);

	private ArrayList<WorkoutItem> mWorkouts;
	private OnItemLongClickListener<WorkoutItem> mItemLongClickListener;

	public WorkoutsAdapter(OnItemLongClickListener<WorkoutItem> itemClickListener) {
		mItemLongClickListener = itemClickListener;
		mWorkouts = new ArrayList<>();
	}

	@Override
	public WorkoutsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false));
	}

	@Override
	public void onBindViewHolder(WorkoutsAdapter.MyViewHolder holder, int position) {
		WorkoutItem workoutItem = mWorkouts.get(position);
		holder.setWorkout(workoutItem.getObject());
		holder.setSelected(workoutItem.isSelected());
	}

	@Override
	public int getItemCount() {
		return mWorkouts.size();
	}

	public void setWorkoutsList(ArrayList<WorkoutItem> cardsList) {
		mWorkouts = cardsList;
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		private ImageView mTypeImageView;
		private TextView mDateTextView, mDistanceTextView;
		private View mMoreView;

		public MyViewHolder(View itemView) {
			super(itemView);
			mTypeImageView = (ImageView) itemView.findViewById(R.id.item_workout_type);
			mDateTextView = (TextView) itemView.findViewById(R.id.item_workout_date);
			mDistanceTextView = (TextView) itemView.findViewById(R.id.item_workout_distance);
			mMoreView = itemView.findViewById(R.id.item_workout_more);

			itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					if (mItemLongClickListener != null) {
						int position = getLayoutPosition();
						mItemLongClickListener.onItemLongClicked(mWorkouts.get(position));
					}
					return true;
				}
			});

			mMoreView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(TrainerApplication.getContext(), "not implemented", Toast.LENGTH_SHORT).show();
				}
			});
		}

		public void setWorkout(@NonNull Workout workout) {
			mDateTextView.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(workout.getStartDate().getTime())
					+ DateFormat.getDateInstance(DateFormat.LONG).format(workout.getStartDate().getTime()));
			switch (workout.getWorkoutType()) {
				case CYCLING:
					mTypeImageView.setImageResource(R.drawable.ic_directions_bike_gray_48dp);
					mDistanceTextView.setText(String.format(TrainerApplication.getContext().getString(R.string.distance_units_km), workout.getDistance()));
					break;
				case RUNNING:
					mTypeImageView.setImageResource(R.drawable.ic_directions_run_gray_48dp);
					mDistanceTextView.setText(String.format(TrainerApplication.getContext().getString(R.string.distance_units_m), workout.getDistance()));
					break;
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

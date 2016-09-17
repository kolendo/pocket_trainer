package wojtek.pockettrainer.models;

import java.io.Serializable;
import java.util.Calendar;

import wojtek.pockettrainer.models.enums.WorkoutType;

/**
 *
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */
public class Workout implements Serializable {

	private long mId;

	private Calendar mDate;

	private WorkoutType mWorkoutType;


//	region Getters and Setters
	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public Calendar getDate() {
		return mDate;
	}

	public void setDate(Calendar date) {
		mDate = date;
	}

	public void setDate(long date) {
		mDate=Calendar.getInstance();
		mDate.setTimeInMillis(date);
	}

	public WorkoutType getWorkoutType() {
		return mWorkoutType;
	}

	public void setWorkoutType(WorkoutType workoutType) {
		mWorkoutType = workoutType;
	}
//	endregion

	@Override
	public String toString() {
		return "Packing{" +
				"mId=" + mId +
				", mDate='" + mDate + '\'' +
				", mWorkoutType=" + mWorkoutType +
				'}';
	}
}

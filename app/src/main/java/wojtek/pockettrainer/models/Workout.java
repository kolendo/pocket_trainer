package wojtek.pockettrainer.models;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import wojtek.pockettrainer.models.enums.WorkoutType;

/**
 *
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */
public class Workout implements Serializable {

	private long mId;

	private WorkoutType mWorkoutType;

	private Calendar mStartDate;

	private Calendar mFinishDate;

	private ArrayList<Location> mRouteList;

//	region Getters and Setters
	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public Calendar getStartDate() {
		return mStartDate;
	}

	public void setStartDate(Calendar date) {
		mStartDate = date;
	}

	public void setStartDate(long date) {
		mStartDate = Calendar.getInstance();
		mStartDate.setTimeInMillis(date);
	}

	public Calendar getFinishDate() {
		return mFinishDate;
	}

	public void setFinishDate(Calendar date) {
		mFinishDate = date;
	}

	public void setFinishDate(long date) {
		mFinishDate = Calendar.getInstance();
		mFinishDate.setTimeInMillis(date);
	}

	public WorkoutType getWorkoutType() {
		return mWorkoutType;
	}

	public void setWorkoutType(WorkoutType workoutType) {
		mWorkoutType = workoutType;
	}

	public ArrayList<Location> getRouteList() {
		return mRouteList;
	}

	public void setRouteList(ArrayList<Location> routeList) {
		mRouteList = routeList;
	}

	//	endregion

	@Override
	public String toString() {
		return "Packing{" +
				"mId=" + mId +
				", mStartDate='" + mStartDate + '\'' +
				", mFinishDate='" + mFinishDate + '\'' +
				", mWorkoutType=" + mWorkoutType + '\'' +
				", mRouteList=" + mRouteList +
				'}';
	}
}

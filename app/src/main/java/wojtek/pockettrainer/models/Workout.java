package wojtek.pockettrainer.models;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

	private long mElapsedTime;

	private ArrayList<Location> mLocationsList;

	private ArrayList<Double> mSpeedsList;

	private double mDistance;

	private double mAverageSpeed;

	private double mTopSpeed;

	public Workout() {
		mLocationsList = new ArrayList<>();
		mSpeedsList = new ArrayList<>();
		mDistance = 0;
		mAverageSpeed = 0;
		mTopSpeed = 0;
	}

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

	public ArrayList<Location> getLocationsList() {
		return mLocationsList;
	}

	public void setLocationsList(ArrayList<Location> locationsList) {
		mLocationsList = locationsList;
	}

	public void addLocationsList(Location location) {
		mLocationsList.add(location);
	}

	public ArrayList<Double> getSpeedsList() {
		return mSpeedsList;
	}

	public void setSpeedsList(ArrayList<Double> speedsList) {
		mSpeedsList = speedsList;
	}

	public void addSpeedsList(double speed) {
		mSpeedsList.add(speed);
	}

	public long getElapsedTime() {
		return mElapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		mElapsedTime = elapsedTime;
	}

	public double getDistance() {
		return mDistance;
	}

	public void setDistance(double distance) {
		mDistance = distance;
	}

	public void addDistance(double distance) {
		mDistance += distance;
	}

	public double getAverageSpeed() {
		return mAverageSpeed;
	}

	public void setAverageSpeed(double averageSpeed) {
		mAverageSpeed = averageSpeed;
	}

	public double getTopSpeed() {
		return mTopSpeed;
	}

	public void setTopSpeed(double topSpeed) {
		if (mTopSpeed < topSpeed) {
			mTopSpeed = topSpeed;
		}
	}

	//	endregion

	@Override
	public String toString() {
		return "Workout{" +
				"mId=" + mId +
				", mStartDate='" + mStartDate + '\'' +
				", mFinishDate='" + mFinishDate + '\'' +
				", mElapsedTime='" + mElapsedTime + '\'' +
				", mWorkoutType=" + mWorkoutType + '\'' +
				", mDistance=" + mDistance + '\'' +
				", mAverageSpeed=" + mAverageSpeed + '\'' +
				", mTopSpeed=" + mTopSpeed + '\'' +
				", mSpeedsList=" + mSpeedsList + '\'' +
				", mLocationsList=" + mLocationsList +
				'}';
	}
}

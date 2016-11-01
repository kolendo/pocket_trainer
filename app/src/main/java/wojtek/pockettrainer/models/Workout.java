package wojtek.pockettrainer.models;

import android.annotation.SuppressLint;
import android.location.Location;
import android.widget.Switch;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import wojtek.pockettrainer.models.enums.Units;
import wojtek.pockettrainer.models.enums.WorkoutType;

/**
 *
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */
public class Workout implements Serializable {

	private long mId;

	private WorkoutType mWorkoutType;

	private Units mUnits;

	private Calendar mStartDate;

	private String mStartAddress;

	private Calendar mFinishDate;

	private String mFinishAddress;

	private String mElapsedTime;

	private ArrayList<Position> mLocationsList;

	private HashMap<Long, Double> mSpeedsHashMap;

	private HashMap<Long, Double> mDistancesHashMap;

	private double mDistance;

	private double mAverageSpeed;

	private double mAverageSpeedWithoutStops;

	private double mTopSpeed;

	private int mBurnedCalories;

	private boolean mFavourite;

	@SuppressLint("UseSparseArrays")
	public Workout() {
		mLocationsList = new ArrayList<>();
		mSpeedsHashMap = new HashMap<>();
		mDistancesHashMap = new HashMap<>();
		mDistance = 0;
		mAverageSpeed = 0;
		mAverageSpeedWithoutStops = 0;
		mTopSpeed = 0;
		mBurnedCalories = 0;
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

	public int getLocationsListLastIndex() {
		return mLocationsList.size() - 1;
	}

	public Position getLocation(int index) {
		return mLocationsList.get(index);
	}

	public ArrayList<Position> getLocationsList() {
		return mLocationsList;
	}

	public LatLng getLocationsListLatLng(int index) {
		return mLocationsList.get(index).getLatLng();
	}

	public void setLocationsList(ArrayList<Position> locationsList) {
		mLocationsList = locationsList;
	}

	public void addLocationsList(Location location) {
		mLocationsList.add(new Position(location));
	}

	public HashMap<Long, Double> getSpeedsHashMap() {
		return mSpeedsHashMap;
	}

	public void setSpeedsHashMap(HashMap<Long, Double> speedsHashMap) {
		mSpeedsHashMap = speedsHashMap;
	}

	public HashMap<Long, Double> getDistancesHashMap() {
		return mDistancesHashMap;
	}

	public void setDistancesHashMap(HashMap<Long, Double> distancesHashMap) {
		mDistancesHashMap = distancesHashMap;
	}

	public String getElapsedTime() {
		return mElapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
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

	public double getAverageSpeedWithoutStops() {
		return mAverageSpeedWithoutStops;
	}

	public void setAverageSpeedWithoutStops(double averageSpeedWithoutStops) {
		mAverageSpeedWithoutStops = averageSpeedWithoutStops;
	}

	public double getTopSpeed() {
		return mTopSpeed;
	}

	public void setTopSpeed(double topSpeed) {
		if (mTopSpeed < topSpeed) {
			mTopSpeed = topSpeed;
		}
	}

	public String getStartAddress() {
		return mStartAddress;
	}

	public void setStartAddress(String startAddress) {
		mStartAddress = startAddress;
	}

	public String getFinishAddress() {
		return mFinishAddress;
	}

	public void setFinishAddress(String finishAddress) {
		mFinishAddress = finishAddress;
	}

	public boolean isFavourite() {
		return mFavourite;
	}

	public void setFavourite(boolean favourite) {
		mFavourite = favourite;
	}

	public Units getUnits() {
		return mUnits;
	}

	public void setUnits(Units units) {
		mUnits = units;
	}

	public int getBurnedCalories() {
		return mBurnedCalories;
	}

	public void setBurnedCalories(int burnedCalories) {
		mBurnedCalories = burnedCalories;
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
				", mBurnedCalories=" + mBurnedCalories + '\'' +
				", mLocationsList=" + mLocationsList +
				'}';
	}
}

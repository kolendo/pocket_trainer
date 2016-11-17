package wojtek.pockettrainer.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Wojtek Kolendo
 * @date 17.11.2016
 */

public class Training implements Serializable {

	private long mId;

	private String mDescription;

	private ArrayList<TrainingActivity> mTrainingActivities;

	private boolean mFavourite;

	public Training() {
		mFavourite = false;
		mTrainingActivities = new ArrayList<>();
	}

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public ArrayList<TrainingActivity> getTrainingActivities() {
		return mTrainingActivities;
	}

	public void setTrainingActivities(ArrayList<TrainingActivity> trainingActivities) {
		mTrainingActivities = trainingActivities;
	}

	public boolean isFavourite() {
		return mFavourite;
	}

	public void setFavourite(boolean favourite) {
		mFavourite = favourite;
	}

	@Override
	public String toString() {
		return "TrainingActivity{" +
				"mId=" + mId +
				", mDescription='" + mDescription + '\'' +
				", mTrainingActivities='" + mTrainingActivities + '\'' +
				", mFavourite='" + mFavourite + '\'' +
				'}';
	}
}

package wojtek.pockettrainer.models;

import java.io.Serializable;

/**
 * @author Wojtek Kolendo
 * @date 17.11.2016
 */

public class TrainingActivity implements Serializable {

	private String mDescription;

	private int mSets;

	private long mTime;


	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public int getSets() {
		return mSets;
	}

	public void setSets(int sets) {
		mSets = sets;
	}

	public long getTime() {
		return mTime;
	}

	public void setTime(long time) {
		mTime = time;
	}


	@Override
	public String toString() {
		return "TrainingActivity{" +
				"mDescription=" + mDescription +
				", mSets='" + mSets + '\'' +
				", mTime='" + mTime + '\'' +
				'}';
	}
}

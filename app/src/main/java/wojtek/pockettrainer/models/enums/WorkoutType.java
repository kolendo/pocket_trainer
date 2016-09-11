package wojtek.pockettrainer.models.enums;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.TrainerApplication;

/**
 * @author Wojtek Kolendo
 * @date 11.09.2016
 */
public enum WorkoutType {

	RUNNING(R.string.running),
	CYCLING(R.string.cycling);

	private int resourceId;

	WorkoutType(int id)
	{
		resourceId = id;
	}

	public String getString()
	{
		return TrainerApplication.getContext().getString(resourceId);
	}

}

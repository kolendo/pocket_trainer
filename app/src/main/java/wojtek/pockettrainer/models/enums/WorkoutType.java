package wojtek.pockettrainer.models.enums;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.TrainerApplication;

/**
 * @author Wojtek Kolendo
 * @date 11.09.2016
 */
public enum WorkoutType {

	CYCLING(0, R.string.cycling, R.drawable.ic_directions_bike_black_24dp),
	RUNNING(1, R.string.running, R.drawable.ic_directions_run_black_24dp);

	private final int id;
	private int resourceString;
	private int resourceImage;

	WorkoutType(int id, int string, int image)
	{
		this.id = id;
		resourceString = string;
		resourceImage = image;
	}

	public String getString()
	{
		return TrainerApplication.getContext().getString(resourceString);
	}

	public int getResourceString() {
		return resourceString;
	}

	public int getResourceImage() {
		return resourceImage;
	}

	public int getId() {
		return id;
	}
}

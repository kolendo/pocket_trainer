package wojtek.pockettrainer.views.interfaces;

import wojtek.pockettrainer.models.Workout;

/**
 * @author Wojtek Kolendo
 * @date 29.09.2016
 */

public interface MapsWorkoutActivityListener {

	void stopLocationUpdates();

	void startLocationUpdates();

	void finishWorkout(Workout workout);

}

package wojtek.pockettrainer.views.adapters.items;

import android.support.annotation.NonNull;

import wojtek.pockettrainer.models.Workout;

/**
 * @author Wojtek Kolendo
 * @date 15.10.2016
 */

public class WorkoutItem extends SelectableItem<Workout> {

	public WorkoutItem(@NonNull Workout object) {
		super(object);
	}
}

package wojtek.pockettrainer.views.adapters.items;

import android.support.annotation.NonNull;

import wojtek.pockettrainer.models.Training;
import wojtek.pockettrainer.models.Workout;

/**
 * @author Wojtek Kolendo
 * @date 17.11.2016
 */

public class TrainingItem extends SelectableItem<Training> {

	public TrainingItem(@NonNull Training object) {
		super(object);
	}
}

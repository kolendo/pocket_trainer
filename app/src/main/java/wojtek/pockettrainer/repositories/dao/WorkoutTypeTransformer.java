package wojtek.pockettrainer.repositories.dao;

import org.greenrobot.greendao.converter.PropertyConverter;

import wojtek.pockettrainer.models.enums.WorkoutType;

/**
 * @author Wojtek Kolendo
 * @date 30.11.2016
 */

public class WorkoutTypeTransformer implements PropertyConverter<WorkoutType, Integer> {


	@Override
	public WorkoutType convertToEntityProperty(Integer databaseValue) {
		if (databaseValue == null) {
			return null;
		}
		for (WorkoutType workoutType : WorkoutType.values()) {
			if (workoutType.getId() == databaseValue) {
				return workoutType;
			}
		}
		return null;
	}

	@Override
	public Integer convertToDatabaseValue(WorkoutType entityProperty) {
		if (entityProperty == null) {
			return null;
		} else {
			return entityProperty.getId();
		}
	}
}

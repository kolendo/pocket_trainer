package wojtek.pockettrainer.repositories.dao;

import org.greenrobot.greendao.converter.PropertyConverter;

import wojtek.pockettrainer.models.enums.Units;

/**
 * @author Wojtek Kolendo
 * @date 30.11.2016
 */

public class UnitsTransformer implements PropertyConverter<Units, Integer> {


	@Override
	public Units convertToEntityProperty(Integer databaseValue) {
		if (databaseValue == null) {
			return null;
		}
		for (Units unit : Units.values()) {
			if (unit.getId() == databaseValue) {
				return unit;
			}
		}
		return null;
	}

	@Override
	public Integer convertToDatabaseValue(Units entityProperty) {
		if (entityProperty == null) {
			return null;
		} else {
			return entityProperty.getId();
		}
	}
}

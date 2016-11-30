package wojtek.pockettrainer.repositories.dao;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Calendar;

/**
 * @author Wojtek Kolendo
 * @date 30.11.2016
 */

public class CalendarTransformer implements PropertyConverter<Calendar, Long> {


	@Override
	public Calendar convertToEntityProperty(Long databaseValue) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(databaseValue);
		return calendar;
	}

	@Override
	public Long convertToDatabaseValue(Calendar entityProperty) {
		return entityProperty.getTimeInMillis();
	}
}

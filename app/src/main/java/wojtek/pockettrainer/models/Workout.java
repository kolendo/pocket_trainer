package wojtek.pockettrainer.models;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import wojtek.pockettrainer.models.enums.Units;
import wojtek.pockettrainer.models.enums.WorkoutType;
import wojtek.pockettrainer.repositories.dao.CalendarTransformer;
import wojtek.pockettrainer.repositories.dao.UnitsTransformer;
import wojtek.pockettrainer.repositories.dao.WorkoutTypeTransformer;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */

@Entity
public class Workout {

	@Id(autoincrement = true)
	private Long id;

	@Convert(converter = WorkoutTypeTransformer.class, columnType = Integer.class)
	private WorkoutType workoutType;

	@Convert(converter = UnitsTransformer.class, columnType = Integer.class)
	private Units units;

	@Convert(converter = CalendarTransformer.class, columnType = Long.class)
	private Calendar startDate;

	private String startAddress;

	@Convert(converter = CalendarTransformer.class, columnType = Long.class)
	private Calendar finishDate;

	private String finishAddress;

	private String elapsedTime;

	@ToMany
	@JoinEntity(
			entity = JoinWorkoutWithPositions.class,
			sourceProperty = "workoutId",
			targetProperty = "positionId"
	)
	private List<Position> locationsList;

	@ToMany
	@JoinEntity(
			entity = JoinWorkoutWithSpeeds.class,
			sourceProperty = "workoutId",
			targetProperty = "speedId"
	)
	private List<Speed> speedsList;

	@ToMany
	@JoinEntity(
			entity = JoinWorkoutWithDistances.class,
			sourceProperty = "workoutId",
			targetProperty = "distanceId"
	)
	private List<Distance> distancesList;

	private double distance;

	private double averageSpeed;

	private double averageSpeedWithoutStops;

	private double topSpeed;

	private int burnedCalories;

	private boolean favourite;

	/** Used to resolve relations */
	@Generated(hash = 2040040024)
	private transient DaoSession daoSession;

	/** Used for active entity operations. */
	@Generated(hash = 1950649078)
	private transient WorkoutDao myDao;

	public Workout() {
		distance = 0;
		averageSpeed = 0;
		averageSpeedWithoutStops = 0;
		topSpeed = 0;
		burnedCalories = 0;
	}

	@Generated(hash = 1098007056)
	public Workout(Long id, WorkoutType workoutType, Units units,
									Calendar startDate, String startAddress, Calendar finishDate,
									String finishAddress, String elapsedTime, double distance,
									double averageSpeed, double averageSpeedWithoutStops, double topSpeed,
									int burnedCalories, boolean favourite) {
					this.id = id;
					this.workoutType = workoutType;
					this.units = units;
					this.startDate = startDate;
					this.startAddress = startAddress;
					this.finishDate = finishDate;
					this.finishAddress = finishAddress;
					this.elapsedTime = elapsedTime;
					this.distance = distance;
					this.averageSpeed = averageSpeed;
					this.averageSpeedWithoutStops = averageSpeedWithoutStops;
					this.topSpeed = topSpeed;
					this.burnedCalories = burnedCalories;
					this.favourite = favourite;
	}

	//	region Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar date) {
		startDate = date;
	}

	public void setStartDate(long date) {
		startDate = Calendar.getInstance();
		startDate.setTimeInMillis(date);
	}

	public Calendar getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Calendar date) {
		finishDate = date;
	}

	public void setFinishDate(long date) {
		finishDate = Calendar.getInstance();
		finishDate.setTimeInMillis(date);
	}

	public WorkoutType getWorkoutType() {
		return workoutType;
	}

	public void setWorkoutType(WorkoutType workoutType) {
		this.workoutType = workoutType;
	}

	public int getLocationsListLastIndex() {
		return locationsList.size() - 1;
	}

	public Position getLocation(int index) {
		return locationsList.get(index);
	}

	public LatLng getLocationsListLatLng(int index) {
		return locationsList.get(index).getLatLng();
	}

	public void addLocationsList(Location location) {
		locationsList.add(new Position(location));
	}

	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void addDistance(double distance) {
		this.distance += distance;
	}

	public double getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(double averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public double getAverageSpeedWithoutStops() {
		return averageSpeedWithoutStops;
	}

	public void setAverageSpeedWithoutStops(double averageSpeedWithoutStops) {
		this.averageSpeedWithoutStops = averageSpeedWithoutStops;
	}

	public double getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(double topSpeed) {
		if (this.topSpeed < topSpeed) {
			this.topSpeed = topSpeed;
		}
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getFinishAddress() {
		return finishAddress;
	}

	public void setFinishAddress(String finishAddress) {
		this.finishAddress = finishAddress;
	}

	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	public Units getUnits() {
		return units;
	}

	public void setUnits(Units units) {
		this.units = units;
	}

	public int getBurnedCalories() {
		return burnedCalories;
	}

	public void setBurnedCalories(int burnedCalories) {
		this.burnedCalories = burnedCalories;
	}

	//	endregion

	@Override
	public String toString() {
		return "Workout{" +
				"id=" + id +
				", startDate='" + startDate + '\'' +
				", finishDate='" + finishDate + '\'' +
				", elapsedTime='" + elapsedTime + '\'' +
				", workoutType=" + workoutType + '\'' +
				", distance=" + distance + '\'' +
				", averageSpeed=" + averageSpeed + '\'' +
				", topSpeed=" + topSpeed + '\'' +
				", burnedCalories=" + burnedCalories + '\'' +
				", locationsList=" + locationsList +
				'}';
	}

	public boolean getFavourite() {
					return this.favourite;
	}

	/**
	 * To-many relationship, resolved on first access (and after reset).
	 * Changes to to-many relations are not persisted, make changes to the target entity.
	 */
	@Generated(hash = 992446055)
	public List<Position> getLocationsList() {
					if (locationsList == null) {
									final DaoSession daoSession = this.daoSession;
									if (daoSession == null) {
													throw new DaoException("Entity is detached from DAO context");
									}
									PositionDao targetDao = daoSession.getPositionDao();
									List<Position> locationsListNew = targetDao
																	._queryWorkout_LocationsList(id);
									synchronized (this) {
													if (locationsList == null) {
																	locationsList = locationsListNew;
													}
									}
					}
					return locationsList;
	}

	/** Resets a to-many relationship, making the next get call to query for a fresh result. */
	@Generated(hash = 178409376)
	public synchronized void resetLocationsList() {
					locationsList = null;
	}

	/**
	 * To-many relationship, resolved on first access (and after reset).
	 * Changes to to-many relations are not persisted, make changes to the target entity.
	 */
	@Generated(hash = 1532382398)
	public List<Speed> getSpeedsList() {
					if (speedsList == null) {
									final DaoSession daoSession = this.daoSession;
									if (daoSession == null) {
													throw new DaoException("Entity is detached from DAO context");
									}
									SpeedDao targetDao = daoSession.getSpeedDao();
									List<Speed> speedsListNew = targetDao._queryWorkout_SpeedsList(id);
									synchronized (this) {
													if (speedsList == null) {
																	speedsList = speedsListNew;
													}
									}
					}
					return speedsList;
	}

	/** Resets a to-many relationship, making the next get call to query for a fresh result. */
	@Generated(hash = 1483486642)
	public synchronized void resetSpeedsList() {
					speedsList = null;
	}

	/**
	 * To-many relationship, resolved on first access (and after reset).
	 * Changes to to-many relations are not persisted, make changes to the target entity.
	 */
	@Generated(hash = 424644441)
	public List<Distance> getDistancesList() {
					if (distancesList == null) {
									final DaoSession daoSession = this.daoSession;
									if (daoSession == null) {
													throw new DaoException("Entity is detached from DAO context");
									}
									DistanceDao targetDao = daoSession.getDistanceDao();
									List<Distance> distancesListNew = targetDao
																	._queryWorkout_DistancesList(id);
									synchronized (this) {
													if (distancesList == null) {
																	distancesList = distancesListNew;
													}
									}
					}
					return distancesList;
	}

	/** Resets a to-many relationship, making the next get call to query for a fresh result. */
	@Generated(hash = 519921783)
	public synchronized void resetDistancesList() {
					distancesList = null;
	}

	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 128553479)
	public void delete() {
					if (myDao == null) {
									throw new DaoException("Entity is detached from DAO context");
					}
					myDao.delete(this);
	}

	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 1942392019)
	public void refresh() {
					if (myDao == null) {
									throw new DaoException("Entity is detached from DAO context");
					}
					myDao.refresh(this);
	}

	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 713229351)
	public void update() {
					if (myDao == null) {
									throw new DaoException("Entity is detached from DAO context");
					}
					myDao.update(this);
	}

	/** called by internal mechanisms, do not call yourself. */
	@Generated(hash = 1398188052)
	public void __setDaoSession(DaoSession daoSession) {
					this.daoSession = daoSession;
					myDao = daoSession != null ? daoSession.getWorkoutDao() : null;
	}
}

package wojtek.pockettrainer.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * @author Wojtek Kolendo
 * @date 17.11.2016
 */

@Entity
public class Training {

	@Id(autoincrement = true)
	private Long id;

	private String title;

	@ToMany
	@JoinEntity(
			entity = JoinTrainingsWithTrainingActivities.class,
			sourceProperty = "trainingId",
			targetProperty = "trainingActivityId"
	)
	private List<TrainingActivity> trainingActivities;

	private boolean favourite;

	/** Used to resolve relations */
	@Generated(hash = 2040040024)
	private transient DaoSession daoSession;

	/** Used for active entity operations. */
	@Generated(hash = 811827863)
	private transient TrainingDao myDao;


	public Training(String title) {
		this.title = title;
		favourite = false;
	}

	@Generated(hash = 409535726)
	public Training(Long id, String title, boolean favourite) {
		this.id = id;
		this.title = title;
		this.favourite = favourite;
	}

	@Generated(hash = 1863741921)
	public Training() {
	}

	@Override
	public String toString() {
		return "TrainingActivity{" +
				"id=" + id +
				", title='" + title + '\'' +
				", trainingActivities='" + trainingActivities + '\'' +
				", favourite='" + favourite + '\'' +
				'}';
	}

	public Long getId() {
					return this.id;
	}

	public void setId(Long id) {
					this.id = id;
	}

	public String getTitle() {
					return this.title;
	}

	public void setTitle(String title) {
					this.title = title;
	}

	public boolean getFavourite() {
					return this.favourite;
	}

	public void setFavourite(boolean favourite) {
					this.favourite = favourite;
	}

	/**
	 * To-many relationship, resolved on first access (and after reset).
	 * Changes to to-many relations are not persisted, make changes to the target entity.
	 */
	@Generated(hash = 785367468)
	public List<TrainingActivity> getTrainingActivities() {
					if (trainingActivities == null) {
									final DaoSession daoSession = this.daoSession;
									if (daoSession == null) {
													throw new DaoException("Entity is detached from DAO context");
									}
									TrainingActivityDao targetDao = daoSession.getTrainingActivityDao();
									List<TrainingActivity> trainingActivitiesNew = targetDao
																	._queryTraining_TrainingActivities(id);
									synchronized (this) {
													if (trainingActivities == null) {
																	trainingActivities = trainingActivitiesNew;
													}
									}
					}
					return trainingActivities;
	}

	/** Resets a to-many relationship, making the next get call to query for a fresh result. */
	@Generated(hash = 1516952399)
	public synchronized void resetTrainingActivities() {
					trainingActivities = null;
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
	@Generated(hash = 1249407740)
	public void __setDaoSession(DaoSession daoSession) {
		this.daoSession = daoSession;
		myDao = daoSession != null ? daoSession.getTrainingDao() : null;
	}


}

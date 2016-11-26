package wojtek.pockettrainer.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Wojtek Kolendo
 * @date 26.11.2016
 */

@Entity
public class JoinTrainingsWithTrainingActivities {

	@Id
	private long id;

	private long trainingId;

	private long trainingActivityId;

	@Generated(hash = 144328762)
	public JoinTrainingsWithTrainingActivities(long id, long trainingId,
									long trainingActivityId) {
					this.id = id;
					this.trainingId = trainingId;
					this.trainingActivityId = trainingActivityId;
	}

	@Generated(hash = 399530170)
	public JoinTrainingsWithTrainingActivities() {
	}

	public long getId() {
					return this.id;
	}

	public void setId(long id) {
					this.id = id;
	}

	public long getTrainingId() {
					return this.trainingId;
	}

	public void setTrainingId(long trainingId) {
					this.trainingId = trainingId;
	}

	public long getTrainingActivityId() {
					return this.trainingActivityId;
	}

	public void setTrainingActivityId(long trainingActivityId) {
					this.trainingActivityId = trainingActivityId;
	}

}

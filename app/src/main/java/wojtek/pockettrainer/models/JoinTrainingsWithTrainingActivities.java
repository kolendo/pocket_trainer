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

	@Id(autoincrement = true)
	private Long id;

	private long trainingId;

	private long trainingActivityId;

	@Generated(hash = 229904011)
	public JoinTrainingsWithTrainingActivities(Long id, long trainingId,
			long trainingActivityId) {
		this.id = id;
		this.trainingId = trainingId;
		this.trainingActivityId = trainingActivityId;
	}

	@Generated(hash = 399530170)
	public JoinTrainingsWithTrainingActivities() {
	}

	public Long getId() {
					return this.id;
	}

	public void setId(Long id) {
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

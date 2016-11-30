package wojtek.pockettrainer.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Wojtek Kolendo
 * @date 26.11.2016
 */

@Entity
public class JoinWorkoutWithSpeeds {

	@Id(autoincrement = true)
	private Long id;

	private long workoutId;

	private long speedId;

	@Generated(hash = 93489027)
	public JoinWorkoutWithSpeeds(Long id, long workoutId, long speedId) {
					this.id = id;
					this.workoutId = workoutId;
					this.speedId = speedId;
	}

	@Generated(hash = 1849228240)
	public JoinWorkoutWithSpeeds() {
	}

	public Long getId() {
					return this.id;
	}

	public void setId(Long id) {
					this.id = id;
	}

	public long getWorkoutId() {
					return this.workoutId;
	}

	public void setWorkoutId(long workoutId) {
					this.workoutId = workoutId;
	}

	public long getSpeedId() {
					return this.speedId;
	}

	public void setSpeedId(long speedId) {
					this.speedId = speedId;
	}

}

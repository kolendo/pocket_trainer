package wojtek.pockettrainer.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author Wojtek Kolendo
 * @date 26.11.2016
 */

@Entity
public class JoinWorkoutWithPositions {

	@Id(autoincrement = true)
	private Long id;

	private long workoutId;

	private long positionId;

	@Generated(hash = 1911099959)
	public JoinWorkoutWithPositions(Long id, long workoutId, long positionId) {
					this.id = id;
					this.workoutId = workoutId;
					this.positionId = positionId;
	}

	@Generated(hash = 1738102148)
	public JoinWorkoutWithPositions() {
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

	public long getPositionId() {
					return this.positionId;
	}

	public void setPositionId(long positionId) {
					this.positionId = positionId;
	}

}

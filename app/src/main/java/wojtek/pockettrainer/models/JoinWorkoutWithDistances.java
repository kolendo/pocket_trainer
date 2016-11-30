package wojtek.pockettrainer.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Wojtek Kolendo
 * @date 26.11.2016
 */

@Entity
public class JoinWorkoutWithDistances {

	@Id(autoincrement = true)
	private Long id;

	private long workoutId;

	private long distanceId;

	@Generated(hash = 1114072309)
	public JoinWorkoutWithDistances(Long id, long workoutId, long distanceId) {
					this.id = id;
					this.workoutId = workoutId;
					this.distanceId = distanceId;
	}

	@Generated(hash = 1182214419)
	public JoinWorkoutWithDistances() {
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

	public long getDistanceId() {
					return this.distanceId;
	}

	public void setDistanceId(long distanceId) {
					this.distanceId = distanceId;
	}

}

package wojtek.pockettrainer.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * @author Wojtek Kolendo
 * @date 30.11.2016
 */

@Entity
public class Speed {

	@Id(autoincrement = true)
	private Long id;

	private long time;

	private double speed;

	public Speed(long time, double speed) {
		this.time = time;
		this.speed = speed;
	}

	@Generated(hash = 1191736634)
	public Speed(Long id, long time, double speed) {
					this.id = id;
					this.time = time;
					this.speed = speed;
	}

	@Generated(hash = 1069754329)
	public Speed() {
	}

	@Override
	public String toString() {
		return "Speed{" +
				"id=" + id +
				", time='" + time +
				", speed='" + speed +
				'}';
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Long getId() {
					return this.id;
	}

	public void setId(Long id) {
					this.id = id;
	}
}

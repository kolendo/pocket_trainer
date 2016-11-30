package wojtek.pockettrainer.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * @author Wojtek Kolendo
 * @date 30.11.2016
 */

@Entity
public class Distance {

	@Id(autoincrement = true)
	private Long id;

	private long time;

	private double distance;

	public Distance(long time, double distance) {
		this.time = time;
		this.distance = distance;
	}

	@Generated(hash = 1873494747)
	public Distance(Long id, long time, double distance) {
					this.id = id;
					this.time = time;
					this.distance = distance;
	}

	@Generated(hash = 2139379519)
	public Distance() {
	}

	@Override
	public String toString() {
		return "Distance{" +
				"id=" + id +
				", time='" + time +
				", distance='" + distance +
				'}';
	}

	public Long getId() {
					return this.id;
	}

	public void setId(Long id) {
					this.id = id;
	}

	public long getTime() {
					return this.time;
	}

	public void setTime(long time) {
					this.time = time;
	}

	public double getDistance() {
					return this.distance;
	}

	public void setDistance(double distance) {
					this.distance = distance;
	}
}

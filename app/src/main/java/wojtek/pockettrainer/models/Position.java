package wojtek.pockettrainer.models;

import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * @author Wojtek Kolendo
 * @date 01.10.2016
 */

@Entity
public class Position {

	@Id(autoincrement = true)
	private Long id;

	private double latitude;

	private double longitude;

	private double altitude;

	public Position() {
	}

	public Position(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		altitude = location.getAltitude();
	}

	@Generated(hash = 46318097)
	public Position(Long id, double latitude, double longitude, double altitude) {
					this.id = id;
					this.latitude = latitude;
					this.longitude = longitude;
					this.altitude = altitude;
	}

	public LatLng getLatLng() {
		return new LatLng(latitude, longitude);
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	@Override
	public String toString() {
		return "Position{" +
				"latitude=" + latitude +
				", longitude='" + longitude +
				", altitude='" + altitude +
				'}';
	}

	public Long getId() {
					return this.id;
	}

	public void setId(Long id) {
					this.id = id;
	}
}

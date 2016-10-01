package wojtek.pockettrainer.models;

import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * @author Wojtek Kolendo
 * @date 01.10.2016
 */

public class Position implements Serializable {

	private double mLatitude;

	private double mLongitude;

	private double mAltitude;

	public Position() {
	}

	public Position(Location location) {
		mLatitude = location.getLatitude();
		mLongitude = location.getLongitude();
		mAltitude = location.getAltitude();
	}

	@NonNull
	public LatLng getLatLng() {
		return new LatLng(mLatitude, mLongitude);
	}

	public double getLatitude() {
		return mLatitude;
	}

	public void setLatitude(double latitude) {
		mLatitude = latitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public void setLongitude(double longitude) {
		mLongitude = longitude;
	}

	public double getAltitude() {
		return mAltitude;
	}

	public void setAltitude(double altitude) {
		mAltitude = altitude;
	}

	@Override
	public String toString() {
		return "Position{" +
				"mLatitude=" + mLatitude +
				", mLongitude='" + mLongitude +
				", mAltitude='" + mAltitude +
				'}';
	}
}

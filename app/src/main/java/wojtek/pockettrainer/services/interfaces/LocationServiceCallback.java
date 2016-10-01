package wojtek.pockettrainer.services.interfaces;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import wojtek.pockettrainer.models.enums.WorkoutType;

/**
 * @author Wojtek Kolendo
 * @date 27.09.2016
 */

public interface LocationServiceCallback {

	void passDataMeters(LatLng latLng, double totalDistance, double currentSpeed);

	void passDataKilometers(LatLng latLng, double totalDistance, double currentSpeed);

	void passLocation(LatLng latLng);

	boolean checkGpsPermission();

	WorkoutType getWorkoutType();
}

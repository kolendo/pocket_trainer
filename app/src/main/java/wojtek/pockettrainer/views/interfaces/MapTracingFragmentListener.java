package wojtek.pockettrainer.views.interfaces;


import com.google.android.gms.maps.model.LatLng;

/**
 * @author Wojtek Kolendo
 * @date 29.09.2016
 */

public interface MapTracingFragmentListener {

	void setDataViewMeters(LatLng latLng, double totalDistance, double currentSpeed);

	void setDataViewKilometers(LatLng latLng, double totalDistance, double currentSpeed);

	void setLocation(LatLng latLng);
}

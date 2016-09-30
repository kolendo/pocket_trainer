package wojtek.pockettrainer.views.interfaces;

import android.location.Location;

/**
 * @author Wojtek Kolendo
 * @date 29.09.2016
 */

public interface MapTracingFragmentListener {

	void receiveData(double totalDistance, double currentSpeed);

}

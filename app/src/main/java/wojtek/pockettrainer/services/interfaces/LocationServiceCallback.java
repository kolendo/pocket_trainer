package wojtek.pockettrainer.services.interfaces;

import android.location.Location;

/**
 * @author Wojtek Kolendo
 * @date 27.09.2016
 */

public interface LocationServiceCallback {

	void passData(double totalDistance, double currentSpeed);

	boolean checkGpsPermission();
}

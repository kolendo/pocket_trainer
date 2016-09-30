package wojtek.pockettrainer.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.rafalzajfert.androidlogger.Logger;

import java.util.ArrayList;
import java.util.Locale;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.services.interfaces.LocationServiceCallback;

/**
 * @author Wojtek Kolendo
 * @date 18.09.2016
 */
public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener, LocationListener {

	//	minimalny dystans pomiędzy dwoma następującymi lokacjami do wzięcia pod uwagę (w metrach)
	private static final int MIN_DISTANCE = 5;
	//	maksymalny dystans pomiędzy dwoma następującymi lokacjami do wzięcia pod uwagę (w metrach)
	private static final int MAX_DISTANCE = 100;

	public static final int UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
	public static final int FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 5;

	private final IBinder mBinder = new LocalBinder();
	private GoogleApiClient mGoogleApiClient;
	private LocationRequest mLocationRequest;
	boolean mRequestingLocationUpdates = false;
	private LocationServiceCallback mLocationServiceCallback;

//	Workout Data
	private ArrayList<Location> mLocationsList;
	private ArrayList<Double> mSpeedsList;
	private double mTotalDistance, mCurrentDistance, mCurrentSpeed, mTopSpeed;
	private Location mCurrentLocation, mLastLocation;

	public LocationService() {
		mLocationsList = new ArrayList<>();
		mSpeedsList = new ArrayList<>();
		mTotalDistance = 0;
		mTopSpeed = 0;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		if (mGoogleApiClient == null) {
			mGoogleApiClient = new GoogleApiClient.Builder(this)
					.addConnectionCallbacks(this)
					.addOnConnectionFailedListener(this)
					.addApi(LocationServices.API)
					.build();
		}

		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}

	public void setCallback(LocationServiceCallback callback) {
		mLocationServiceCallback = callback;
	}

	private void startGoogleClient() {
		if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {

			mGoogleApiClient = new GoogleApiClient.Builder(this)
					.addConnectionCallbacks(this)
					.addOnConnectionFailedListener(this)
					.addApi(LocationServices.API)
					.build();

			if (!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		} else {
			Logger.debug("unable to connect to google play services.");
		}
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		if (!mRequestingLocationUpdates) {
			startGoogleClient();
		}
		return mBinder;
	}


	@Override
	public void onConnected(@Nullable Bundle bundle) {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		startLocationUpdates();
	}

	public void startLocationUpdates() {
		if (mLocationServiceCallback.checkGpsPermission()) {
			mRequestingLocationUpdates = true;
			LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest, LocationService.this);
		} else {
			Toast.makeText(getApplicationContext(), R.string.no_location_permission, Toast.LENGTH_SHORT).show();
		}
	}

	public void stopLocationUpdates() {
		mRequestingLocationUpdates = false;
		LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
	}

	public Location getLastLocation() {
		if (mLocationServiceCallback.checkGpsPermission()) {
			return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
		} else {
			Toast.makeText(getApplicationContext(), R.string.no_location_permission, Toast.LENGTH_SHORT).show();
			return null;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		mLocationsList.add(location);
		Logger.debug(mLocationsList.size());
		Logger.debug(location.toString());

		if (mCurrentLocation != null) {
			mLastLocation = mCurrentLocation;
			mCurrentLocation = location;
			setAndSendData();
		} else {
			mCurrentLocation = location;
		}
	}

	private void setAndSendData() {
		mCurrentDistance = mCurrentLocation.distanceTo(mLastLocation);
		if (mCurrentDistance > MIN_DISTANCE && mCurrentDistance < MAX_DISTANCE) {
			mTotalDistance += mCurrentDistance;
			mCurrentSpeed = mCurrentDistance / ((mCurrentLocation.getTime() - mLastLocation.getTime()) / 1000);
			if (mCurrentSpeed > mTopSpeed) {
				mTopSpeed = mCurrentSpeed;
			}
			mSpeedsList.add(mCurrentSpeed);
			mLocationServiceCallback.passData(mTotalDistance, mCurrentSpeed);
		} else {
			mSpeedsList.add(0.0);
			mLocationServiceCallback.passData(mTotalDistance, 0.0);
		}
	}


	@Override
	public void onConnectionSuspended(int i) {
		Toast.makeText(this, R.string.locations_services_suspend, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		Toast.makeText(this, R.string.locations_services_fail, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroy() {
		mGoogleApiClient.disconnect();
		super.onDestroy();
	}

	public class LocalBinder extends Binder {
		public LocationService getService() {
			// Return this instance of LocalService so clients can call public methods
			return LocationService.this;
		}
	}
}

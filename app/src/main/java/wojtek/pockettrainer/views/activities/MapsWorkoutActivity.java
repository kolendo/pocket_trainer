package wojtek.pockettrainer.views.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.views.fragments.MapTracingFragment;

/**
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */
public class MapsWorkoutActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
		com.google.android.gms.location.LocationListener, ResultCallback<LocationSettingsResult> {

	private static final int FRAGMENT_CONTAINER = R.id.fragment_frame_map;
	public static final String EXTRA_WORKOUT = "extra_workout";
	private static final String WORKOUT_KEY = "workout_key";
	public static final String EXTRA_LOCATION = "extra_location";

	public static final int REQUEST_CHECK_SETTINGS = 100;
	public static final int UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
	public static final int FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 5;

	private AppBarLayout mAppBarLayout;
	private Workout mWorkout;
	private GoogleApiClient mGoogleApiClient;
	Location mLocation;
	LocationRequest mLocationRequest;
	boolean mRequestingLocationUpdates = true;
	LocationSettingsRequest.Builder gpsBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_workout);
		initWorkout(savedInstanceState);
		initToolbar();

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

		if (savedInstanceState == null) {
			changeFragment(MapTracingFragment.newInstance(mWorkout), false);
		}
	}

	private void initWorkout(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mWorkout = (Workout) savedInstanceState.getSerializable(WORKOUT_KEY);
		} else {
			mWorkout = (Workout) getIntent().getSerializableExtra(EXTRA_WORKOUT);
		}
		if (mWorkout == null) {
			throw new IllegalArgumentException(MapsWorkoutActivity.class.getSimpleName() + " must be started with EXTRA_WORKOUT argument");
		}
	}

	private void initToolbar() {
		mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_map);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_map);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowHomeEnabled(true);
		}
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		setTitle(getResources().getText(R.string.new_whitespace) + mWorkout.getWorkoutType().toString());
	}


	public void changeFragment(final Fragment fragment, boolean useDelay) {
		final FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment oldFragment = fragmentManager.findFragmentById(FRAGMENT_CONTAINER);
		if (oldFragment != null) {
			fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
					.remove(oldFragment)
					.commit();
		}
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				fragmentManager
						.beginTransaction()
						.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
						.add(FRAGMENT_CONTAINER, fragment)
						.commit();
			}
		}, useDelay ? 500 : 0);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!mGoogleApiClient.isConnected()) {
			mGoogleApiClient.connect();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
//			startLocationUpdates();
		}
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		gpsBuilder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
		gpsBuilder.setAlwaysShow(true);
		PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, gpsBuilder.build());
		result.setResultCallback(this);
		if (mRequestingLocationUpdates) {
			startLocationUpdates();
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
	public void onLocationChanged(Location location) {
		mLocation = location;
		Log.d("location", mLocation.toString());
	}

	private void startLocationUpdates() {
		if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
		}
	}

	private void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
	}

	public void switchUpdates() {
		mRequestingLocationUpdates = !mRequestingLocationUpdates;
		if (mRequestingLocationUpdates) {
			startLocationUpdates();
		}
		else{
			stopLocationUpdates();
		}
	}

	@Override
	public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
		final Status status = locationSettingsResult.getStatus();
		switch (status.getStatusCode()) {
			case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
				try {
					status.startResolutionForResult(MapsWorkoutActivity.this, REQUEST_CHECK_SETTINGS);
				} catch (IntentSender.SendIntentException e) {
					Log.d("Exception", e.toString());
				}
				break;
			case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
				Toast.makeText(getApplicationContext(), R.string.locations_fail, Toast.LENGTH_LONG).show();
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CHECK_SETTINGS) {
			if (resultCode == RESULT_OK) {
			} else {
				finish();
			}
		}
	}

	@Override
	public void onBackPressed() {
		cancelWorkoutAlertDialog();
	}

	private void cancelWorkoutAlertDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage(R.string.cancel_workout)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						})
				.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	@Override
	protected void onDestroy() {
		mGoogleApiClient.disconnect();
		super.onDestroy();
	}
}

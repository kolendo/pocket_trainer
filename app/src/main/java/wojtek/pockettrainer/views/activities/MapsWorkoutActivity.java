package wojtek.pockettrainer.views.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.models.enums.WorkoutType;
import wojtek.pockettrainer.services.LocationService;
import wojtek.pockettrainer.services.interfaces.LocationServiceCallback;
import wojtek.pockettrainer.views.fragments.WorkoutDetailsFragment;
import wojtek.pockettrainer.views.fragments.WorkoutTracingFragment;
import wojtek.pockettrainer.views.interfaces.MapTracingFragmentListener;
import wojtek.pockettrainer.views.interfaces.MapsWorkoutActivityListener;


/**
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */
public class MapsWorkoutActivity extends AppCompatActivity implements LocationServiceCallback, MapsWorkoutActivityListener {

	private static final int FRAGMENT_CONTAINER = R.id.fragment_frame_map;
	public static final String EXTRA_WORKOUT = "extra_workout";

	MapTracingFragmentListener mFragmentListener;
	LocationService mService;
	boolean mBound = false;
	private Workout mWorkout;
	View mLoadingView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_workout);
		mLoadingView = findViewById(R.id.loading_foreground);
		initWorkout();
		initToolbar();
		animateLoading(true);

		if (savedInstanceState == null) {
			changeFragment(WorkoutTracingFragment.newInstance(), false);
		}
		Intent intent = new Intent(this, LocationService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		unbindService(mConnection);
		stopService(new Intent(this, LocationService.class));
		super.onDestroy();
	}

	/** Defines callbacks for service binding, passed to bindService() */
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
			mService = binder.getService();
			mBound = true;
			mService.setCallback(MapsWorkoutActivity.this);
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mBound = false;
		}
	};

	private void initWorkout() {
		mWorkout = (Workout) getIntent().getSerializableExtra(EXTRA_WORKOUT);
		if (mWorkout == null) {
			throw new IllegalArgumentException(MapsWorkoutActivity.class.getSimpleName() + " must be started with EXTRA_WORKOUT argument");
		} else {
			mWorkout.setStartDate(System.currentTimeMillis());
		}
	}

	private void initToolbar() {
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
		setTitle(getResources().getText(R.string.new_with_whitespace) + mWorkout.getWorkoutType().toString());
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
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);

		if (fragment instanceof WorkoutTracingFragment) {
			mFragmentListener = (MapTracingFragmentListener) fragment;
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
								stopService(new Intent(getApplicationContext(), LocationService.class));
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

	private void finishWorkoutAlertDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage(R.string.finish_workout)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						prepareWorkoutResult();
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
	public void passDataMeters(LatLng latLng, double totalDistance, double currentSpeed) {
		mFragmentListener.setDataViewMeters(latLng, totalDistance, currentSpeed);
	}

	@Override
	public void passDataKilometers(LatLng latLng, double totalDistance, double currentSpeed) {
		mFragmentListener.setDataViewKilometers(latLng, totalDistance, currentSpeed);
	}

	@Override
	public void passLocation(LatLng latLng) {
		mFragmentListener.setLocation(latLng);
	}

	@Override
	public void stopLocationUpdates() {
		mService.stopLocationUpdates();
	}

	@Override
	public void startLocationUpdates() {
		mService.startLocationUpdates();
	}

	@Override
	public void finishWorkout() {
		finishWorkoutAlertDialog();
	}

	@Override
	public Workout getWorkout() {
		return mWorkout;
	}

	@Override
	public boolean checkGpsPermission() {
		return ActivityCompat.checkSelfPermission(MapsWorkoutActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
	}

	@Override
	public WorkoutType getWorkoutType() {
		return mWorkout.getWorkoutType();
	}

	@Override
	public void animateLoading(boolean animate) {
		if (animate) {
			mLoadingView.setVisibility(View.VISIBLE);
		} else {
			mLoadingView.setVisibility(View.GONE);
		}
	}

	@Override
	public void showBottomSheet() {
		mFragmentListener.showBottomSheet(true);
	}

	private void prepareWorkoutResult() {

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mFragmentListener.showBottomSheet(false);
				animateLoading(true);
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);
				if (getSupportActionBar() != null) {
					getSupportActionBar().setDisplayHomeAsUpEnabled(false);
					setTitle(R.string.workout_summary);
				}
				changeFragment(WorkoutDetailsFragment.newInstance(mWorkout), true);
				animateLoading(false);
			}

			@Override
			protected Void doInBackground(Void... params) {
				stopLocationUpdates();
				mWorkout.setFinishDate(System.currentTimeMillis());
				mWorkout.setElapsedTime(mFragmentListener.getElapsedTime());
				mWorkout.setLocationsList(mService.getLocationsList());
				mWorkout.setSpeedsHashMap(mService.getSpeedsHashMap());
				mWorkout.setDistancesHashMap(mService.getDistancesHashMap());
				mWorkout.setTopSpeed(mService.getTopSpeed());
				mWorkout.setAverageSpeed(mService.getAverageSpeed());
				mWorkout.setAverageSpeedWithoutStops(mService.getAverageSpeedWithoutStops());
				mWorkout.setDistance(mService.getTotalDistance());
				mWorkout.setStartAddress(getLocationAddress(mWorkout.getLocation(0).getLatitude(), mWorkout.getLocation(0).getLongitude()));
				mWorkout.setFinishAddress(getLocationAddress(mWorkout.getLocation(mWorkout.getLocationsListLastIndex()).getLatitude(),
						mWorkout.getLocation(mWorkout.getLocationsListLastIndex()).getLongitude()));
				mWorkout.setBurnedCalories(mService.getBurnedCalories());
				return null;
			}
		}.execute();

	}

	private String getLocationAddress(double latitude, double longitude) {
		Geocoder geocoder;
		List<Address> addresses;
		String address = "";
		geocoder = new Geocoder(this, Locale.getDefault());

		try {
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
			if (addresses.get(0).getAddressLine(0) != null && !addresses.get(0).getAddressLine(0).isEmpty()) {
				address += addresses.get(0).getAddressLine(0) + " ";
			}
			if (addresses.get(0).getPostalCode() != null && !addresses.get(0).getPostalCode().isEmpty()) {
				address += addresses.get(0).getPostalCode() + " ";
			}
			if (addresses.get(0).getLocality() != null && !addresses.get(0).getLocality().isEmpty()) {
				address += addresses.get(0).getLocality() + " ";
			}
			if (addresses.get(0).getCountryName() != null && !addresses.get(0).getCountryName().isEmpty()) {
				address += addresses.get(0).getCountryName() + " ";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return address;
	}
}

package wojtek.pockettrainer.views.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
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

import com.rafalzajfert.androidlogger.Logger;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.services.LocationService;
import wojtek.pockettrainer.services.interfaces.LocationServiceCallback;
import wojtek.pockettrainer.views.fragments.MapTracingFragment;
import wojtek.pockettrainer.views.interfaces.MapTracingFragmentListener;
import wojtek.pockettrainer.views.interfaces.MapsWorkoutActivityListener;


/**
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */
public class MapsWorkoutActivity extends AppCompatActivity implements LocationServiceCallback, MapsWorkoutActivityListener {

	private static final int FRAGMENT_CONTAINER = R.id.fragment_frame_map;
	public static final String EXTRA_WORKOUT = "extra_workout";

	MapTracingFragmentListener mMapTracingFragmentListener;
	LocationService mService;
	boolean mBound = false;
	private Workout mWorkout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_workout);
		initWorkout();
		initToolbar();

		if (savedInstanceState == null) {
			changeFragment(MapTracingFragment.newInstance(mWorkout), false);
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

		mMapTracingFragmentListener = (MapTracingFragmentListener) fragment;
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

	@Override
	public void passData(double totalDistance, double currentSpeed) {
		mMapTracingFragmentListener.receiveData(totalDistance, currentSpeed);
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
	public void finishWorkout(Workout workout) {
		finishWorkoutAlertDialog();
	}

	@Override
	public boolean checkGpsPermission() {
		return ActivityCompat.checkSelfPermission(MapsWorkoutActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
	}
}

package wojtek.pockettrainer.views.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.rafalzajfert.androidlogger.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.views.interfaces.MapTracingFragmentListener;
import wojtek.pockettrainer.views.interfaces.MapsWorkoutActivityListener;


/**
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */
public class MapTracingFragment extends Fragment implements OnMapReadyCallback, MapTracingFragmentListener {

	private static final String WORKOUT_KEY = "workout_key";

	MapsWorkoutActivityListener mActivityListener;
	private GoogleMap mGoogleMap;
	private MapView mMapView;
	private View mBottomSheetView;
	private BottomSheetBehavior mBottomSheetBehavior;
	private Workout mWorkout;
	private ArrayList<Location> mLocationsList;
	private Location mCurrentLocation, mLastLocation;
	private long mTime, mTotalTime;
	private CountDownTimer mTimer;
	private TextView mTimeTextView, mDistanceTextView, mSpeedTextView;

	public static MapTracingFragment newInstance(Workout workout) {
		Bundle args = new Bundle();
		args.putSerializable(WORKOUT_KEY, workout);
		MapTracingFragment fragment = new MapTracingFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
			mWorkout = (Workout) args.getSerializable(WORKOUT_KEY);
			mWorkout.setStartDate(System.currentTimeMillis());
		}
		Logger.debug(mWorkout.toString());
		mLocationsList = new ArrayList<>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map_tracing, container, false);
		mMapView = (MapView) view.findViewById(R.id.map_view);
		mMapView.onCreate(savedInstanceState);
		mMapView.getMapAsync(this);
		mBottomSheetView = view.findViewById(R.id.map_bottom_sheet);
		mTimeTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_time);
		mDistanceTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_route);
		mSpeedTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_speed);
		TextView resumeTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_resume);
		TextView pauseTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_pause);
		TextView stopTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_stop);

		mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheetView);
		resumeTextView.setOnClickListener(mResumeOnClickListener);
		pauseTextView.setOnClickListener(mPauseOnClickListener);
		stopTextView.setOnClickListener(mStopOnClickListener);

		setBottomSheetPeek();
		startupTimer();

		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			mActivityListener = (MapsWorkoutActivityListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() + " must implement MapsWorkoutActivityListener");
		}
	}

	View.OnClickListener mResumeOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mTimer == null) {
				Toast.makeText(getContext(), R.string.workout_resumed, Toast.LENGTH_SHORT).show();
				startupTimer();
				mActivityListener.startLocationUpdates();
			}
		}
	};

	View.OnClickListener mPauseOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mTimer != null) {
				Toast.makeText(getContext(), R.string.workout_paused, Toast.LENGTH_SHORT).show();
				finishTimer();
				mActivityListener.stopLocationUpdates();
			}
		}
	};

	View.OnClickListener mStopOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mActivityListener.finishWorkout(mWorkout);
		}
	};

	@Override
	public void onResume() {
		if (mMapView != null) {
			mMapView.onResume();
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		if (mMapView != null) {
			mMapView.onPause();
		}
		super.onPause();
	}

	@Override
	public void onDestroy() {
		if (mMapView != null) {
			try {
				mMapView.onDestroy();
			} catch (NullPointerException e) {
				Logger.error(e);
			}
		}
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		if (mMapView != null) {
			mMapView.onSaveInstanceState(outState);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if (mMapView != null) {
			mMapView.onLowMemory();
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mGoogleMap = googleMap;

		if (checkGpsPermission()) {
			mGoogleMap.setMyLocationEnabled(true);
			mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
		}
		mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
		mGoogleMap.getUiSettings().setCompassEnabled(true);
		mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

	}

	private boolean checkGpsPermission() {
		return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
	}

	private void setBottomSheetPeek() {
		mBottomSheetView.post(new Runnable() {
			@Override
			public void run() {
				mBottomSheetBehavior.setPeekHeight(mTimeTextView.getHeight());
				mGoogleMap.setPadding(0, 0, 0, mTimeTextView.getHeight());
			}
		});
	}

	private void startupTimer() {
		mTimer = new CountDownTimer(Integer.MAX_VALUE, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				mTime = Integer.MAX_VALUE - millisUntilFinished;
				setTimeTextView(mTotalTime + mTime);
			}

			@Override
			public void onFinish() {
			}

		}.start();
	}

	private void finishTimer() {
		mTimer.cancel();
		mTimer = null;
		mTotalTime += mTime;
	}

	private void setTimeTextView(long milliseconds) {
		String timestamp = String.format(Locale.getDefault(), "%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(milliseconds),
				TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1),
				TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1));
		mTimeTextView.setText(timestamp);
	}

	@Override
	public void receiveLocation(Location location) {
		if (mWorkout != null) {
			mWorkout.addLocationsList(location);
			Logger.debug(mWorkout.getLocationsList().size());
			Logger.debug(location.toString());
			if (mCurrentLocation != null) {
				mLastLocation = mCurrentLocation;
				mCurrentLocation = location;
				setCurrentDistance();
			} else {
				mCurrentLocation = location;
			}
		}
	}

	private void setCurrentDistance() {
		double distance = mCurrentLocation.distanceTo(mLastLocation);
		mWorkout.addDistance(distance);
		mDistanceTextView.setText(round(distance, 2) + " m");
		setCurrentSpeed(distance);
		Logger.debug(distance);
	}

	private void setCurrentSpeed(double distance) {
		if (distance > 0.0) {
			double speed = distance / (mCurrentLocation.getTime() - mLastLocation.getTime()) * 1000;
			Logger.debug(speed);
			mWorkout.setTopSpeed(speed);
			mSpeedTextView.setText(round(speed, 2) + " m/s");
		} else {
			mSpeedTextView.setText(R.string.blank_speed);
		}
	}

	private double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
	}
}

package wojtek.pockettrainer.views.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rafalzajfert.androidlogger.Logger;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.views.interfaces.MapTracingFragmentListener;
import wojtek.pockettrainer.views.interfaces.MapsWorkoutActivityListener;


/**
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */
public class WorkoutTracingFragment extends Fragment implements OnMapReadyCallback, MapTracingFragmentListener {

	MapsWorkoutActivityListener mActivityListener;
	private GoogleMap mGoogleMap;
	private MapView mMapView;
	private View mBottomSheetView, mBottomSheetHeaderView;
	private BottomSheetBehavior mBottomSheetBehavior;
	private long mTime, mTotalTime;
	private CountDownTimer mTimer;
	private TextView mTimeTextView, mDistanceTextView, mSpeedTextView;
	private Marker mLocationMarker;

	public static WorkoutTracingFragment newInstance() {
		return new WorkoutTracingFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_workout_tracing, container, false);
		mMapView = (MapView) view.findViewById(R.id.map_view);
		mMapView.onCreate(savedInstanceState);
		mMapView.getMapAsync(this);
		mBottomSheetView = view.findViewById(R.id.map_bottom_sheet);
		mBottomSheetHeaderView = view.findViewById(R.id.map_bottom_sheet_header);
		mTimeTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_time);
		mDistanceTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_route);
		mSpeedTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_speed);
		View resumeTextView = view.findViewById(R.id.map_bottom_sheet_resume);
		View pauseTextView = view.findViewById(R.id.map_bottom_sheet_pause);
		View stopTextView = view.findViewById(R.id.map_bottom_sheet_stop);

		mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheetView);
		resumeTextView.setOnClickListener(onResumeOnClickListener);
		pauseTextView.setOnClickListener(onPauseOnClickListener);
		stopTextView.setOnClickListener(onStopOnClickListener);

		setBottomSheetPeek();
//		initDataView();
		startupTimer();

		return view;
	}

	private void initDataView() {
		switch (mActivityListener.getWorkout().getWorkoutType()) {
			case CYCLING:
				mDistanceTextView.setText(R.string.blank_distance_km);
				mSpeedTextView.setText(R.string.blank_speed_km);
				break;
			case RUNNING:
				mDistanceTextView.setText(R.string.blank_distance_m);
				mSpeedTextView.setText(R.string.blank_speed_m);
				break;
		}
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

	View.OnClickListener onResumeOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mTimer == null) {
				Toast.makeText(getContext(), R.string.workout_resumed, Toast.LENGTH_SHORT).show();
				startupTimer();
				mActivityListener.startLocationUpdates();
			}
		}
	};

	View.OnClickListener onPauseOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mTimer != null) {
				Toast.makeText(getContext(), R.string.workout_paused, Toast.LENGTH_SHORT).show();
				finishTimer();
				mActivityListener.stopLocationUpdates();
			}
		}
	};

	View.OnClickListener onStopOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mActivityListener.finishWorkout();
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
				mBottomSheetBehavior.setPeekHeight(mBottomSheetHeaderView.getHeight());
				mGoogleMap.setPadding(0, 0, 0, mBottomSheetHeaderView.getHeight());
			}
		});
	}

	private void startupTimer() {
		mTimer = new CountDownTimer(Integer.MAX_VALUE, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				mTime = Integer.MAX_VALUE - millisUntilFinished;
				mTimeTextView.setText(setTimeTextView(mTotalTime + mTime));
			}

			@Override
			public void onFinish() {
			}

		}.start();
	}

	public void finishTimer() {
		mTimer.cancel();
		mTimer = null;
		mTotalTime += mTime;
	}

	@Override
	public String getElapsedTime() {
		finishTimer();
		return setTimeTextView(mTotalTime);
	}

	private String setTimeTextView(long milliseconds) {
		return String.format(Locale.getDefault(), "%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(milliseconds),
				TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1),
				TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1));
	}

	@Override
	public void setDataViewMeters(LatLng latLng, double totalDistance, double currentSpeed) {
		mDistanceTextView.setText(String.format(getResources().getString(R.string.distance_units_m), totalDistance));
		mSpeedTextView.setText(String.format(getResources().getString(R.string.speed_units_mps), currentSpeed));
		setMyLocationMarker(latLng, 19);
	}

	@Override
	public void setDataViewKilometers(LatLng latLng, double totalDistance, double currentSpeed) {
		mDistanceTextView.setText(String.format(getResources().getString(R.string.distance_units_km), totalDistance));
		mSpeedTextView.setText(String.format(getResources().getString(R.string.speed_units_kph), currentSpeed));
		setMyLocationMarker(latLng, 17);
	}

	@Override
	public void setLocation(LatLng latLng) {
		setMyLocationMarker(latLng, 17);
	}

	private void setMyLocationMarker(LatLng latLng, int zoom) {
		if (isMapReady()) {
			if (mLocationMarker != null) {
				mLocationMarker.setPosition(latLng);
				moveCamera(latLng, mGoogleMap.getCameraPosition().zoom);
			} else {
				mLocationMarker = mGoogleMap.addMarker(new MarkerOptions()
						.position(latLng)
						.icon(getMarkerBitmapDescriptor()));
				moveCamera(latLng, zoom);
			}
		}
	}

	private void moveCamera(LatLng latLng, float zoom) {
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
		mGoogleMap.animateCamera(cameraUpdate);
	}

	private BitmapDescriptor getMarkerBitmapDescriptor() {
		Drawable vectorDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_my_location_accent_24dp);
		int h = vectorDrawable.getIntrinsicHeight();
		int w = vectorDrawable.getIntrinsicWidth();
		vectorDrawable.setBounds(0, 0, w, h);
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bm);
		vectorDrawable.draw(canvas);
		return BitmapDescriptorFactory.fromBitmap(bm);
	}

	private boolean isMapReady() {
		return mGoogleMap != null;
	}
}

package wojtek.pockettrainer.views.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.Workout;


/**
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */
public class MapTracingFragment extends Fragment implements OnMapReadyCallback {

	private static final String WORKOUT_KEY = "workout_key";

	private GoogleMap mGoogleMap;
	private MapView mMapView;
	private View mBottomSheetView;
	private BottomSheetBehavior mBottomSheetBehavior;
	private Workout mWorkout;
	private Location mCurrentLocation, mLastLocation;

	private TextView mTimeTextView, mRouteTextView, mSpeedTextView, mResumeTextView, mPauseTextView, mStopTextView;

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
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map_tracing, container, false);
		mMapView = (MapView) view.findViewById(R.id.map_view);
		mMapView.onCreate(savedInstanceState);
		mMapView.getMapAsync(this);
		mBottomSheetView = view.findViewById(R.id.map_bottom_sheet);
		mTimeTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_time);
		mRouteTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_route);
		mSpeedTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_speed);
		mResumeTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_resume);
		mPauseTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_pause);
		mStopTextView = (TextView) view.findViewById(R.id.map_bottom_sheet_stop);

		mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheetView);
		setBottomSheetPeek();
		mResumeTextView.setOnClickListener(mResumeOnClickListener);
		mPauseTextView.setOnClickListener(mPauseOnClickListener);
		mStopTextView.setOnClickListener(mStopOnClickListener);
		return view;
	}

	View.OnClickListener mResumeOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getContext(), "not implemented", Toast.LENGTH_SHORT).show();
		}
	};

	View.OnClickListener mPauseOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getContext(), "not implemented", Toast.LENGTH_SHORT).show();
		}
	};

	View.OnClickListener mStopOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getContext(), "not implemented", Toast.LENGTH_SHORT).show();
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


}

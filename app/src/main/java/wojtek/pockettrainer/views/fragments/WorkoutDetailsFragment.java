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
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
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
public class WorkoutDetailsFragment extends Fragment implements OnMapReadyCallback {

	private static final String WORKOUT_KEY = "workout_key";

	MapsWorkoutActivityListener mActivityListener;
	private GoogleMap mGoogleMap;
	private MapView mMapView;
	private Workout mWorkout;

	public static WorkoutDetailsFragment newInstance(Workout workout) {
		Bundle args = new Bundle();
		args.putSerializable(WORKOUT_KEY, workout);
		WorkoutDetailsFragment fragment = new WorkoutDetailsFragment();
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_workout_details, container, false);
		mMapView = (MapView) view.findViewById(R.id.map_view_workout_details);
		mMapView.onCreate(savedInstanceState);
		mMapView.getMapAsync(this);

		TextView startTimeTextView = (TextView) view.findViewById(R.id.workout_details_start_time);
		TextView startLocationTextView = (TextView) view.findViewById(R.id.workout_details_start_location);
		TextView finishTimeTextView = (TextView) view.findViewById(R.id.workout_details_finish_time);
		TextView finishLocationTextView = (TextView) view.findViewById(R.id.workout_details_finish_location);
		TextView elapsedTimeTextView = (TextView) view.findViewById(R.id.workout_details_elapsed_time);
		TextView totalDistanceTextView = (TextView) view.findViewById(R.id.workout_details_total_distance);
		TextView averageSpeedTextView = (TextView) view.findViewById(R.id.workout_details_average_speed);
		TextView topSpeedTextView = (TextView) view.findViewById(R.id.workout_details_top_speed);
		TextView burnedCaloriesTextView = (TextView) view.findViewById(R.id.workout_details_burned_calories);
		ImageView backgroundImageView = (ImageView) view.findViewById(R.id.workout_details_faded_background);

		startTimeTextView.setText(new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a").format(mWorkout.getStartDate().getTime()));
		startLocationTextView.setText("Januszowo 34");
		finishTimeTextView.setText(new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a").format(mWorkout.getFinishDate().getTime()));
		finishLocationTextView.setText("Aaaaa");
		elapsedTimeTextView.setText(mWorkout.getElapsedTime());
		averageSpeedTextView.setText("54 km/h");
		burnedCaloriesTextView.setText("4343 kcal");

		switch (mWorkout.getWorkoutType()) {
			case CYCLING:
				backgroundImageView.setImageResource(R.drawable.ic_directions_bike_gray_100dp);
				totalDistanceTextView.setText(String.format(getResources().getString(R.string.distance_units_km), mWorkout.getDistance()));
				topSpeedTextView.setText(String.format(getResources().getString(R.string.speed_units_kph), mWorkout.getTopSpeed()));
				break;
			case RUNNING:
				backgroundImageView.setImageResource(R.drawable.ic_directions_run_gray_100dp);
				totalDistanceTextView.setText(String.format(getResources().getString(R.string.distance_units_m), mWorkout.getDistance()));
				topSpeedTextView.setText(String.format(getResources().getString(R.string.speed_units_mps), mWorkout.getTopSpeed()));
				break;
		}

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

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mGoogleMap = googleMap;

		mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
		mGoogleMap.getUiSettings().setCompassEnabled(false);
		mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
		moveCamera(mWorkout.getLocationsListLatLng(0), 11);
	}

	private void moveCamera(LatLng latLng, float zoom) {
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
		mGoogleMap.animateCamera(cameraUpdate);
	}
}

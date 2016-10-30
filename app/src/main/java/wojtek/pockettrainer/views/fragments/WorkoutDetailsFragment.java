package wojtek.pockettrainer.views.fragments;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.rafalzajfert.androidlogger.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.views.interfaces.MapsWorkoutActivityListener;


/**
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */
public class WorkoutDetailsFragment extends Fragment implements OnMapReadyCallback {

	private static final String WORKOUT_KEY = "workout_key";

	MapsWorkoutActivityListener mActivityListener;
	private GoogleMap mGoogleMap;
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
		MapView mapView = (MapView) view.findViewById(R.id.map_view_workout_details);
		mapView.onCreate(savedInstanceState);
		mapView.getMapAsync(this);

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

		startTimeTextView.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(mWorkout.getStartDate().getTime()) +
				", " + DateFormat.getDateInstance(DateFormat.LONG).format(mWorkout.getStartDate().getTime()));
		startLocationTextView.setText(getLocationAddress(mWorkout.getLocation(0).getLatitude(), mWorkout.getLocation(0).getLongitude()));
		finishTimeTextView.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(mWorkout.getFinishDate().getTime()) +
				", " + DateFormat.getDateInstance(DateFormat.LONG).format(mWorkout.getFinishDate().getTime()));
		finishLocationTextView.setText(getLocationAddress(mWorkout.getLocation(mWorkout.getLocationsListLastIndex()).getLatitude(),
				mWorkout.getLocation(mWorkout.getLocationsListLastIndex()).getLongitude()));
		elapsedTimeTextView.setText(mWorkout.getElapsedTime());
		averageSpeedTextView.setText("?");
		burnedCaloriesTextView.setText("?");

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

	private String getLocationAddress(double latitude, double longitude) {
		Geocoder geocoder;
		List<Address> addresses;
		String address = "";
		geocoder = new Geocoder(getContext(), Locale.getDefault());

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

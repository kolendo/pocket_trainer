package wojtek.pockettrainer.views.activities;

import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.Workout;
import wojtek.pockettrainer.views.fragments.MapTracingFragment;

/**
 * @author Wojtek Kolendo
 * @date 17.09.2016
 */
public class MapsWorkoutActivity extends AppCompatActivity {

	private static final int FRAGMENT_CONTAINER = R.id.fragment_frame_map;
	public static final String EXTRA_WORKOUT = "extra_workout";
	private static final String WORKOUT_KEY = "workout_key";

	private AppBarLayout mAppBarLayout;
	private Workout mWorkout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_workout);
		initWorkout(savedInstanceState);
		initToolbar();

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

}

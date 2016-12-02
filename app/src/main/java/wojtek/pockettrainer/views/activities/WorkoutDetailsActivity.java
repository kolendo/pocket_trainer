package wojtek.pockettrainer.views.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.views.fragments.WorkoutDetailsFragment;

/**
 * @author Wojtek Kolendo
 * @date 02.12.2016
 */

public class WorkoutDetailsActivity extends AppCompatActivity {


	private static final int FRAGMENT_CONTAINER = R.id.fragment_frame_workout_details;
	public static final String EXTRA_WORKOUT_DETAILS = "extra_workout_details";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_details);
		initToolbar();

		long workoutId = getIntent().getLongExtra(EXTRA_WORKOUT_DETAILS, -1);
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(FRAGMENT_CONTAINER, WorkoutDetailsFragment.newInstance(workoutId))
					.commit();
		}
	}

	/**
	 * inicjalizacja toolbara
	 */
	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_workout_details);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

package wojtek.pockettrainer.views.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.Training;
import wojtek.pockettrainer.views.fragments.TrainingFragment;

/**
 * @author Wojtek Kolendo
 * @date 18.11.2016
 */

public class TrainingActivity extends AppCompatActivity {


	private static final int FRAGMENT_CONTAINER = R.id.fragment_frame_training;
	public static final String EXTRA_TRAINING = "extra_training";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training);
		initToolbar();

		long trainingId = getIntent().getLongExtra(EXTRA_TRAINING, -1);
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(FRAGMENT_CONTAINER, TrainingFragment.newInstance(trainingId))
					.commit();
		}
	}

	/**
	 * inicjalizacja toolbara
	 */
	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_training);
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

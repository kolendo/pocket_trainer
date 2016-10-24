package wojtek.pockettrainer.views.activities;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import wojtek.pockettrainer.R;
import wojtek.pockettrainer.TrainerApplication;
import wojtek.pockettrainer.views.fragments.BfpCalculatorFragment;
import wojtek.pockettrainer.views.fragments.menu.BmiFragment;
import wojtek.pockettrainer.views.fragments.menu.HomeFragment;
import wojtek.pockettrainer.views.fragments.menu.NewWorkoutFragment;
import wojtek.pockettrainer.views.fragments.menu.WorkoutsHistoryFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	private static final float DEFAULT_APP_BAR_ELEVATION = dpToPx(4);
	private static final int FRAGMENT_CONTAINER = R.id.fragment_frame;

	private Toolbar mToolbar;
	private DrawerLayout mDrawerLayout;
	private AppBarLayout mAppBarLayout;
	private Fragment mCurrentFragment;
	private NavigationView mNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_drawer);
		setStatusBarTranslucent(true);
		initToolbar();
		initDrawer();
		changeFragment(HomeFragment.newInstance(), false);

	}

	private static int dpToPx(int dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	private void initToolbar() {
		mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
	}

	private void initDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		mDrawerLayout.addDrawerListener(toggle);
		toggle.syncState();

		mNavigationView = (NavigationView) findViewById(R.id.nav_view);
		mNavigationView.setNavigationItemSelectedListener(this);
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else if (!(mCurrentFragment instanceof HomeFragment)) { //If main fragment changes this should be changed.
			mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
			setTitle(R.string.app_name);
			changeFragment(HomeFragment.newInstance(), true);
		} else {
			super.onBackPressed();
		}
	}

	public void changeFragment(final Fragment fragment, boolean useDelay) {
		mCurrentFragment = fragment;
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

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		if (item.getItemId() == R.id.nav_home) {
			setTitle(R.string.app_name);
		} else {
			setTitle(item.getTitle());
		}
			if (!item.isChecked()) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				mAppBarLayout.setElevation(DEFAULT_APP_BAR_ELEVATION);
			}
			switch (item.getItemId()) {
				case R.id.nav_home:
					changeFragment(HomeFragment.newInstance(), true);
					break;
				case R.id.nav_planner:
					Toast.makeText(this, "Not implemented!", Toast.LENGTH_SHORT).show();
					break;
				case R.id.nav_workout:
					changeFragment(NewWorkoutFragment.newInstance(), true);
					break;
				case R.id.nav_history:
					changeFragment(WorkoutsHistoryFragment.newInstance(), true);
					break;
				case R.id.nav_spotify:
					Toast.makeText(this, "Not implemented!", Toast.LENGTH_SHORT).show();
					break;
				case R.id.nav_bmi:
					changeFragment(BmiFragment.newInstance(), true);
					break;
				case R.id.nav_calculate_bfp:
					changeFragment(BfpCalculatorFragment.newInstance(), true);
					break;
				case R.id.nav_settings:
					Toast.makeText(this, "Not implemented!", Toast.LENGTH_SHORT).show();
					break;
				case R.id.nav_quit:
					finish();
					break;
			}
		}

		mDrawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}

	protected void setStatusBarTranslucent(boolean makeTranslucent) {
		if (makeTranslucent) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		} else {
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
	}
}

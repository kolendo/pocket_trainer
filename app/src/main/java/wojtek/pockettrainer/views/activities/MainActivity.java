package wojtek.pockettrainer.views.activities;

import android.content.Intent;
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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.humandevice.android.core.tools.ViewUtils;
import com.humandevice.android.mvpframework.PresenterActivity;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.views.fragments.menu.CalculatorBfpFragment;
import wojtek.pockettrainer.views.fragments.menu.CalculatorBmiFragment;
import wojtek.pockettrainer.views.fragments.menu.HomeFragment;

public class MainActivity extends PresenterActivity implements NavigationView.OnNavigationItemSelectedListener {

	private static final float DEFAULT_APP_BAR_ELEVATION = ViewUtils.dpToPx(4);
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
		initToolbar();
		initDrawer();
		changeFragment(HomeFragment.newInstance(), false);

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

//		if (UserService.getInstance().getUserProfile().isCourier()) {
//			Menu menu = mNavigationView.getMenu();
//			menu.findItem(R.id.nav_deliver_package).setVisible(true);
//			menu.findItem(R.id.nav_taken_packages).setVisible(true);
//		}

		TextView emailTextView = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.email);
//		UserProfile userProfile = UserService.getInstance().getUserProfile();
//		nameTextView.setText(userProfile.getFullName());
//		emailTextView.setText(userProfile.getEmail());
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
			changeFragment(HomeFragment.newInstance(), false);
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
					changeFragment(HomeFragment.newInstance(), false);
					break;
				case R.id.nav_profile:
					Toast.makeText(this, "Not implemented!", Toast.LENGTH_SHORT).show();
					break;
				case R.id.nav_calculate_bmi:
					changeFragment(CalculatorBmiFragment.newInstance(), false);
					break;
				case R.id.nav_calculate_bfp:
					changeFragment(CalculatorBfpFragment.newInstance(), false);
					break;
				case R.id.nav_log_out:
//					UserService.getInstance().logout();
					finish();
//					startActivity(new Intent(MainActivity.this, SearchPackageActivity.class));
					break;
			}
		}

		mDrawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}
}

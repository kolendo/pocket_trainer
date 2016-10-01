package wojtek.pockettrainer.repositories.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.TrainerApplication;

/**
 * @author Wojtek Kolendo
 * @date 01.10.2016
 */

public class SettingsPreferences {

	private static final int SETTINGS_LESS_ACCURACY_KEY = R.string.settings_less_accuracy_key;
	private static final int SETTINGS_METRIC_UNITS_KEY = R.string.settings_metric_units_key;

	private SharedPreferences mSharedPreferences;

	public SettingsPreferences() {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(TrainerApplication.getContext());
	}

	public void clearSettings() {
		mSharedPreferences.edit().clear().apply();
	}

	public boolean isLessAccuracyEnabled() {
		return mSharedPreferences.getBoolean(TrainerApplication.getContext()
				.getResources().getString(SETTINGS_LESS_ACCURACY_KEY), false);
	}

	public void setLessAccuracyEnabled(boolean enable) {
		mSharedPreferences.edit().putBoolean(TrainerApplication.getContext()
				.getResources().getString(SETTINGS_LESS_ACCURACY_KEY), enable).apply();
	}

	public boolean isMetricUnitsEnabled() {
		return mSharedPreferences.getBoolean(TrainerApplication.getContext()
				.getResources().getString(SETTINGS_METRIC_UNITS_KEY), true);
	}

	public void setMetricUnitsEnabled(boolean enable) {
		mSharedPreferences.edit().putBoolean(TrainerApplication.getContext()
				.getResources().getString(SETTINGS_METRIC_UNITS_KEY), enable).apply();
	}
}

package wojtek.pockettrainer;

import android.app.Application;
import android.content.Context;

/**
 * @author Wojtek Kolendo
 * @date 14.08.2016
 */
public class TrainerApplication extends Application {

	private static Context sContext;

	@Override
	public void onCreate() {
		super.onCreate();

		sContext = this;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static Context getContext() {
		return sContext;
	}
}

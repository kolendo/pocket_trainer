package wojtek.pockettrainer;

import android.app.Application;
import android.content.Context;

import org.greenrobot.greendao.database.Database;

import wojtek.pockettrainer.models.DaoMaster;
import wojtek.pockettrainer.models.DaoSession;
import wojtek.pockettrainer.models.JoinTrainingsWithTrainingActivities;
import wojtek.pockettrainer.models.Training;
import wojtek.pockettrainer.models.TrainingActivity;

/**
 * @author Wojtek Kolendo
 * @date 14.08.2016
 */
public class TrainerApplication extends Application {

	private static Context sContext;
	private static DaoSession sDaoSession;

	@Override
	public void onCreate() {
		super.onCreate();
		sContext = this;

		initDatabase();
	}

	private void initDatabase() {
		DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "trainer-db");
		Database db = helper.getWritableDb();
		sDaoSession = new DaoMaster(db).newSession();
		mockDao();
	}

	public static DaoSession getDaoSession() {
		return sDaoSession;
	}

	public static Context getContext() {
		return sContext;
	}

	private void mockDao() {
		// TODO: 26.11.2016
//		 STOPSHIP: 26.11.2016 DELETE MOCK

		Training training_a = new Training(null, "Sunday chest lifting", false);
		getDaoSession().getTrainingDao().insert(training_a);

		Training training_b = new Training(null, "Friday running night", true);
		getDaoSession().getTrainingDao().insert(training_b);

	}

}

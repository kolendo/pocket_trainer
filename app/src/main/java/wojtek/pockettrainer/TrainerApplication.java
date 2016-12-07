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

		Training training_a = new Training(0L, "Friday running night", true);
		getDaoSession().getTrainingDao().insertOrReplace(training_a);

		TrainingActivity trainingActivity_a = new TrainingActivity(0L, "Treadmill", "Break every 30 mins", 0, 5400000);
		getDaoSession().getTrainingActivityDao().insertOrReplace(trainingActivity_a);
		TrainingActivity trainingActivity_b = new TrainingActivity(1L, "Stationary bicycle", "Hill climbing mode ", 0, 2700000);
		getDaoSession().getTrainingActivityDao().insertOrReplace(trainingActivity_b);

		JoinTrainingsWithTrainingActivities joinTrainingsWithTrainingActivities_a = new JoinTrainingsWithTrainingActivities(0L, 0, 0);
		JoinTrainingsWithTrainingActivities joinTrainingsWithTrainingActivities_b = new JoinTrainingsWithTrainingActivities(1L, 0, 1);
		getDaoSession().getJoinTrainingsWithTrainingActivitiesDao().insertOrReplace(joinTrainingsWithTrainingActivities_a);
		getDaoSession().getJoinTrainingsWithTrainingActivitiesDao().insertOrReplace(joinTrainingsWithTrainingActivities_b);

	}

}

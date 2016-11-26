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
//		getDaoSession().getTrainingDao().deleteAll();

		Training training_a = new Training();
		training_a.setId(0);
		training_a.setTitle("Sunday chest lifting");
		getDaoSession().getTrainingDao().insertOrReplace(training_a);

		Training training_b = new Training();
		training_b.setId(1);
		training_b.setTitle("Friday running night");
		getDaoSession().getTrainingDao().insertOrReplace(training_b);


//		TrainingActivity trainingActivity = new TrainingActivity();
//		trainingActivity.setId(0);
//		trainingActivity.setTitle("Fast running");
//		trainingActivity.setTime(25);
//
//		TrainingActivity trainingActivity2 = new TrainingActivity();
//		trainingActivity2.setId(1);
//		trainingActivity2.setTitle("Fast running");
//		trainingActivity2.setSets(12);
//		getDaoSession().getTrainingActivityDao().insertOrReplace(trainingActivity2);
//
//		JoinTrainingsWithTrainingActivities joinTrainingsWithTrainingActivities = new JoinTrainingsWithTrainingActivities();
//		joinTrainingsWithTrainingActivities.setId(0);
//		joinTrainingsWithTrainingActivities.setTrainingId(0);
//		joinTrainingsWithTrainingActivities.setTrainingActivityId(1);
//		getDaoSession().getJoinTrainingsWithTrainingActivitiesDao().insertOrReplace(joinTrainingsWithTrainingActivities);
	}

}

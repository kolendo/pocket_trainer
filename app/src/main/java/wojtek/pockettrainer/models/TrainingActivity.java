package wojtek.pockettrainer.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Wojtek Kolendo
 * @date 17.11.2016
 */

@Entity
public class TrainingActivity {

	@Id(autoincrement = true)
	private long id;

	private String title;

	private String description;

	private int sets;

	private long time;


	public TrainingActivity() {
		sets = 0;
		time = 0;
	}

	@Generated(hash = 759100894)
	public TrainingActivity(long id, String title, String description, int sets,
									long time) {
					this.id = id;
					this.title = title;
					this.description = description;
					this.sets = sets;
					this.time = time;
	}

	@Override
	public String toString() {
		return "TrainingActivity{" +
				"mId=" + id +
				", mTitle='" + title + '\'' +
				", mDescription='" + description + '\'' +
				", mSets='" + sets + '\'' +
				", mTime='" + time + '\'' +
				'}';
	}

	public long getId() {
					return this.id;
	}

	public void setId(long id) {
					this.id = id;
	}

	public String getTitle() {
					return this.title;
	}

	public void setTitle(String title) {
					this.title = title;
	}

	public String getDescription() {
					return this.description;
	}

	public void setDescription(String description) {
					this.description = description;
	}

	public int getSets() {
					return this.sets;
	}

	public void setSets(int sets) {
					this.sets = sets;
	}

	public long getTime() {
					return this.time;
	}

	public void setTime(long time) {
					this.time = time;
	}
}

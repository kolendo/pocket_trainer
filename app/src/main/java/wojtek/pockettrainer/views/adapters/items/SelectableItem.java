package wojtek.pockettrainer.views.adapters.items;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * @author Wojtek Kolendo
 * @date 15.10.2016
 */

public abstract class SelectableItem <T> implements Serializable {

	@NonNull
	protected T mObject;

	private boolean mSelected;

	private long mSelectedTime;

	private boolean mAnimateSelection;

	public SelectableItem(@NonNull T object) {
		mObject = object;
	}

	public boolean isSelected() {
		return mSelected;
	}

	public void setSelected(boolean selected) {
		setSelected(selected, false);
	}

	public void setSelected(boolean selected, boolean animate) {
		mSelected = selected;
		mAnimateSelection = animate;
		mSelectedTime = System.currentTimeMillis();
	}

	public boolean animateSelection() {
		return mAnimateSelection && System.currentTimeMillis() - mSelectedTime < 500;
	}

	public boolean disableAnimation() {
		return mAnimateSelection = false;
	}

	@NonNull
	public T getObject() {
		return mObject;
	}
}
package wojtek.pockettrainer.views.interfaces;

import android.support.v4.app.Fragment;

/**
 * @author Wojtek Kolendo
 * @date 02.12.2016
 */

public interface MainActivityListener {

	void changeFragment(final Fragment fragment, boolean useDelay);

	void setDrawerItemChecked(int item);

}

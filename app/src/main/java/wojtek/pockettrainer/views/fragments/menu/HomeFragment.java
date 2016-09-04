package wojtek.pockettrainer.views.fragments.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humandevice.android.v4.mvpframework.PresenterFragment;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.presenters.HomePresenter;
import wojtek.pockettrainer.presenters.impl.HomePresenterImpl;
import wojtek.pockettrainer.views.HomeView;


/**
 * TODO Dokumentacja
 *
 * @author Wojtek Kolendo
 * @date 04.09.2016
 */
public class HomeFragment extends PresenterFragment<HomeView, HomePresenter> implements HomeView {

	public static HomeFragment newInstance() {
		return new HomeFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public Class<? extends HomePresenter> getPresenterClass() {
		return HomePresenterImpl.class;
	}

	@Override
	protected void initView(View view) {

	}
}
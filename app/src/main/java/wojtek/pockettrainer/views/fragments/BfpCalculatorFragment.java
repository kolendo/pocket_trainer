package wojtek.pockettrainer.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import wojtek.pockettrainer.R;


/**
 * TODO Dokumentacja
 *
 * @author Wojtek Kolendo
 * @date 04.09.2016
 */
public class BfpCalculatorFragment extends Fragment {

	public static BfpCalculatorFragment newInstance() {
		return new BfpCalculatorFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bfp_calculator, container, false);
	}

}
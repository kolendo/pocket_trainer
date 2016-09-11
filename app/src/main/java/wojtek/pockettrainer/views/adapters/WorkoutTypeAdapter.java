package wojtek.pockettrainer.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import wojtek.pockettrainer.R;
import wojtek.pockettrainer.models.enums.WorkoutType;

/**
 * @author Wojtek Kolendo
 * @date 11.09.2016
 */
public class WorkoutTypeAdapter extends ArrayAdapter<WorkoutType> {

	private int mResource;

	public WorkoutTypeAdapter(Context context, int resource) {
		super(context, resource, WorkoutType.values());
		this.mResource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getMyView(position, convertView, parent);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getMyView(position, convertView, parent);
	}

	private View getMyView(int position, View convertView, ViewGroup parent) {
		if (convertView == null || convertView.getTag() == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(mResource, parent, false);
			TextView typeTextView = (TextView) convertView.findViewById(R.id.spinner_name);
			convertView.setTag(typeTextView);
		}

		TextView text = (TextView) convertView.getTag();
		String setText = getItem(position).toString();
		text.setText(setText);

		return convertView;
	}
}

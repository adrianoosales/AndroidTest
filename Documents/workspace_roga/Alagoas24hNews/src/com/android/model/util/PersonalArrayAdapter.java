package com.android.model.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.model.rss.Message;
import com.android.view.activity.R;

public class PersonalArrayAdapter extends ArrayAdapter<Message> {

	// Create necessary variables
	private final Context context;
	private final List<Message> values;
	
	// Creates the constructor of class
	public PersonalArrayAdapter(Context context, List<Message> values) {
		super(context, R.layout.news_row_layout, values);
		// Sets the parameters
		this.context = context;
		this.values  = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Gets the LayoutInflater
		LayoutInflater layoutInflater = 
				(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		// Create a view to represent the Row view
		View rowView = layoutInflater.inflate(R.layout.news_row_layout, parent, false);
		
		// Gets the values of a row
		TextView title = (TextView) rowView.findViewById(R.id.title);
		TextView date = (TextView) rowView.findViewById(R.id.date);
		TextView description = (TextView) rowView.findViewById(R.id.description);
		// Now, sets this values
		title.setText(this.values.get(position).getTitle());
		date.setText(this.values.get(position).getDate());
		description.setText("\t" + this.values.get(position).getDescription());
		
		return rowView;
	}
}
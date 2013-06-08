package com.android.view.activity;

import java.util.List;

import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.model.rss.Message;
import com.android.model.rss.XmlPullFeedParser;
import com.android.model.util.PersonalArrayAdapter;

public class NewsListView extends ListActivity {
	// Defines the instance of database
	private SQLiteDatabase database;

	// Defines an Array to store the RSS Messages
	private List<Message> RSSMessages;

	private ListView listView;
	
	private PersonalArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_news_list_view);
		Log.i("livro", "Olha eu aqui!");

		listView = getListView();
		
		// Call the method to able the long click
		setLongClickAvailable();
		
		// Call the method to instantiate the database
		setDatabase();

		// Calls the method that gets data from RSS
		getRSSData();

		// Create an instance of the our Adapter
		adapter = new PersonalArrayAdapter(this,
				this.RSSMessages);
		setListAdapter(adapter);
	}

	// Method to set the database
	public void setDatabase() {
		// Sets the instance of database
		this.database = this.openOrCreateDatabase("rssFeed",
				Context.MODE_PRIVATE, null);
	}

	// Method that gets the RSS data
	public void getRSSData() {
		XmlPullFeedParser parser = new XmlPullFeedParser(
				"http://www.alagoas24horas.com.br/feed/");
		// Sets the Array RSS messages
		this.RSSMessages = parser.parse();

	}

	// Method to sets the longClickable available
	public void setLongClickAvailable() {
		// Sets as true the long clickable
		this.listView.setLongClickable(true);
		// Define the action to long click
		this.listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				try {
					// Gets the values of news
					TextView title = (TextView) view.findViewById(R.id.title);
					TextView date = (TextView) view.findViewById(R.id.date);
					TextView description = (TextView) view.findViewById(R.id.description);
					
					// Now, put the values in ContentValues to insert on database
					ContentValues valuesToInsert = new ContentValues();
					valuesToInsert.put("title", title.getText().toString());
					valuesToInsert.put("date", date.getText().toString());
					valuesToInsert.put("description", description.getText().toString());
					
					// Finally, add my contentValues on database
					database.insert("favorites", null, valuesToInsert);
					
					// Finally (again), show the confirmation message on screen
					Toast.makeText(NewsListView.this, "Not’cia adicionada aos favoritos", 
							Toast.LENGTH_LONG).show();
					
					// Before return and show the insert message I have to
					// refresh the ListView of favorites
					// So, Call the method to do this
					FavoritesListView.fillFavorites();
					// After calls the method to refresh the ListView
					FavoritesListView.refreshDatabase();
					
				}catch(SQLiteConstraintException exc) {
					// Create an Alert to inform that the news is already on database
					Builder alerta = new Builder(NewsListView.this);
					alerta.setTitle("Not’cia favorita");
					alerta.setMessage("A not’cia em quest‹o j‡ est‡ nos seus favoritos.");
					alerta.setPositiveButton("Ok", null);
					alerta.show();
				}
				
				return true;
			}
		});
	}
}
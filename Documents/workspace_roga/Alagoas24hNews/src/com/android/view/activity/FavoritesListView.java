package com.android.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.model.rss.Message;
import com.android.model.util.PersonalArrayAdapter;

public class FavoritesListView extends ListActivity {

	// Create an instance of database
	private static SQLiteDatabase database;
	// Create a Cursor to get database data
	private static Cursor databaseCursor;
	
	// Create an Adapter instance
	private static PersonalArrayAdapter adapter;

	// Defines an Array to store the RSS Favorite Messages
	private static List<Message> RSSMessages;

	// Sets this ListActivity
	private static ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_favorites_list_view);

		// Calls the method to instantiate the necessary files
		instantiateNecessaryFiles();
		
		// Calls the method to be able the long click
		setLongClickAvailable();

		// Call the method to instantiate the database
		setDatabase();

		// Call the method that fill the favorite if exists
		fillFavorites();

		// Now, create an instance of the our Adapter
		adapter = new PersonalArrayAdapter(this,
				RSSMessages);
		setListAdapter(adapter);
	}

	// Method to instantiate necessary files
	public void instantiateNecessaryFiles() {

		RSSMessages = new ArrayList<Message>();

		listView = getListView();
	}

	// Method to set the database
	public void setDatabase() {
		// Sets the instance of database
		database = this.openOrCreateDatabase("rssFeed",
				Context.MODE_PRIVATE, null);
	}
	
	// Method to refresh a database
	public static void refreshDatabase() {
		adapter.notifyDataSetChanged();
	}

	// Method to fill the favorites if exists
	public static void fillFavorites() {
		// Cleans the favorite array
		RSSMessages.clear();
		// Gets the instances of database
		databaseCursor = database.query("favorites", null, null, null, null,
				null, null);

		// Analisys if cursor isn't empty
		if (databaseCursor.getCount() > 0) {
			// Move cursor to first element
			databaseCursor.moveToFirst();

			// For to get all favorites from database
			for (int count = 0; count < databaseCursor.getCount(); count++) {
				// Create an instance of Message
				Message aMessage = new Message();
				// Sets this values
				aMessage.setTitle(databaseCursor.getString(databaseCursor
						.getColumnIndex("title")));
				aMessage.setDate(databaseCursor.getString(databaseCursor
						.getColumnIndex("date")));
				aMessage.setDescription(databaseCursor.getString(databaseCursor
						.getColumnIndex("description")));
				// Finally, add the Message object to array
				RSSMessages.add(aMessage);
				// Move cursor to next element
				databaseCursor.moveToNext();
			}
		} else {
			// If I don't have any favorite notice yet
			// I'll create a message saying it
			Message aMessage = new Message();
			// Sets this values
			aMessage.setTitle("");
			aMessage.setDate("");
			aMessage.setDescription("Voc ainda n‹o possui not’cias nos favoritos.");
			// Finnaly, add the Message object to array
			RSSMessages.add(aMessage);
		}
	}

	// Method to sets the longClickable available
	public void setLongClickAvailable() {
		// Sets as true the long clickable
		listView.setLongClickable(true);
		// Define the action to long click
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// Gets the values of news
				final TextView title = (TextView) view.findViewById(R.id.title);
				
				// Shows an Alert to confirm exclusion
				Builder alerta = new Builder(FavoritesListView.this);
				alerta.setTitle("Excluir Not’cia");
				alerta.setMessage("Deseja excluir a not’cia '" + title.getText().toString() 
						+ "' dos seus favoritos?");
				alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Delete the instance from database
						database.delete("favorites", "title=?", new String[]{title.getText().toString()});
						// Show a toast informing the news was removed
						Toast.makeText(FavoritesListView.this, "Not’cia exclu’da.", Toast.LENGTH_SHORT).show();
						
						// Calls the method to refresh favorites list
						fillFavorites();
						// Calls the method to refresh the ListView
						refreshDatabase();
					}
				});
				alerta.setNegativeButton("N‹o", null);
				alerta.show();
				
				return true;
			}
		});
	}
}

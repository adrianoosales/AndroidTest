package com.android.view.activity;

import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.android.model.persistence.RepositorioScript;

public class MainAlagoas24h extends TabActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_alagoas24h);

		// Calls the method to instantiate database
		setRepository();

		// Calls the method to create the tabBars
		createTabBars();
	}

	// Method to instantiate the database to store the favorites
	public void setRepository() {
		// Instantiate the databse and if it doesnt exist, the method create it
		RepositorioScript rp = new RepositorioScript(this);
		// After, Close the databsae
		rp.fechar();
	}

	// Method to create the tabBars of my application
	public void createTabBars() {
		// Analisys if the internet connection is on
		if (isOnline()) {
			// Initialize and Get the necessary variables
			TabHost tabHost = getTabHost();

			// Instantiate the views of each tabBar
			TabSpec tabSpecNews = tabHost.newTabSpec("Not’cias");
			tabSpecNews
					.setIndicator(
							"Not’cias",
							getResources().getDrawable(
									android.R.drawable.ic_menu_zoom));
			tabSpecNews.setContent(new Intent(this, NewsListView.class));
			tabHost.addTab(tabSpecNews);

			TabSpec tabSpecFavorites = tabHost.newTabSpec("Favoritos");
			tabSpecFavorites.setIndicator("Favoritos", getResources()
					.getDrawable(android.R.drawable.star_big_off));
			tabSpecFavorites.setContent(new Intent(this,
					FavoritesListView.class));
			tabHost.addTab(tabSpecFavorites);

			// Sets the current tab as first tab (News)
			tabHost.setCurrentTab(0);

		} else {

			// Sets the message of connection fault
			Builder alert = new Builder(this);
			alert.setTitle("Erro ao conectar-se");
			alert.setMessage("Por favor, verifique sua conex‹o com a internet.");
			alert.setPositiveButton("Tentar Novamente",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Calls method again
							createTabBars();
						}
					});
			alert.setNegativeButton("Configurar",
					new DialogInterface.OnClickListener() {
						// Envio o pedido ao servidor
						@Override
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(
									android.provider.Settings.ACTION_SETTINGS));
							// Then, finish my activity
							finish();
						}
					});
			// Shows the alert on screen
			alert.show();
		}
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
}

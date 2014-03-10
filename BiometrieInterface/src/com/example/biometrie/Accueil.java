package com.example.biometrie;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Accueil extends Activity {
	
	Button buttonInscription;
	Button buttonIdentification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accueil);
		
		buttonInscription = (Button)findViewById(R.id.buttonInscription);
		buttonIdentification = (Button)findViewById(R.id.buttonIdentification);
		
		buttonInscription.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent (Accueil.this,Inscription.class);
				startActivity(i);
				
			}
		});
		
		buttonIdentification.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Accueil.this,LesUtilisateurs.class);
				startActivity(i);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accueil, menu);
		return true;
	}

}

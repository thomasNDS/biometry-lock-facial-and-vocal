package com.example.biometrie;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.biometrie.*;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

public class Inscription extends Activity {

	EditText editPrenom = null;
	EditText editNom = null;
	EditText editMail = null;
	
	
	Button photo = null;
	
	ImageView image = null;
	ImageButton prendreUnePhoto = null;
	
	
	Timer timer = new Timer();
	
	String prenom = "toto";
	String nom = "titi";
	String mail = "toto@titi";
	
	Button Valider = null;
	List<User> listeDesUtilisateurs;
	File mFichier;
	String pathPhoto;
	

	//Action listener pour prendre une photo et l'enregistrer dasn la mémoir externe du smartphone
	
private OnClickListener takePicture = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			prenom = editPrenom.getText().toString();
			nom = editNom.getText().toString();
			//System.out.println("jai appuyé sur le bouton");
			pathPhoto = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Pictures/" +  prenom + "-" + nom +".jpg";
		    mFichier = new File(pathPhoto);
		    Uri fileUri = Uri.fromFile(mFichier);
		    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		    startActivityForResult(intent, 0);
			
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
		
		Valider = (Button)findViewById(R.id.ValiderButtonInscri);
		
		prendreUnePhoto = (ImageButton)findViewById(R.id.buttonPhotoInscription);
		
		editPrenom = (EditText)findViewById(R.id.editTextPrenom);
		editNom = (EditText)findViewById(R.id.editTextNom);
		editMail = (EditText)findViewById(R.id.editTextMail);
		
		prendreUnePhoto.setOnClickListener(takePicture);
		
		Valider.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				prenom = editPrenom.getText().toString();
				nom = editNom.getText().toString();
				mail = editMail.getText().toString();
				Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
				
				Matcher m = p.matcher(mail);
				if(prenom.isEmpty() || nom.isEmpty() || mail.isEmpty())
					Toast.makeText(Inscription.this,"Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
				else if(!m.matches())
					Toast.makeText(Inscription.this,"Votre mail est invalide", Toast.LENGTH_SHORT).show();
				else if(!isNameFormat(prenom))
					Toast.makeText(Inscription.this,"Votre prenom est invalide", Toast.LENGTH_SHORT).show();
				else if(!isNameFormat(nom))
					Toast.makeText(Inscription.this,"Votre nom est invalide", Toast.LENGTH_SHORT).show();
				else{
					
					Bundle bundle = new Bundle();
					
					bundle.putString("prenom", prenom);
					bundle.putString("nom", nom);
					bundle.putString("mail",mail);
					bundle.putString("photo", pathPhoto);
					
				
					Intent i = new Intent(Inscription.this,InscriptionSon.class);
					i.putExtras(bundle);
					startActivity(i);
				}
			}
		});
	}
	
	public boolean isNameFormat(String s)
	{
		for (int i = 0; i < s.length(); i++) {
			if (!(Character.isLetter(s.charAt(i)) || s.charAt(i) == '-'))
				return false;
		}
		return true;
	}
	
	protected void onResume() {
	    super.onResume();
	    editPrenom.setText("");
	    editMail.setText("");
	    editNom.setText("");
	    
	    // Normal case behavior follows
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscription, menu);
		return true;
	}

}

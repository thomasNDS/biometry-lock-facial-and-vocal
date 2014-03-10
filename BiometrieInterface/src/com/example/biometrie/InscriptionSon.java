package com.example.biometrie;

import java.io.IOException;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import 	android.media.AudioRecord;
public class InscriptionSon extends Activity {

	Button enregDebut = null;
	Button enregFin = null;
	MediaRecorder recorder;
	MediaController son = null;
	String cheminSon = "";
	Button finInscription = null;
	private int bufferSize = 0;
	
	
private OnClickListener touchListenerEnre = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			enregDebut.setEnabled(false);
	        enregFin.setEnabled(true);
			
			recorder = new MediaRecorder();
			//recorder = new AudioRecord();
	       
	        
			//recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			//recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
			//recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

			String path = Environment.getExternalStorageDirectory().getAbsolutePath()
					 + "/Music/" +  System.currentTimeMillis() +".wav";
			
			cheminSon = path;
			//recorder.setOutputFile(path);
			System.out.println(path);
			try {
				//recorder.prepare();
				recorder.start();
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	};
	
private OnClickListener FinEnreg = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			enregDebut.setEnabled(true);
	        enregFin.setEnabled(false);
	        
	        try {
				recorder.stop();
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	        
	        Toast.makeText(getApplicationContext(),"Son enregistré avec succès", Toast.LENGTH_LONG).show();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription_son);
		
		bufferSize = AudioRecord.getMinBufferSize(8000,AudioFormat.CHANNEL_CONFIGURATION_MONO,AudioFormat.ENCODING_PCM_16BIT);
		enregDebut = (Button)findViewById(R.id.buttonPlayEnregistrement);
		enregFin = (Button)findViewById(R.id.buttonStopEnregistrement);
		
		enregDebut.setOnClickListener(touchListenerEnre);
		enregFin.setOnClickListener(FinEnreg);
		
		finInscription = (Button)findViewById(R.id.buttonFinInscription);
		
		Bundle bundle = this.getIntent().getExtras();
		Database db = new Database();
		String prenom = bundle.getString("prenom");
		String nom = bundle.getString("nom");
		String mail = bundle.getString("mail");
		String photo = bundle.getString("photo");
		 db.AddUser(prenom, nom, mail, photo, User.emptyField);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscription_son, menu);
		return true;
	}

}

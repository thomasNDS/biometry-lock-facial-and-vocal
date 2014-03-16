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
import android.media.AudioRecord;
import fr.lium.spkDiarization.programs.*;


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
	        
	        Toast.makeText(getApplicationContext(),"Son enregistr� avec succ�s", Toast.LENGTH_LONG).show();
		}
	};
	
private OnClickListener FinInscription = new View.OnClickListener() {
	
	
	
	/* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        Toast.makeText(getApplicationContext(),"Not writable", Toast.LENGTH_LONG).show();
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        Toast.makeText(getApplicationContext(),"Not readable", Toast.LENGTH_LONG).show();
        return false;
    }
	
	
	@Override
	public void onClick(View v) {
		//Path to data
		isExternalStorageReadable();
		isExternalStorageWritable();
		String path = Environment.getExternalStorageDirectory().getAbsolutePath()
				 + "/Music/";
		String sousMonde = "sousMondeREAL.seg";
		String enregWave = "sousMonde.wav";
		String ubm = "ubm.gmm";
		String[] args = {"--help","--sInputMask="+path+sousMonde, "--fInputMask="+path+enregWave, "--fInputDesc=audio16kHz2sphinx,1:3:2:0:0:0,13,1:1:300:4",  "--emInitMethod=copy" ,"--tInputMask="+path+ubm, "--tOutputMask="+path+"initGmm"+".init.gmm" ,"speakers"};
		try {
			MTrainInit.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}
};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription_son);
		
		bufferSize = AudioRecord.getMinBufferSize(8000,AudioFormat.CHANNEL_CONFIGURATION_MONO,AudioFormat.ENCODING_PCM_16BIT);
		enregDebut = (Button)findViewById(R.id.buttonPlayEnregistrement);
		enregFin = (Button)findViewById(R.id.buttonStopEnregistrement);
		finInscription = (Button)findViewById(R.id.buttonFinInscription);
		
		enregDebut.setOnClickListener(touchListenerEnre);
		enregFin.setOnClickListener(FinEnreg);
		finInscription.setOnClickListener(FinInscription);
		
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

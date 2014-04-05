package com.example.biometrie;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
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
	AudioRecord mAudioRecord;
	MediaController son = null;
	String cheminSon = "";
	Button finInscription = null;
	private boolean recordOK = false;
	String rawFilePath;
	
	FileOutputStream mFos = null;
	
	int recordChannel = AudioFormat.CHANNEL_IN_MONO;
    int recordFrequency = 8000;
    int recordBits = AudioFormat.ENCODING_PCM_16BIT;
    
    Thread mRecordThread = null;
    
    int mBufferSizeInBytes = 0;
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // POUR REGARDER LES FICHIERS DE L'EXTERNAL STORAGE DU TEL, AFFICHER LA VUE DDMS DE ECLIPSE
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    private Runnable recordRunnable = new Runnable() {

	    @Override
	    public void run() {

	        byte[] audiodata = new byte[mBufferSizeInBytes];
	        int readsize = 0;

	        // start the audio recording
	        try {
	            mAudioRecord.startRecording();
	            
	        } catch (IllegalStateException ex) {
	            ex.printStackTrace();
	        }

	        
	        
	        
	        
	        
	        // in the loop to read data from audio and save it to file.
	        while (recordOK == true) {
	            readsize = mAudioRecord.read(audiodata, 0, mBufferSizeInBytes);
	            if (AudioRecord.ERROR_INVALID_OPERATION != readsize
	                    && mFos != null) {
	                try {
	                    mFos.write(audiodata);
	                    
	                    System.out.println("je suis entrin denregistrer le son!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	        // stop recording
	        try {
	            mAudioRecord.stop();
	        } catch (IllegalStateException ex) {
	            ex.printStackTrace();
	        }

	        // close the file
	        try {
	            if (mFos != null){
	            	System.out.println("fin de lenregistrement &&&&&&&&&&&&&&&&&&&&&&&&&&");
	            	mFos.flush();
	                mFos.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        // eidt header
	        try{
		        
		        String wavFilePath = rawFilePath + ".wav";
		        
		        File f = new File(rawFilePath);
	        	FileInputStream mFis = new FileInputStream(rawFilePath);
	        	DataOutputStream fos = new DataOutputStream(new FileOutputStream(wavFilePath));
	        	
	        	WriteWaveFileHeader(fos,f.length(),f.length()+36,recordFrequency,1,2*recordFrequency,16);
	        	
	        	
	             
	        	
	        	byte [ ] buff = new byte[2048];
	        	int r = mFis.read(buff);
	        	while(r > 0){
	        		fos.write(buff, 0, r);
	        		r = mFis.read(buff);
	        	}
	        	
	        	fos.close();
	        	mFis.close();
	        	
	        }catch(Exception e){
	        	System.out.println(e.toString());
	        }

	    }
	};
	
	private OnClickListener touchListenerEnre = new View.OnClickListener() {
			
		@Override
	    public void onClick(View v) {
			
			// reset the save file setup
	        rawFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()
					 + "/Music/" +  System.currentTimeMillis();
	
			Toast.makeText(getApplicationContext(),"Début de l'enregistrement audio...", Toast.LENGTH_SHORT).show();
			
	        // set up the audio source : get the buffer size for audio
	        // record.
	        int minBufferSizeInBytes = AudioRecord.getMinBufferSize(recordFrequency, recordChannel, recordBits);
	
	        if (AudioRecord.ERROR_BAD_VALUE == minBufferSizeInBytes)
	            return;
	
	        int bufferSizeInBytes = minBufferSizeInBytes * 4;
	
	        // create AudioRecord object
	        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION,
	                recordFrequency, recordChannel, recordBits,
	                bufferSizeInBytes);
	
	        // calculate the buffer size used in the file operation.
	        mBufferSizeInBytes = minBufferSizeInBytes * 2;
	
	        
	
	        try {
	            File file = new File(rawFilePath);
	            if (file.exists()) {
	                file.delete();
	            }
	            System.out.println("fileoutPutStream======================");
	            mFos = new FileOutputStream(file);
	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
	        if (recordOK == false) {
	
	            mRecordThread = new Thread(recordRunnable);
	            mRecordThread.setName("Demo.AudioRecord");
	            mRecordThread.start();
	            
	            recordOK = true;
	
	            // enable the stop button
	            enregFin.setEnabled(true);
	
	            // disable the start button
	            enregDebut.setEnabled(false);
	        }
	    }
	};
	
	private OnClickListener FinEnreg = new View.OnClickListener() {
		
		@Override
        public void onClick(View v) {

            if (recordOK == true) {

                // stop recording
                if (mRecordThread != null) {
                	
                	recordOK = false;
                	
                    try { mRecordThread.join(1000); }
                    catch (InterruptedException e) { e.printStackTrace(); }

                    mRecordThread = null;

                    // re-enable the start button
                    enregDebut.setEnabled(true);

                    // disable the start button
                    enregFin.setEnabled(false);

                    Toast.makeText(getApplicationContext(),"Enregistrement terminé.", Toast.LENGTH_SHORT).show();
                }
            }
        }
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription_son);
		
		enregDebut = (Button)findViewById(R.id.buttonPlayEnregistrement);
		enregFin = (Button)findViewById(R.id.buttonStopEnregistrement);
		
		enregDebut.setOnClickListener(touchListenerEnre);
		enregFin.setOnClickListener(FinEnreg);
		
		finInscription = (Button)findViewById(R.id.buttonFinInscription);
		
		finInscription.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(),"Inscription terminée !", Toast.LENGTH_SHORT).show();
				Intent i = new Intent (InscriptionSon.this,Accueil.class);
				startActivity(i);
			}
		});
		
		Bundle bundle = this.getIntent().getExtras();
		Database db = new Database();
		String prenom = bundle.getString("prenom");
		String nom = bundle.getString("nom");
		String mail = bundle.getString("mail");
		String photo = bundle.getString("photo");
		db.AddUser(prenom, nom, mail, photo, rawFilePath);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscription_son, menu);
		return true;
	}
	
	byte[] toBytes(int i)
	{
	  byte[] result = new byte[4];

	  result[1] = (byte) (i >> 24);
	  result[0] = (byte) (i >> 16);
	  result[3] = (byte) (i >> 8);
	  result[2] = (byte) (i /*>> 0*/);
	  
	  
	  
	  return result;
	}
	
	byte[] shortToBytes(short i)
	{
	  byte[] result = new byte[2];

	  
	  result[1] = (byte) (i >> 8);
	  result[0] = (byte) (i /*>> 0*/);

	  return result;
	}
	
	
	//Construction de l'entete d'un fichier Son Wave
	private void WriteWaveFileHeader(DataOutputStream out, long totalAudioLen,
			long totalDataLen, long longSampleRate, int channels,
			long byteRate, int RECORDER_BPP) throws IOException {

		byte[] header = new byte[44];

		header[0] = 'R';
		header[1] = 'I';
		header[2] = 'F';
		header[3] = 'F';
		header[4] = (byte) (totalDataLen & 0xff);
		header[5] = (byte) ((totalDataLen >> 8) & 0xff);
		header[6] = (byte) ((totalDataLen >> 16) & 0xff);
		header[7] = (byte) ((totalDataLen >> 24) & 0xff);
		header[8] = 'W';
		header[9] = 'A';
		header[10] = 'V';
		header[11] = 'E';
		header[12] = 'f';
		header[13] = 'm';
		header[14] = 't';
		header[15] = ' ';
		header[16] = 16;
		header[17] = 0;
		header[18] = 0;
		header[19] = 0;
		header[20] = 1;
		header[21] = 0;
		header[22] = (byte) channels;
		header[23] = 0;
		header[24] = (byte) (longSampleRate & 0xff);
		header[25] = (byte) ((longSampleRate >> 8) & 0xff);
		header[26] = (byte) ((longSampleRate >> 16) & 0xff);
		header[27] = (byte) ((longSampleRate >> 24) & 0xff);
		header[28] = (byte) (byteRate & 0xff);
		header[29] = (byte) ((byteRate >> 8) & 0xff);
		header[30] = (byte) ((byteRate >> 16) & 0xff);
		header[31] = (byte) ((byteRate >> 24) & 0xff);
		header[32] = (byte) (2);// (2 * 16 / 8);
		header[33] = 0;
		header[34] = (byte) RECORDER_BPP;
		header[35] = 0;
		header[36] = 'd';
		header[37] = 'a';
		header[38] = 't';
		header[39] = 'a';
		header[40] = (byte) (totalAudioLen & 0xff);
		header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
		header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
		header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

		out.write(header, 0, 44);
	}
	
}

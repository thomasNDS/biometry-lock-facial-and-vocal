package com.example.biometrie;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.example.biometrie.*;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;

public class MainActivity extends Activity {
	
	Button inscri = null;
	Button enregDebut = null;
	Button enregFin = null;
	Button photo = null;
	ImageView image = null;
	MediaRecorder recorder;
	MediaController son = null;
	Timer timer = new Timer();
	ProgressDialog myProgressDialog; 
	
	private OnClickListener clickListenerInscri = new View.OnClickListener() {
	    @SuppressWarnings("deprecation")
		@Override
	    public void onClick(View v) {
	    		
	    	
	    }
	  };
	  
	  private OnClickListener prendreUnePhoto = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, 0);
			
			
		}
	};
	  
	
	  private OnClickListener touchListenerEnre = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			enregDebut.setEnabled(false);
	        enregFin.setEnabled(true);
			
			recorder = new MediaRecorder();
			
			
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

			String path = Environment.getExternalStorageDirectory().getAbsolutePath()
					 + "/Music/" +  System.currentTimeMillis() +".mp4";
			
			recorder.setOutputFile(path);
			System.out.println(path);
			try {
				recorder.prepare();
				recorder.start();
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
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
	        
			
		}
	};

	
	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 
		setContentView(R.layout.activity_main);
		
		//Database db = new Database();
		//db.Clear();
		
		Intent intent = new Intent(MainActivity.this, Accueil.class);
   		startActivity(intent);
	   
		
		
		
		/*inscri = (Button)findViewById(R.id.inscription);
		enregDebut = (Button)findViewById(R.id.enregistrer);
		enregFin = (Button)findViewById(R.id.enregistrerFin);
		photo = (Button)findViewById(R.id.photer);
		image = (ImageView)findViewById(R.id.imageView1);
		son = (MediaController)findViewById(R.id.sonEnregistre);
		
		inscri.setOnClickListener(clickListenerInscri);
		enregDebut.setOnClickListener(touchListenerEnre);
		enregFin.setOnClickListener(FinEnreg);*/
		//photo.setOnClickListener(prendreUnePhoto);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 @Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	      // TODO Auto-generated method stub
	      super.onActivityResult(requestCode, resultCode, data);
	      Bitmap bp = (Bitmap) data.getExtras().get("data");
	      
	      image.setImageBitmap(bp);
	   }



	 protected void onStop() {
		    super.onStop();
		    
	 }
	 
	 
	
	
	

}

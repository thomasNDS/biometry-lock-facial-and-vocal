package org.opencv.samples.tutorial3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Tutorial3Activity extends Activity implements CvCameraViewListener2, OnTouchListener {
    private static final String TAG = "OCVSample::Activity";

    private Tutorial3View mOpenCvCameraView;
    private SubMenu sousMenu1;
    private SubMenu sousMenu2;
    private SubMenu sousMenu3;
    private Mat last;
    private ArrayList<Scene> scenes = new ArrayList<Scene>();
    Scene refScene;
    ProgressDialog progress;
    Bitmap bmp;
    View v;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.setOnTouchListener(Tutorial3Activity.this);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public Tutorial3Activity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.tutorial3_surface_view);

        mOpenCvCameraView = (Tutorial3View) findViewById(R.id.tutorial3_activity_java_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
    	last = inputFrame.rgba();
    	return last;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.sousMenu1 = menu.addSubMenu(1,Menu.FIRST, 0, "Retour");
        this.sousMenu2 = menu.addSubMenu(1,Menu.FIRST+1,1,"Comparer");
        this.sousMenu3 = menu.addSubMenu(1,Menu.FIRST+2,1,"Référence");
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case Menu.FIRST:
    		startActivity(new Intent(Tutorial3Activity.this, MainActivity.class));
    		break;
    	case Menu.FIRST+1:
    		compareClick(v);
    		break;
    	case Menu.FIRST+2:
    		takePic2(v);
    		break;
    	}
    	return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG,"onTouch event");
        
        //take pict 1
        Scene scene = new Scene(last);
		scenes.add(scene);
		
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateandTime = sdf.format(new Date());
        String fileName = Environment.getExternalStorageDirectory().getPath() +
                               "/sample_picture_" + currentDateandTime + ".jpg";
        mOpenCvCameraView.takePicture(fileName);
        Toast.makeText(this, fileName + " saved", Toast.LENGTH_SHORT).show();
        return false;
    }
    
    public void takePic2(View w) {
		Mat im = last.clone();
		// Imgproc.cvtColor(im, im, Imgproc.COLOR_BGR2RGB);
		Bitmap bmp = Bitmap.createBitmap(im.cols(), im.rows(),
				Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(im, bmp);
		//matchDrawArea.setImageBitmap(bmp);
		refScene = new Scene(last);
	}
    
	public void compareClick(View w) {
		if (scenes.size() == 0) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("No scenes.");
			alertDialog
					.setMessage("You should add scenes to compare the reference image.");
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			alertDialog.show();
		} else if (refScene == null) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("No reference image.");
			alertDialog.setMessage("You should take a reference image to compare with the scenes you have taken before.");
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});

			alertDialog.show();

		} else {
			new BetterComparePics(this).execute();
		}
	}

	class BetterComparePics extends AsyncTask<Void, Integer, SceneDetectData> {
		Context context;

		public BetterComparePics(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			progress = new ProgressDialog(context);
			progress.setCancelable(false);
			progress.setMessage("Starting to Compare Images");
			progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progress.setProgress(1);
			progress.setMax(scenes.size());
			progress.show();
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			progress.setProgress(values[0]);
			super.onProgressUpdate(values);
		}

		@Override
		protected SceneDetectData doInBackground(Void... params) {
			long s = System.currentTimeMillis();
			Scene max = null;
			SceneDetectData maxData = null;
			int maxDist = -1;
			int idx = -1;
			for (int i = 0; i < scenes.size(); i++) {
				Scene scn = scenes.get(i);
				SceneDetectData data = refScene.compare(scn);
				int currDist;
				currDist = data.dist_matches;
				if (currDist > maxDist) {
					max = scn;
					maxData = data;
					maxDist = currDist;
					idx = i;
				}
				this.publishProgress(i + 1);
			}

			bmp = maxData.bmp;
			long e = System.currentTimeMillis();
			maxData.elapsed = e - s;
			maxData.idx = idx;

			return maxData;
		}

		@Override
		protected void onPostExecute(SceneDetectData maxData) {
			// info.setText(result);
			progress.dismiss();

			final Dialog settingsDialog = new Dialog(context);
			settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout, null));
			ImageView im = (ImageView) settingsDialog.findViewById(R.id.imagePopup);
			Button dismiss = (Button) settingsDialog.findViewById(R.id.dismissBtn);
			TextView info = (TextView) settingsDialog.findViewById(R.id.infoText);
				
			im.setImageBitmap(bmp);
			/*dismiss.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					settingsDialog.dismiss();
				}
			});*/

			info.setText(maxData.toString());

			settingsDialog.show();

			super.onPostExecute(maxData);
		}
	}
    
}

package org.opencv.samples.tutorial3;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;

public class Scene {
	final Mat image;
	final Mat descriptors = new Mat();
	final MatOfKeyPoint keypoints = new MatOfKeyPoint();
	boolean firstTime = true;
	
	public Scene(Mat image) {
		this.image = image.clone();
		// DetectUtility.analyze(image, keypoints, descriptors);
	}
	public void preCompute() {
		if (firstTime) {
			DetectUtility.analyze(image, keypoints, descriptors);
			firstTime = false;
		}
	}

	public SceneDetectData compare(Scene frame) {
		// Info to store analysis stats
		SceneDetectData s = new SceneDetectData();

		// Detect key points and compute descriptors for inputFrame
		Mat f_descriptors = frame.descriptors;

		this.preCompute();
		frame.preCompute();
		

		// Compute matches
		MatOfDMatch matches = DetectUtility.match(descriptors, f_descriptors);
		
		s.original_key1 = (int) descriptors.size().height;
		s.original_key2 = (int) f_descriptors.size().height;

		s.original_matches = (int) matches.size().height;
		return s;
	}
}

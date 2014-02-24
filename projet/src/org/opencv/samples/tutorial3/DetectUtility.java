package org.opencv.samples.tutorial3;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;

public class DetectUtility {
	final static FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
	final static DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.BRIEF);			
	final static DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
	
	static void analyze(Mat image, MatOfKeyPoint keypoints, Mat descriptors){
		detector.detect(image, keypoints);
		extractor.compute(image, keypoints, descriptors);
	}
	
	static MatOfDMatch match(Mat desc1, Mat desc2){
		MatOfDMatch matches = new MatOfDMatch();
		matcher.match(desc1, desc2, matches);	
		return matches;
	}
}

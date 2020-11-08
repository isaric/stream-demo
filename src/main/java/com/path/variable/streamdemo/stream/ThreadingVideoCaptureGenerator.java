package com.path.variable.streamdemo.stream;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class ThreadingVideoCaptureGenerator implements Runnable{

    private byte[] currentImage;

    private final VideoCapture videoCapture;

    public ThreadingVideoCaptureGenerator(String path) {
        this.videoCapture = new VideoCapture(path);
    }

    @Override
    public void run() {
        System.out.println("started run");
        while (true) {
            if (videoCapture.isOpened()) {
                var mat = new Mat();
                videoCapture.read(mat);
                var ofbytes = new MatOfByte();
                Imgcodecs.imencode(".jpg", mat, ofbytes);
                byte[] array = ofbytes.toArray();
                mat.release();
                ofbytes.release();
                this.currentImage = array;
            } else throw new IllegalStateException("Camera is closed!");
        }
    }

    public byte[] getImage() {
        return currentImage;
    }
}

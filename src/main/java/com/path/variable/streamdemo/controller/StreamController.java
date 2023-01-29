package com.path.variable.streamdemo.controller;

import com.path.variable.streamdemo.stream.ThreadingVideoCaptureGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/video")
public class StreamController {

    private static final Logger logger = LoggerFactory.getLogger(StreamController.class);

    private final List<ThreadingVideoCaptureGenerator> captures;

    @Autowired
    public StreamController(List<ThreadingVideoCaptureGenerator> captures) {
        this.captures = captures;
    }


    @GetMapping("/stream/{index}")
    public void getStreamForIndex(@PathVariable int index, HttpServletResponse response) throws IOException {
        var generator = captures.get(index);
        OutputStream stream = response.getOutputStream();
        response.setContentType("multipart/x-mixed-replace; boundary=image");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, pre-check=0, post-check=0, max-age=0");
        response.setHeader("Expires", "0");
        response.setHeader("Max-Age", "0");
        stream.write(("\r\n--image\r\n").getBytes());
        try {
            while (true) {
                byte[] imageBytes = generator.getImage();
                stream.write(("Content-type: image/jpeg\r\n" +
                        "Content-Length: " + imageBytes.length + "\r\n" +
                        "\r\n").getBytes());
                stream.write(imageBytes);
                stream.write(("\r\n--image\r\n").getBytes());
            }
        } catch (Exception ex) {
            logger.info("Client closed connection or link to camera broke", ex);
            stream.flush();
            stream.close();
        }

    }
}

package com.path.variable.streamdemo;

import com.path.variable.streamdemo.stream.ThreadingVideoCaptureGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

@Configuration
public class StreamConfiguration implements AsyncConfigurer {

    @Value("${camera.url}")
    private String cameraUrl;

    @Bean
    public ThreadingVideoCaptureGenerator getThreadingGenerator() {
        var generator = new ThreadingVideoCaptureGenerator(cameraUrl);
        var t = new Thread(generator);
        t.setName("videoGenerator");
        t.start();
        return generator;
    }
}

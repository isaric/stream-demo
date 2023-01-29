package com.path.variable.streamdemo;

import com.path.variable.streamdemo.stream.ThreadingVideoCaptureGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class StreamConfiguration implements AsyncConfigurer {

    @Value("${cameras}")
    private List<String> cameras;

    @Bean
    public List<ThreadingVideoCaptureGenerator> getCaptures() {
        List<ThreadingVideoCaptureGenerator> captures = new ArrayList<>();
        int count = 0;
        for (String camera : cameras) {
            var generator = new ThreadingVideoCaptureGenerator(camera);
            var t = new Thread(generator);
            t.setName(String.format("videoGenerator%d",count++));
            t.start();
            captures.add(generator);
        }
        return captures;
    }
}

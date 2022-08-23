package com.example.test1;

import com.example.test1.contraller.Pusher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class Test1Application {

    public static void main(String[] args) {
        SpringApplication.run(Test1Application.class, args);
    }

    //这里配置推送的时间（秒，分，时，天，月）
    @Scheduled(cron = "30 * * * * *")
    public void goodMorning() throws IOException {
        Pusher.push();
    }

}

package com.ibm.codeengine.kafka.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class KafkaCeJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaCeJobApplication.class, args);
    }

    @Component
    public class KafkaConsumer {

        @KafkaListener(topics = "my-topic", groupId = "my-group")
        public void listen(String event) {
            System.out.println("Received event: " + event);
            //TODO: Write Record
        }
    }
}

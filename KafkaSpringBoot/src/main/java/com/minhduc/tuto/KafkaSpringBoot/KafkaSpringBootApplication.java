package com.minhduc.tuto.KafkaSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaSpringBootApplication implements ApplicationRunner {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringBootApplication.class, args);
	}

	/**
	 * to publish msg
	 * 
	 * @param msg
	 */
	public void sendMessage(String msg) {
		System.out.println("Send msg: " + msg);
		kafkaTemplate.send("springboot-kafka", msg);
	}

	@KafkaListener(topics = "springboot-kafka", groupId = "group-spring-app")
	public void listen(String message) {
		System.out.println("Received Messasge in group (group-id): " + message);
	}

	/**
	 * to send msg to kafka server after app started
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Send msg to kafka server");
		for (int i = 0; i < 100; i++) {
			sendMessage("Hi Welcome to Spring For Apache Kafka. Msg " + i);
			Thread.sleep(1000);
		}
	}
}

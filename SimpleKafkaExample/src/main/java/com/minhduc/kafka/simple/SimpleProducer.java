package com.minhduc.kafka.simple;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleProducer {
	private static final Logger log = LoggerFactory.getLogger(SimpleProducer.class);

	public static void main(String[] args) throws Exception {
		log.info("I am a Kafka Producer");
		String bootstrapServers = "localhost:9092";

		// create Producer properties
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// create the producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

		for (int counter = 0; counter <= 10; counter++) {
			for (int i = 0; i < 10; i++) {
				Thread.sleep(1000);
				int index = counter * 10 + i;
				log.info("Sent msg: " + index);
				// create a producer record
				ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("demo_java",
						"hello world " + index);
				// send data - asynchronous
				producer.send(producerRecord);
				// flush data - synchronous
				producer.flush();

			}
			Thread.sleep(10 * 1000);
		}

		// flush and close producer
		producer.close();
	}
}

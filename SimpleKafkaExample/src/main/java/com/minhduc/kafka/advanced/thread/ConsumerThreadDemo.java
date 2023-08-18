package com.minhduc.kafka.advanced.thread;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerThreadDemo {

	private static final Logger log = LoggerFactory.getLogger(ConsumerThreadDemo.class);

	/**
	 * Running a Java Consumer in a separate thread allows you to perform other
	 * tasks in the main thread.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		ConsumerWorker worker = new ConsumerWorker();
		new Thread(worker, "ConsumerWorkerThread").start();

		// add the shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					worker.shutdown();
				} catch (InterruptedException e) {
					log.error("Error shutting down consumer", e);
				}
			}
		}));
	}

	private static class ConsumerWorker implements Runnable {

		private static final Logger log = LoggerFactory.getLogger(ConsumerWorker.class);
		private CountDownLatch countDownLatch;
		private Consumer<String, String> consumer;

		@Override
		public void run() {
			log.info("I'm Consumer Worker!");
			// wait until countdown = 0, than other clients will be executed
			countDownLatch = new CountDownLatch(1);
			final Properties properties = new Properties();

			// set consumer properties
			properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-kafka-thread-application");
			properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

			// init Kafka Consumer
			consumer = new KafkaConsumer<>(properties);
			consumer.subscribe(Collections.singleton("demo_java"));

			try {
				while (true) {
					ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));
					for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
						log.info("Getting consumer record key: '" + consumerRecord.key() + "', value: '"
								+ consumerRecord.value() + "', partition: " + consumerRecord.partition()
								+ " and offset: " + consumerRecord.offset() + " at "
								+ new Date(consumerRecord.timestamp()));
					}
				}
			} catch (WakeupException e) {
				log.info("Consumer poll woke up");
			} finally {
				consumer.close();
				countDownLatch.countDown();
			}
		}

		void shutdown() throws InterruptedException {
			consumer.wakeup();
			countDownLatch.await();
			log.info("Consumer closed");
		}

	}
}

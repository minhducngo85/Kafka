import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class SimpleProducer {
    public static void main(String[] args) throws Exception {

	System.out.println("I am a Kafka Producer");

	String bootstrapServers = "localhost:9092";

	// create Producer properties
	Properties properties = new Properties();
	properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

	// create the producer
	KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

	for (int i = 0; i < 10; i++) {
	    // create a producer record
	    ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("demo_java", "hello world " + i);
	    // send data - asynchronous
	    producer.send(producerRecord);

	}
	// flush data - synchronous
	producer.flush();
	// flush and close producer
	producer.close();
    }
}

package hello;

import com.google.common.io.Resources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Producer {
    private static final Properties props = getKafkaProperties();
    protected KafkaProducer<String, String> producer;

    public Producer() {
        producer = new KafkaProducer<String, String>(props);
    }

    public void close() {
        producer.close();
    }

    private static Properties getKafkaProperties() {
        Properties props = new Properties();
        try (InputStream propIS = Resources.getResource("kafka-producer.properties").openStream()) {
            props.load(propIS);
            return props;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties: kafka-producer.properties", e);
        }
    }

    public void send(String topic, String message) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, message);
        try {
            producer.send(producerRecord);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        int index = 0;

        while (true) {
            producer.send("hello", String.valueOf(index));
            index++;
            try {
                Thread.sleep(2000);
            } catch (Exception e) {

            }
        }
    }
}

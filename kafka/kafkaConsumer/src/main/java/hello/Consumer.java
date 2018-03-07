package hello;


import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;

public class Consumer {
    private static final Properties props = getKafkaProperties();
    protected KafkaConsumer<String, String> consumer;
    protected ScheduledExecutorService executor;
    protected List<String> topics = new ArrayList<>();
    protected long pollTime;

    public Consumer(String topic, long pollTime) {
        this.topics.add(topic);
        this.pollTime = pollTime;
    }

    private static Properties getKafkaProperties() {
        Properties props = new Properties();
        try (InputStream propIS = Resources.getResource("kafka-consumer.properties").openStream()) {
            props.load(propIS);
            return props;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties: kafka-consumer.properties", e);
        }
    }

    private void process(ConsumerRecords<?, ?> records) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date) + " process " + records.count());
        for (ConsumerRecord<?, ?> record:records) {
            System.out.println(record.topic() + '#' + record.partition() + '#' + record.offset() + '#' + record.value());
        }
    }

    private void start() {
        consumer = new KafkaConsumer<String, String>(props);

        try {
            consumer.subscribe(topics);
            System.out.println("Topics " + topics + " subscribed");
            while (true) {
                ConsumerRecords<?, ?>records = consumer.poll(pollTime);
                try {
                    consumer.commitSync();
                } catch (CommitFailedException e) {
                    System.out.println(e);
                }
                process(records);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            consumer.close();
            System.out.println("Topics " + topics + " closed");
        }
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer("hello", 10000);
        consumer.start();
    }
}

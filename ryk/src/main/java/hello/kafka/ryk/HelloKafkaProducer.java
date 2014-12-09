package hello.kafka.ryk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.log4j.BasicConfigurator;

/**
 * Equivalent of a hello world for producing messages to kafka queue
 * 
 */
public class HelloKafkaProducer {

  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

  /**
   * The producer
   */
  private Producer<String, String> producer;

  /**
   * The name of the topic to which the messages are produced
   */
  private String topic = "test";

  public HelloKafkaProducer(Properties properties) {
    ProducerConfig producerConfig = new ProducerConfig(properties);
    producer = new Producer<String, String>(producerConfig);
  }


  /**
   * returns a string representation of the formatted current timestamp.
   * 
   * @return
   */
  public String getCurrentTimeStamp() {
    Date now = new Date();
    String strDate = sdf.format(now);
    return strDate;
  }

  /**
   * creates a simple string message containing the current timestamp and produces it to the kafka
   * queue.
   */
  private void produce() {
    String message = "hello at " + getCurrentTimeStamp();
    producer.send(new KeyedMessage<String, String>(topic, message));

  }

  private void close() {
    this.producer.close();
  }

  public static void main(String[] args) {
    BasicConfigurator.configure();
    Properties properties = new Properties();
    properties.put("metadata.broker.list", "localhost:9092");
    properties.put("serializer.class", "kafka.serializer.StringEncoder");
    // properties.put("kafka.group.id", "test-consumer-group");

    HelloKafkaProducer producer = new HelloKafkaProducer(properties);
    for (int i = 0; i < 10; i++) {
      producer.produce();
    }
    producer.close();
  }



}

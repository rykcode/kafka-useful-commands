package hello.kafka.ryk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

/**
 * Equivalent of a hello world for Consuming messages from the kafka queue. Interacts with the kafka
 * queue, consumes the messages on the kafka queue and invokes the business logic on each message.
 * 
 * 
 */
public class HelloKafkaConsumer {
  private final ConsumerConnector consumer;
  private final String topic;
  private ExecutorService executor;

  public HelloKafkaConsumer(String a_zookeeper, String a_groupId, String a_topic) {
    consumer =
        kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig(a_zookeeper,
            a_groupId));
    this.topic = a_topic;
  }

  public void shutdown() {
    if (consumer != null) consumer.shutdown();
    if (executor != null) executor.shutdown();
  }

  public void run(int a_numThreads) {
    Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
    topicCountMap.put(topic, new Integer(a_numThreads));
    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap =
        consumer.createMessageStreams(topicCountMap);
    List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

    // now launch all the threads
    //
    executor = Executors.newFixedThreadPool(a_numThreads);

    // now create an object to consume the messages
    //
    int threadNumber = 0;
    // apply the business logic on each message
    //
    for (final KafkaStream stream : streams) {
      executor.submit(new ConsumerBusinessLogic(stream, threadNumber));
      threadNumber++;
    }
  }

  private static ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId) {
    Properties props = new Properties();
    props.put("zookeeper.connect", a_zookeeper);
    props.put("group.id", a_groupId);
    props.put("zookeeper.session.timeout.ms", "400");
    props.put("zookeeper.sync.time.ms", "200");
    props.put("auto.commit.interval.ms", "1000");

    return new ConsumerConfig(props);
  }

  public static void main(String[] args) {
    String zooKeeper = "192.168.1.119:2181";
    String groupId = "test-consumer";
    String topic = "test";
    int threads = 4;

    HelloKafkaConsumer example = new HelloKafkaConsumer(zooKeeper, groupId, topic);
    example.run(threads);

    try {
      Thread.sleep(10000);
    } catch (InterruptedException ie) {

    }
    example.shutdown();
  }
}
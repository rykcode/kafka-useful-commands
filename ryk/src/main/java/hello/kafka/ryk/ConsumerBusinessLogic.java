package hello.kafka.ryk;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * Contains the business logic that to be applied to each message consumed from the kafka queue.
 * 
 */
public class ConsumerBusinessLogic implements Runnable {
  private KafkaStream m_stream;
  private int m_threadNumber;

  public ConsumerBusinessLogic(KafkaStream a_stream, int a_threadNumber) {
    m_threadNumber = a_threadNumber;
    m_stream = a_stream;
  }

  public void run() {
    ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
    while (it.hasNext())
      System.out.println("Thread " + m_threadNumber + ": " + new String(it.next().message()));
    System.out.println("Shutting down Thread: " + m_threadNumber);
  }
}
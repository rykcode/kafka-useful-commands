ryk_hello_kafka
===============

Starter code for kafka

Prerequisites - Apache Zookeeper
================================
Download zookeeper 3.4.6 and execute the following commands

```mkdir ~/apache-zookeeper; cd ~/apache-zookeeper```

Uncompress the tar file

```tar -zxvf <path to zookeeper tgz file>/zookeeper-3.4.6.tar.gz```

Create symlink to simplify references to zookeeper

```ln -s zookeeper-3.4.6 zookeeper```

Setup the zookeeper config. Zookeeper reads the config from the conf/zoo.cfg file. Zookeeper comes with a sample config file. Copy the file to zoo.cfg

```cp conf/zoo_sample.cfg conf/zoo.cfg```

```vim conf/zoo.cfg```

Change the entry of dataDir to /tmp/zookeeper or /var/zookeeper or any other convenient location.

Make sure that the current user (most likely ubuntu has permissions to write to /var/zookeeper)

Start the zookeeper server

```bin/zkServer.sh start```

If you have used the defaults then the zookeeper server is running at localhost:2181

Verify that zookeeper is running using jps
output of jps should show a process called QuorumPeerMain running

Installing Apache Kafka
=======================

Download a stable version of Apache Kafka from http://kafka.apache.org/downloads.html. As of this date the stable version of kafka is 0.8.1.1 Download the kafka binary created using Scala 2.9.2. 


Create a directory called apache-kafka

```mkdir ~/apache-kafka; cd ~/apache-kafka```

Uncompress the tar file

```tar -zxvf <full path to kafka tgz file>/kafka_2.9.2-0.8.1.1.tgz```


Create symlink to simplify references to kafka

```ln -s kafka_2.9.2-0.8.1.1 kafka```

```vim ~/apache-kafka/kafka/conf/server.properties```

Enter the correct value for zookeeper.connect. If you have not changed the defaults in ```zoo.cfg``` then accept the default value provided ie. ```localhost:2181```

Starting the kafka server

```bin/kafka-server-start.sh config/server.properties```

Create a topic named “test” on the kafka queue

```~/apache-kafka/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test```

Start a consumer that consumes all messages sent to topic=“test”. This consumer is a simple command line program that comes with the kafka code and is used for illustration purposes only. This will print all the consumed messages on the terminal.

```~/apache-kafka/kafka/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning```

Later we will build a consumer using java code. 

Producing messages to the kafka queue
=====================================
For this run the main class ```HelloKafkaProducer.java``` as a Java Application through eclipse.

Consuming messages from the Kafka queue
=======================================
For this run the ```HelloKafkaConsumer.java``` as a Java Application through eclipse.

Other helpful commands
======================
Deleting a topic on the kafka queue. Sometimes you may want to simply start from scratch by deleting the topic from the kafka queue. Use the following command

```bin/kafka-run-class.sh kafka.admin.DeleteTopicCommand --zookeeper localhost:2181 --topic <topicname>```
This command will simply delete the topic but not the underlying data stored in kafka.

Alternately you could do the following
```~/apache-kafka/kafka/bin/kafka-server-stop.sh```
```rm -rf /tmp/kafka``` This is where kafka stores the data.
log in to zookeeper cli 
```~/apache-zookeeper/zookeeper/bin/zkCli.sh ```
delete consumers and brokers
```rmr /consumers ```
```rmr /brokers ```
exit from zookeeper cli
```exit ```

Start the kafka server again
```bin/kafka-server-start.sh config/server.properties```
Create topic if needed 
```~/apache-kafka/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test```

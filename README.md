ryk_hello_kafka
===============

Starter code for kafka

Prerequisites - Apache Zookeeper
================================
Download zookeeper 3.4.6 and execute the following commands
```<mkdir ~/apache-zookeeper; cd ~/apache-zookeeper>```

Uncompress the tar file
```tar -zxvf <path to zookeeper tgz file>/zookeeper-3.4.6.tar.gz```

Create symlink to simplify references to zookeeper
```ln -s zookeeper-3.4.6 zookeeper```

Setup the zookeeper config. Zookeeper reads the config from the conf/zoo.cfg file. Zookeeper comes with a sample config file. Copy the file to zoo.cfg
```cp conf/zoo_sample.cfg conf/zoo.cfg```
```vim conf/zoo.cfg```
Change the entry of dataDir as follows
```dataDir=/var/zookeeper```
Make sure that the current user (most likely ubuntu has permissions to write to /var/zookeeper)

Start the zookeeper server
```bin/zkServer.sh start```
If you have used the defaults then the zookeeper server is running at localhost:2181

Verify that zookeeper is running using jps
output of jps should show a process called QuorumPeerMain running

Installing Apache Kafka
=======================


Other helpful commands
======================

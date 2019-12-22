# Spring-Cloud-Stream-Example
**[example for mac]**

###### Apache-Kafka Binary Distribution [Download](http://ftp.unicamp.br/pub/apache/kafka/2.4.0/kafka_2.12-2.4.0.tgz) [2.12-version]

###### Before strart its necessary (on mac) build kafka applicatin to use all commands. Into kafka folder run:
>./gradlew jar -PscalaVersion=2.12.10

###### Start Zookeeper server:
>bin/zookeeper-server-start.sh config/zookeeper.properties

###### Start Kafka server:
>bin/kafka-server-start.sh config/server.properties

###### Create Topic
>bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test

###### List all avaliable topics
>bin/kafka-topics.sh --list --bootstrap-server localhost:9092

**[click here to see the font of this study example](https://kafka.apache.org/quickstart)**



docker run -itd --name kafka-test -p 9092:9092 \
--link zookeeper-test:zookeeperHost \
--env KAFKA_ADVERTISED_HOST_NAME=localhost \
-e KAFKA_ZOOKEEPER_CONNECT=zookeeperHost:2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
wurstmeister/kafka:2.11-1.0.2

# test producer
docker exec -it kafka-test bash
cd /opt/kafka_2.11-1.0.2/
bin/kafka-topics.sh --create --zookeeper zookeeperHost:2181 --replication-factor 1 --partitions 8 --topic test
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

# test consumer
docker exec -it kafka-test bash
cd /opt/kafka_2.11-1.0.2/
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

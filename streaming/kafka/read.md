#kafka Version: kafka_2.12-3.3.1
#Spark version 3.4.1
#Scala version 2.12.17


# run below command in terminal before running the scripts

#zoo keeper server
# bin/zookeeper-server-start.sh config/zookeeper.properties

# kafka server
# bin/kafka-server-start.sh config/server.properties

# Create a Topic
# bin/kafka-topics.sh --create --topic cryptos --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

#List of topics
# bin/kafka-topics.sh --list --bootstrap-server localhost:9092

#check the kafka connection
# nc -zv localhost 9092

# To Test
# bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic cryptos --from-beginning

# Use startingOffsets = "earliest" or configure offset checkpointing.
# Ensure Kafka topic retention is long enough to handle consumer downtime
# kafka-configs.sh --bootstrap-server localhost:9092 --alter --entity-type topics --entity-name cryptos --add-config retention.ms=86400000  # 24 hours

# run in terminal
# spark-submit --jars ./jars/kafka-clients-3.2.0.jar,./jars/spark-sql-kafka-0-10_2.12-3.4.1.jar,./jars/spark-token-provider-kafka-0-10_2.12-3.4.1.jar,./jars/commons-pool2-2.11.1.jar ./streaming/kafka/crypto_consumer.py




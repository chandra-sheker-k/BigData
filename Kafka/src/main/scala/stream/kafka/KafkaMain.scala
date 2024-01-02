package com
package stream.kafka

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.util.Properties

object KafkaMain {
  def main(args: Array[String]): Unit = {
    println("Hello world!")
    val config = new SparkConf().setAppName("SOME APP NAME").setMaster("local[2]").set("spark.executor.memory", "1g");
    //val sc = new SparkContext(config)
    //val spark = new SparkSession()
    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("ReadWriteKafka")
      .getOrCreate()

    val props:Properties = new Properties()
    props.put("bootstrap.servers","localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks","all")

    //Start zookeeper and kafka server First
    //kafka_2.13-3.6.1/bin/zookeeper-server-start.sh kafka_2.13-3.6.1/config/zookeeper.properties
    //kafka_2.13-3.6.1/bin/kafka-server-start.sh kafka_2.13-3.6.1/config/server.properties

    val topic = "json_topic"

    new PushDataToKafka(spark).dataPush(topic, props)
    new ReadDataFromKafka(spark)readDataFromKafka(topic, props)
    println("done!")
  }
}

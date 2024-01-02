package com
package stream.kafka

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.spark.sql.SparkSession

import java.util
import java.util.Properties

class ReadDataFromKafka(spark: SparkSession) {
  def readDataFromKafka(topic:String, props:Properties): Unit = {
    println("data read from kafka start")
    props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    props.put("group.id", topic)
    val consumer = new KafkaConsumer[String, String](props)
    val topics = util.Arrays.asList(topic)

    consumer.subscribe(topics)
    try{
      while(true){
        val records = consumer.poll(1000)
        records.forEach(p => println(p.value()))
      }

    }finally {
      consumer.close()
    }
  }
}

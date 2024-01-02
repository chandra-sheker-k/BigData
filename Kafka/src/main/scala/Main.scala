package com

import com.stream.kafka.{FakeDataGenerator, PushDataToKafka, ReadDataFromKafka}
import org.apache.spark.{SparkConf, SparkContext}
import faker._
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, KafkaConsumer}
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.SparkSession

import java.util
import java.util.Properties

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world!")
    val config = new SparkConf().setAppName("SOME APP NAME").setMaster("local[2]").set("spark.executor.memory", "1g");
    //val sc = new SparkContext(config)
    //val spark = new SparkSession()
    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("SparkByExamples.com")
      .getOrCreate()


    println("done!")
  }
}

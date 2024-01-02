package com
package stream.kafka

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.spark.sql.SparkSession

import java.time
import java.util.{Properties, UUID}

class PushDataToKafka(spark: SparkSession) {

  def dataPush(topic:String, props:Properties): Unit = {
    val producer = new KafkaProducer[String, String](props)

    val t = new java.util.Timer()
    val task = new java.util.TimerTask {
      def run() = {
        val usersData = new FakeDataGenerator(spark).generateDummyData(10)
        producer.send(new ProducerRecord(topic, "userData" ,usersData.toString()))
        println("data created and pushed")

        //these will create file in
        val todayDate = time.LocalDateTime.now()
        val folderNameformat = f"y=${todayDate.getYear}/m=${todayDate.getMonthValue}/d=${todayDate.getDayOfMonth}" +
          f"/${todayDate.getHour}-${todayDate.getMinute}_${UUID.randomUUID().toString.split("-")(0)}"
        val users = spark.sparkContext.parallelize(usersData)
        users.saveAsTextFile("output/"+folderNameformat)

      }
    }
    t.schedule(task, 1000L, 30000L)
  }
}

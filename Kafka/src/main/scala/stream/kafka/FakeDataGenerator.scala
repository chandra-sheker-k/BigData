package com
package stream.kafka

import org.apache.spark.sql.SparkSession

import java.time
import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.util.Random

class FakeDataGenerator(spark: SparkSession) {
  private val device_list: Array[String] = Array("device_1","device_2","device_3","device_4")

  def generateDummyData(generateRecordsCount:Int): ListBuffer[String] = {
    var usersData = new ListBuffer[String]()
    for (idx <- 1 to generateRecordsCount) {
      val userStr = "{'name':'"+faker.Name.name+"','device_id':'"+device_list(Random.nextInt(device_list.length))+
        "','device_model_id':'"+UUID.randomUUID().toString.split("-")(0)+"','city':'"+
        faker.Address.city+"','timestamp':'"+time.LocalDateTime.now().toString+"'}"
      usersData.append(userStr)
      //producer.send(new ProducerRecord(topic, "userData" ,userStr))
    }
    usersData
  }
}

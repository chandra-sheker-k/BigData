
from datetime import datetime
from pyspark.sql import SparkSession
from pyspark.sql.functions import from_json, col
from pyspark.sql.types import StructType, StringType, DoubleType

spark = SparkSession.builder \
    .appName("CryptoKafkaConsumer") \
    .master("local[*]") \
    .config("spark.streaming.stopGracefullyOnShutdown", "true") \
    .getOrCreate()

spark.sparkContext.setLogLevel("WARN")

# Define the schema of the incoming JSON data
schema = StructType() \
    .add("symbol", StringType()) \
    .add("timestamp", StringType()) \
    .add("price", DoubleType())

df_raw = spark.readStream \
    .format("kafka") \
    .option("kafka.bootstrap.servers", "localhost:9092") \
    .option("subscribe", "cryptos") \
    .option("startingOffsets", "latest") \
    .load()

df_parsed = df_raw.selectExpr("CAST(value AS STRING) as json_str") \
    .select(from_json(col("json_str"), schema).alias("data")) \
    .select("data.*")

def write_to_file(batch_df, batch_id):
    if not batch_df.rdd.isEmpty():
        timestamp = datetime.now().strftime("%Y-%m-%d-%H-%M")
        output_path = f"./crypto_output/cryptos_{timestamp}"
        batch_df.coalesce(1).write.mode("overwrite").option("header", "true").csv(output_path)

query = df_parsed.writeStream \
    .outputMode("append") \
    .foreachBatch(write_to_file) \
    .option("path", "./crypto_output/") \
    .option("checkpointLocation", "./checkpoints/crypto_csv/") \
    .trigger(processingTime='5 minutes')\
    .start()
# for instance update remove the processingTime

query.awaitTermination()

# run below command in terminal
# spark-submit --jars ./jars/kafka-clients-3.2.0.jar,./jars/spark-sql-kafka-0-10_2.12-3.4.1.jar,./jars/spark-token-provider-kafka-0-10_2.12-3.4.1.jar,./jars/commons-pool2-2.11.1.jar \
# ./streaming/kafka/crypto_consumer.py
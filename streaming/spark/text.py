from pyspark import SparkContext
from pyspark.streaming import StreamingContext

def create_streaming_context(host, port, interval, checkpoint_dir, log_dir):
    sc = SparkContext("local[2]", "local_stream_text")
    ssc = StreamingContext(sc, interval)

    ssc.checkpoint(checkpoint_dir)

    try:
        lines = ssc.socketTextStream(host, port)
        #lines = ssc.textFileStream(log_dir)
        words = lines.flatMap(lambda line: line.split(" "))
        word_counts = words.map(lambda word: (word, 1)).reduceByKey(lambda x, y: x + y)
        word_counts.pprint()
        return ssc
    except Exception as e:
        raise e

#open your shell and run this, nc -lc 1111
def local_stream_text(host="localhost", port=1111, interval=5, checkpoint_dir="./checkpoint", log_dir="/console/log/dir"):
    def create_context():
        return create_streaming_context(host, port, interval, checkpoint_dir)

    ssc = StreamingContext.getOrCreate(checkpoint_dir, create_context)

    try:
        ssc.start()
        ssc.awaitTermination()
    except Exception as e:
        print("Streaming error:", e)

def stream_from_log(log_dir, interval=5, checkpoint_dir="./checkpoint"):
    def create_context():
        return create_streaming_context("","", interval, checkpoint_dir, log_dir)

    ssc = StreamingContext.getOrCreate(checkpoint_dir, create_context)

    try:
        ssc.start()
        ssc.awaitTermination()
    except Exception as e:
        print("Streaming error:", e)


stream_from_log("/console/log/dir")

local_stream_text()

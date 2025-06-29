import time
import yfinance as yf
from kafka import KafkaProducer
import json
import logging

# Initialize Kafka producer
producer = KafkaProducer(
    bootstrap_servers='localhost:9092',
    retries=5,
    acks='all',
    linger_ms=100,
    value_serializer=lambda v: json.dumps(v).encode('utf-8')  # Send JSON messages
)

cryptos = ['BTC-USD', 'ETH-USD', 'USDT-USD', 'BNB-USD', 'USDC-USD']

try:
    while True:
        failed_msgs = []
        for crypt in cryptos:
            latest = None
            try:
                data = yf.download(tickers=crypt, period='1d', interval='1m')
                latest = data.tail(1)
                if not latest.empty:
                    message = {
                        "symbol": crypt,
                        "timestamp": latest.index[0].strftime('%Y-%m-%d %H:%M:%S'),
                        "price": round(float(latest['Close'].iloc[0]), 4)
                    }
                    print(f"Sending: {message}")
                    producer.send('cryptos', message)
            except Exception as e:
                failed_msgs.append({
                    "symbol": crypt,
                    "data":latest,
                    "error": str(e)
                })
                logging.error(f"Error fetching/sending {crypt}: {e}")
        if len(failed_msgs) > 0:
            logging.warning(f"Failed to send messages: {failed_msgs}")
            #Or we can write to a local file
        time.sleep(30)

except KeyboardInterrupt:
    print("Stopped by user.")
finally:
    producer.flush()
    producer.close()

# run below command in terminal
# python ./streaming/kafka/crypto_producer.py


# kafka-console-consumer --bootstrap-server localhost:9092 --topic cryptos --from-beginning

package com.blogspot.benbai123.rocketmq.gettingstarted;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class TestProducer {
	public static void main(String[] args) throws Exception {
		// Instantiate with a producer group name.
		DefaultMQProducer producer = new DefaultMQProducer("TestProducerGroup");
		// Specify name server addresses.
		producer.setNamesrvAddr("localhost:9876");
		
		// Launch the instance.
		producer.start();
		
		for (int i = 0; i < 10; i++) {
			// Create a message instance, specifying topic, tag and message body.
			Message msg = new Message("TestTopic" /* Topic */, "TestTag" /* Tag */,
					("Hello RocketMQ " + i).getBytes("UTF-8") /* Message body */
			);
			// Call send message to deliver message to one of brokers.
			SendResult sendResult = producer.send(msg);
			System.out.println(sendResult.getSendStatus());
		}
		// Shut down once the producer instance is not longer in use.
		producer.shutdown();
	}
}

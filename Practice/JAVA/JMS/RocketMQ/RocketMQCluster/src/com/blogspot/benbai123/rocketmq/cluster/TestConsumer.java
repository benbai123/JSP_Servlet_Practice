package com.blogspot.benbai123.rocketmq.cluster;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

public class TestConsumer {
	public static void main(String[] args) throws Exception {

		// Instantiate with specified consumer group name.
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TestConsumerGroup");
		// Specify name server addresses.
		consumer.setNamesrvAddr("localhost:9487;localhost:9478");

		// Subscribe one more topics to consume with any Tag.
		consumer.subscribe("TestTopic", "*");

		// Register callback to execute on arrival of messages fetched from brokers.
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				for (MessageExt msg : msgs) {
					try {
						System.out.println("receive " + new String(msg.getBody(), "UTF-8"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		// Launch the consumer instance.
		consumer.start();
		System.out.printf("Consumer Started.%n");
	}
}

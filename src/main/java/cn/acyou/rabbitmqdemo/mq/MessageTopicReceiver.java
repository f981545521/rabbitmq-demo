package cn.acyou.rabbitmqdemo.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ExchangeTypes.FANOUT 广播模式
 *
 * fanout: 所有bind到此exchange的queue都可以接收消息，广播
 * direct: 通过routingKey和exchange决定的那个唯一的queue可以接收消息
 * topic:所有符合routingKey(此时可以是一个表达式)的routingKey所bind的queue可以接收消息
 * headers：通过headers来决定把消息发给哪些queue，用的比较少
 *
 *      *(星号)可以完全代替一个词。
 *      #(散列)可以代替零或多个单词。
 * @author youfang
 * @version [1.0.0, 2020-9-5 下午 02:27]
 **/
@Slf4j
@Component
public class MessageTopicReceiver {

    @RabbitListener(
            bindings =
            @QueueBinding(
                    value = @Queue(value = "topic-good-queue", durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = "topic-exchange", durable = "true", type = ExchangeTypes.TOPIC),
                    key = "topic.good"
            ),
            concurrency = "1",
            ackMode = "AUTO"
    )
    public void messageReceiver1(String message) {
        log.info("RabbitMQ ————————————> MessageTopicReceiver[GOOD] : " + message);
    }


    @RabbitListener(
            bindings =
            @QueueBinding(
                    value = @Queue(value = "topic-bad-queue", durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = "topic-exchange", durable = "true", type = ExchangeTypes.TOPIC),
                    key = "topic.bad"
            ),
            concurrency = "1",
            ackMode = "AUTO"
    )
    public void messageReceiver2(String message) {
        log.info("RabbitMQ ————————————> MessageTopicReceiver[BAD] : " + message);
    }

    @RabbitListener(
            bindings =
            @QueueBinding(
                    value = @Queue(value = "topic-normal-queue", durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = "topic-exchange", durable = "true", type = ExchangeTypes.TOPIC),
                    key = "topic.#"
            ),
            concurrency = "1",
            ackMode = "AUTO"
    )
    public void messageReceiver3(String message) {
        log.info("RabbitMQ ————————————> MessageTopicReceiver[NORMAL] : " + message);
    }
}

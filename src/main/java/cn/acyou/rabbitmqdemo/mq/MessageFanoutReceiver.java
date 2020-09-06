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
 * @author youfang
 * @version [1.0.0, 2020-9-5 下午 02:27]
 **/
@Slf4j
@Component
public class MessageFanoutReceiver {

    @RabbitListener(
            bindings =
            @QueueBinding(
                    value = @Queue(value = "user-message-queue", durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = "user-message-exchange", durable = "true", type = ExchangeTypes.FANOUT),
                    key = "user-message.*"
            ),
            concurrency = "1",
            ackMode = "AUTO"
    )
    public void messageReceiver1(String message) {
        log.info("RabbitMQ ————————————> MessageFanoutReceiver[1] : " + message);
    }
    @RabbitListener(
            bindings =
            @QueueBinding(
                    value = @Queue(value = "member-message-queue", durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = "user-message-exchange", durable = "true", type = ExchangeTypes.FANOUT),
                    key = "user-message.*"
            ),
            concurrency = "1",
            ackMode = "AUTO"
    )
    public void messageReceiver2(String message) {
        log.info("RabbitMQ ————————————> MessageFanoutReceiver[2] : " + message);
    }
}

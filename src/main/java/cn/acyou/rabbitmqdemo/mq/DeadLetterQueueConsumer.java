package cn.acyou.rabbitmqdemo.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static cn.acyou.rabbitmqdemo.conf.RabbitMQConfig.QUEUE_ORDER_EXPIRE_DELAY;
import static cn.acyou.rabbitmqdemo.conf.RabbitMQConfig.EXCHANGE_ORDER_EXPIRE_DELAY;
import static cn.acyou.rabbitmqdemo.conf.RabbitMQConfig.ROUTING_KEY_ORDER_EXPIRE_DELAY;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-24 下午 09:29]
 **/
@Slf4j
@Component
public class DeadLetterQueueConsumer {


    @RabbitListener(
            bindings =
            @QueueBinding(
                    value = @Queue(value = QUEUE_ORDER_EXPIRE_DELAY, durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = EXCHANGE_ORDER_EXPIRE_DELAY, durable = "true", type = ExchangeTypes.DIRECT, delayed = "true", arguments = {
                            @Argument(name = "x-delayed-type", value = "direct")
                    }),
                    key = ROUTING_KEY_ORDER_EXPIRE_DELAY
            ),
            concurrency = "1",
            ackMode = "AUTO"
    )
    public void delayQueueConsumer(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("延时队列收到消息：{}", msg);
    }
}

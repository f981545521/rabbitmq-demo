package cn.acyou.rabbitmqdemo.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author youfang
 * @version [1.0.0, 2020-9-5 下午 02:27]
 **/
@Slf4j
//@Component
public class DelayReceiver {

    @RabbitListener(
            bindings =
            @QueueBinding(
                    value = @Queue(value = "my-delay-queue", durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = "dead-letter-routing-key", durable = "true", type = ExchangeTypes.TOPIC, delayed = "true")
            ),
            ackMode = "AUTO"
    )
    public void process(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        log.info("RabbitMQ ————————————> DelayReceiver : " + message);
    }
}

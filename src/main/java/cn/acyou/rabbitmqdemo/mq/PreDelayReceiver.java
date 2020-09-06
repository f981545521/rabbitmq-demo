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
 * @version [1.0.0, 2020-9-5 下午 11:23]
 **/
@Slf4j
//@Component
public class PreDelayReceiver {
    @RabbitListener(
            bindings =
            @QueueBinding(
                    value = @Queue(value = "pre-delay-queue", durable = "true", autoDelete = "false", arguments = {
                                @Argument(name = "x-delayed-type", value = "direct"),
                                @Argument(name = "x-dead-letter-exchange", value = "dead-letter-exchange"),
                                @Argument(name = "x-dead-letter-routing-key", value = "dead-letter-routing-key")
                    }),
                    exchange = @Exchange(value = "pre-delay-exchange", durable = "true", type = ExchangeTypes.TOPIC, delayed = "true"),
                    key = "pre.delay"
            ),
            ackMode = "AUTO"
    )
    public void process(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        log.info("RabbitMQ ————————————> PreDelayReceiver : " + message);
    }
}

package cn.acyou.rabbitmqdemo.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author youfang
 * @version [1.0.0, 2020-9-5 下午 02:27]
 **/
@Slf4j
//@Component
public class MessageReceiver {

    @RabbitListener(queues = "user-delay-message")
    public void process(String message) {
        log.info("RabbitMQ ————————————> MessageReceiver : " + message);
    }
}

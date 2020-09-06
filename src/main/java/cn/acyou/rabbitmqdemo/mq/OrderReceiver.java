package cn.acyou.rabbitmqdemo.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
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
public class OrderReceiver {

    @RabbitListener(
            bindings =
                @QueueBinding(
                        //声明队列，value 为 queueName, durable 表示队列是否持久化, autoDelete 表示没有消费者之后队列是否自动删除
                        value = @Queue(value = "order-queue", durable = "true", autoDelete = "false", arguments = {
                                //Message TTL:  message在队列中可以存活多长时间，以毫秒为单位；发布的消息在queue时间超过了你设定的时间就会被删除掉。
                                //@Argument(name = "x-message-ttl", value = "60000 "),
                                //Auto expire:  当前的queue在指定的时间内，没有consumer、basic.get也就是未被访问，就会被删除。
                                //@Argument(name = "x-expires", value = "60000 "),
                                //Max length:   消息条数限制,该参数是非负整数值。限制加入queue中消息的条数。先进先出原则，超过10条后面的消息会顶替前面的消息。
                                //@Argument(name = "x-max-length", value = "60000 "),
                                //Max length bytes:　消息容量限制,该参数是非负整数值。该参数和x-max-length目的一样限制队列的容量，但是这个是靠队列大小（bytes）来达到限制。
                                //@Argument(name = "x-expires", value = "60000 "),
                                //Overflow behaviour:设置队列溢出行为。这决定了当队列达到最大长度时，消息会发生什么。有效值为drop head或reject publish。
                                //@Argument(name = "x-overflow", value = "drop-head "),
                                //在x-message-ttl时间到期后把消息放到x-dead-letter-routing-key和x-dead-letter-exchange指定的队列中达到延迟队列的目的。
                                //@Argument(name = "x-dead-letter-exchange", value = "dead-letter-exchange"),
                                //@Argument(name = "x-dead-letter-routing-key", value = "dead-letter-routing-key")

                        }),
                        //声明交换机， type 指定消息投递策略，我们这里用的 topic 方式
                        exchange = @Exchange(value = "order-exchange", durable = "true", type = ExchangeTypes.TOPIC),
                        //在 topic 方式下，这个就是我们熟知的 routingKey
                        key = "order.*"
                ),
            concurrency = "1",// 并行消费
            ackMode = "MANUAL"// 参见 AcknowledgeMode.MANUAL
    )
    public void process(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        log.info("RabbitMQ ————————————> OrderReceiver : " + message);
    }
}

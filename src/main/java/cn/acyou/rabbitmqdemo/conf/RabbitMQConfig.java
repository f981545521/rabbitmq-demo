package cn.acyou.rabbitmqdemo.conf;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-24 下午 09:46]
 **/
@Configuration
@EnableTransactionManagement
public class RabbitMQConfig {
    //订单超时队列
    public static final String QUEUE_ORDER_EXPIRE_DELAY =    "delay.order.expire.queue";
    //订单超时交换机
    public static final String EXCHANGE_ORDER_EXPIRE_DELAY = "delay.order.expire.exchange";
    //订单路由名称
    public static final String ROUTING_KEY_ORDER_EXPIRE_DELAY =   "delay.order.expire.routingkey";

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplateNew(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        //# 注意：发布确认和事务。(两者不可同时使用)在channel为事务时，不可引入确认模式；同样channel为确认模式下，不可使用事务。
        //template.setChannelTransacted(true);
        template.setConfirmCallback(new RabbitTemplateConfirmCallback());
        template.setReturnCallback(new RabbitTemplateReturnCallback());
        return template;
    }
}

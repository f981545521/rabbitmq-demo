package cn.acyou.rabbitmqdemo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static cn.acyou.rabbitmqdemo.conf.RabbitMQConfig.EXCHANGE_ORDER_EXPIRE_DELAY;
import static cn.acyou.rabbitmqdemo.conf.RabbitMQConfig.ROUTING_KEY_ORDER_EXPIRE_DELAY;

/**
 * @author youfang
 * @version [1.0.0, 2020-9-5 下午 02:29]
 **/
@RestController
public class MessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/test1", method = {RequestMethod.GET})
    @ResponseBody
    public String test1(String message) {
        rabbitTemplate.convertAndSend("user-message-exchange", "user-message-route", message);
        return "success";
    }
    @RequestMapping(value = "/test2", method = {RequestMethod.GET})
    @ResponseBody
    public String test2(String exchange, String routingkey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingkey, message);
        return "success";
    }
    @RequestMapping(value = "/testDelayQueue", method = {RequestMethod.GET})
    @ResponseBody
    public String testDelayQueue(String msg, int delayTime) {
        Integer millisecond = delayTime * 1000;
        rabbitTemplate.convertAndSend(EXCHANGE_ORDER_EXPIRE_DELAY, ROUTING_KEY_ORDER_EXPIRE_DELAY, msg, a -> {
            a.getMessageProperties().setDelay(millisecond);
            return a;
        });
        return "success";
    }
}

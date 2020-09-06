package cn.acyou.rabbitmqdemo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "/test3", method = {RequestMethod.GET})
    @ResponseBody
    public String test3(String msg, int delayTime) {
        Integer millisecond = delayTime * 1000;
        rabbitTemplate.convertAndSend("delay.order.expire.exchange", "delay.order.expire.routingkey", msg, a -> {
            a.getMessageProperties().setDelay(millisecond);
            return a;
        });
        return "success";
    }
}

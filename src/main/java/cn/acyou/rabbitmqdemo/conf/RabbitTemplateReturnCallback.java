package cn.acyou.rabbitmqdemo.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 如果消息没有到exchange,则confirm回调,ack=false
 * 如果消息到达exchange,则confirm回调,ack=true
 * exchange到queue成功,则不回调return
 * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
 *                                  `template.setMandatory(true);`
 *
 * RabbitMq ReturnCallback回调
 * @author youfang
 * @version [1.0.0, 2020-4-25 下午 01:15]
 **/
@Slf4j
public class RabbitTemplateReturnCallback implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("RabbitTemplate returned回调--:message:" + message + ",replyText:" + replyText + ",routingKey:" + routingKey);
    }
}

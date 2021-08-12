package com.lee.jxmall.order.listener;

import com.lee.common.to.mq.SecKillOrderTo;
import com.lee.jxmall.order.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RabbitListener(queues = "order.seckill.order.queue")
public class OrderSecKillListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void listener(SecKillOrderTo orderTo, Channel channel, Message message) throws IOException {

        try {
            log.info("准备创建秒杀单的详细信息...");
            orderService.createSecKillOrder(orderTo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
}

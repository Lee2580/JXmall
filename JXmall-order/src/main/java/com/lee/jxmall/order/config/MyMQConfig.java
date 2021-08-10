package com.lee.jxmall.order.config;

import com.lee.jxmall.order.entity.OrderEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyMQConfig {

    /**
     * 监听队列
     * @param entity
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(queues = "order.release.order.queue")
    public void listener(OrderEntity entity, Channel channel, Message message) throws IOException {
        System.out.println("收到过期订单信息，准备关闭订单"+entity.getOrderSn());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    /**
     * 延时队列
     *  容器中的组建Queue Exchange Binding 都会自动创建（前提是RabbitMQ没有）
     *  RabbitMQ中有，属性发生变化也不会覆盖
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {

        // String name, boolean durable, boolean exclusive, boolean autoDelete,
        //			@Nullable Map<String, Object> arguments :
        Map<String, Object> arguments = new HashMap<>();
        //死信交换机
        arguments.put("x-dead-letter-exchange", "order-event-exchange");
        //死信路由键
        arguments.put("x-dead-letter-routing-key", "order.release.order");
        //消息过期时间，单位ms ，1分钟
        arguments.put("x-message-ttl", 60000);
        return new Queue("order.delay.queue", true, false, false, arguments);
    }

    /**
     * 普通队列，接收已经到期的延时消息
     * @return
     */
    @Bean
    public Queue orderReleaseOrderQueue() {

        return new Queue("order.release.order.queue", true, false, false);
    }

    /**
     * 交换机
     * @return
     */
    @Bean
    public Exchange orderEventExchange() {

        // String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        // 普通交换机
        return new TopicExchange("order-event-exchange", true, false);
    }

    /**
     * 和延时队列绑定
     * @return
     */
    @Bean
    public Binding orderCreateOrderBinding() {

        return new Binding("order.delay.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.create.order",
                null);
    }

    /**
     * 和普通队列绑定
     * @return
     */
    @Bean
    public Binding orderReleaseOrderBinding() {

        return new Binding("order.release.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.order",
                null);
    }

    /**
     * 订单释放直接和库存释放进行绑定
     * @return
     */
    @Bean
    public Binding orderReleaseOtherBinding() {

        return new Binding("stock.release.stock.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.other.#",
                null);
    }


}

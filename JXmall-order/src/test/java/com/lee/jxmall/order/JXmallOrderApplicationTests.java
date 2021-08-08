package com.lee.jxmall.order;

import com.lee.jxmall.order.entity.OrderEntity;
import com.lee.jxmall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@Slf4j
@SpringBootTest
class JXmallOrderApplicationTests {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void testSendMessage(){

        //发送消息  参数：1、交换机，2、路由键 key，3、发送的消息
        String msg="hello world";
        rabbitTemplate.convertAndSend("java-exchange","java",msg);
        log.info("消息发送成功");
    }

    @Test
    void testSendObject(){

        for (int i=0;i<10;i++) {
            if (i%2==0) {
                //如果发送的消息是个对象，要使用序列化机制将对象写出去，所有对象要实现Serializable
                OrderReturnReasonEntity entity = new OrderReturnReasonEntity();
                entity.setId((long) i);
                entity.setName("joe");
                entity.setCreateTime(new Date());

                //发送的对象类型的消息，可以是json
                rabbitTemplate.convertAndSend("java-exchange", "java", entity,new CorrelationData(UUID.randomUUID().toString()));
            }else {
                OrderEntity entity = new OrderEntity();
                entity.setId((long) i);
                entity.setMemberUsername("lee");
                entity.setCreateTime(new Date());

                //发送的对象类型的消息，可以是json
                rabbitTemplate.convertAndSend("java-exchange", "111java", entity,new CorrelationData(UUID.randomUUID().toString()));
            }
            log.info("对象消息发送成功");
        }
    }

    /**
     *  1、创建Exchange、Queue、Binding
     *      1）、使用 AmqpAdmin
     *  2、收发消息
     */
    @Test
    void testExchange(){

        //创建交换机 参数：1、名字，2、是否持久化，3、是否自动删除，4、指定参数（可不填）
        DirectExchange directExchange = new DirectExchange("java-exchange",true,false);
        amqpAdmin.declareExchange(directExchange);
        log.info("交换机创建成功");
    }

    @Test
    void contextQueue() {

        //创建队列 参数：1、名字，2、是否持久化，3、是否排他，4、是否自动删除，5、指定参数（可不填）
        Queue queue = new Queue("java-queue",true,false,false);
        amqpAdmin.declareQueue(queue);
        log.info("队列创建成功");
    }

    @Test
    void testBinding(){

        //创建绑定 参数：1、目的地，2、目的地的类型，3、交换机，4、路由键 key，5、自定义参数
        Binding binding = new Binding("java-queue", Binding.DestinationType.QUEUE,"java-exchange","java",null);
        amqpAdmin.declareBinding(binding);
        log.info("绑定成功");
    }

}
